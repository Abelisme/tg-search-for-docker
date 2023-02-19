package com.will.tgchannel.bot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.mchange.v2.lang.StringUtils;
import com.will.tgchannel.beans.SpringBeanFactory;
import com.will.tgchannel.model.TgChannel;
import com.will.tgchannel.service.TgChannelService;
import com.will.tgchannel.utils.IDGeneratorUtils;

//@Controller
//Standard Spring component annotation
@Component
public class TgFileBot extends TelegramLongPollingBot {
//	@Autowired
//	private TgChannelService tgchannelservice;
	TgChannelService tgchannelservice = SpringBeanFactory.getBean("tgChannelServiceImpl");

//	@Autowired
//	private SqlSessionTemplate sqlSessionTemplate;
	SqlSessionTemplate sqlSessionTemplate = SpringBeanFactory.getBean("sqlSessionTemplate");

	@Override
	public void onUpdateReceived(Update update) {
		long stime = System.currentTimeMillis(); // Statistics start time

		if (update.getMessage().hasDocument()) {
			SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			ExecutorService executor = Executors.newCachedThreadPool();// 開thread

			String doc_id = update.getMessage().getDocument().getFileId();
			String doc_name = update.getMessage().getDocument().getFileName();
			String doc_mine = update.getMessage().getDocument().getMimeType();
			Long doc_size = update.getMessage().getDocument().getFileSize();
//			String getID = String.valueOf(update.getMessage().getFrom().getId());
			Document document = new Document();
			document.setMimeType(doc_mine);
			document.setFileName(doc_name);
			document.setFileSize(doc_size);
			document.setFileId(doc_id);
			GetFile getFile = new GetFile();
			getFile.setFileId(document.getFileId());
			try {
				File tgFileObject = execute(getFile);
				URL url = new URL(tgFileObject.getFileUrl(getBotToken()));// https://org.tele.api/bot{token}/document/file.html
				String html = "";
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNextLine()) {
					html += sc.nextLine();
				}
				org.jsoup.nodes.Document doc = Jsoup.parse(html);// 元素
				CompletableFuture<Integer> hello = CompletableFuture.supplyAsync(() -> {
					Integer result = 0;
					try {
						List<TgChannel> tgChannelList = new ArrayList<>();
						Elements elesPageHeader = doc.select("div.page_header");// page_header
						TgChannel tgChannel = new TgChannel();
						for (Element headline : elesPageHeader) {
							String channelName = StringUtils.nonEmptyString(headline.select("div.text.bold").text())
									? headline.select("div.text.bold").text()
									: "";// group name
							tgChannel.setChannelName(channelName);
						}
						Elements elesMessageDefaultClearfix = doc.select(".message.default.clearfix");
//						for (Element headline : elesMessageDefaultClearfix) {
						for (int i = 1; i < elesMessageDefaultClearfix.size(); i++) {
							Element headline = elesMessageDefaultClearfix.get(i);
							tgChannel.setId(IDGeneratorUtils.getUUID());// random ID
							tgChannel.setFromName(headline.select("div.from_name").text());// username
							tgChannel.setMessage(headline.select("div.text").text());// text
							Date messageSendDate = new SimpleDateFormat("mm.dd.yyyy HH:mm:ss")
									.parse(headline.select("div.pull_right.date.details").attr("title"));
							tgChannel.setMessageSendDate(messageSendDate);// date
							tgChannelList.add(tgChannel);

							if (i == elesMessageDefaultClearfix.size() - 1) {
								System.out.println("hello Start");
								result = tgchannelservice.insertTgChannelList(tgChannelList);
								System.out.println("hello End");
							}
							// 第1000個insert都會發生重複id的問題
							if (i % 1000 == 0) {
								// 手動每1000個一提交，提交後無法回滾
								session.commit();
								// 清理快取，防止溢位
								session.clearCache();
								List<TgChannel> tgChannelList2 = new ArrayList<>();
								tgChannelList2.add(tgChannel);
								result = tgchannelservice.insertTgChannelList(tgChannelList2);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					System.out.println("say Hello");
					return result;
				}, executor);
				System.out.println("-----------");

				long etime = System.currentTimeMillis(); // Statistics end time
				System.out.println("execution time:" + (etime - stime));

			} catch (TelegramApiException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				session.rollback();
			} finally {
				session.close();
				executor.shutdown();

				SendMessage message = new SendMessage();
				message.setChatId(update.getMessage().getChatId().toString());
				message.setText("掃描完成");
				try {
					execute(message); // Call method to send the message
				} catch (TelegramApiException e) {
					e.getStackTrace();
				}
			}
		}
		if (update.hasMessage()) {
			Message message = update.getMessage();
			SendMessage sendMessageRequest = new SendMessage();
			if (message.hasText() || message.hasLocation()) {
				if (isCommandForOther(message.getText())) {
					return;
				}
				if (message.getText().startsWith("/stop")) {
					return;
				}
				if (message.getText().startsWith("about")) {
					sendMessageRequest.setChatId(update.getMessage().getChatId().toString());
					sendMessageRequest.setText("這個機器人提供中文使用者查詢中文搜尋爲主要功能,\n" + "做好玩的，如果有任何問題可以發在issues上，謝謝。");
				}
				if (message.getText().startsWith("searchKeyWord")) {// return List<TgChannel>
					try {
						String keyWord = message.getText().replace("searchKeyWord", "");
						if (StringUtils.nonEmptyString(keyWord)) {
							List<TgChannel> queryList = tgchannelservice.queryTgChannelListByKeyWord(keyWord.trim());
							sendMessageRequest.setChatId(update.getMessage().getChatId().toString());
							StringBuilder strb = new StringBuilder();
							for (TgChannel eles : queryList) {
								String resultStr = "使用者名稱: " + eles.getFromName() + "\n訊息: " + eles.getMessage()
										+ "\n時間: " + eles.getMessageSendDate() + "\n";
								strb.append(resultStr);
							}
							if (!queryList.isEmpty()) {
								sendMessageRequest.setText(strb.toString());
							} else {
								sendMessageRequest.setText("查不到相關訊息");
							}
						} else {
							sendMessageRequest.setText("請重新輸入關鍵字");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					execute(sendMessageRequest); // Call method to send the message
				} catch (TelegramApiException e) {
					e.getStackTrace();
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		Properties prop = getAppProperties();
		String botUsername = prop.getProperty("tgbotapi.botToken");
		return botUsername;
	}

	@Override
	public String getBotToken() {
		Properties prop = getAppProperties();
		String botToken = prop.getProperty("tgbotapi.botToken");
		return botToken;
	}

	private static boolean isCommandForOther(String text) {
		boolean isSimpleCommand = text.equals("/start") || text.equals("/help") || text.equals("/stop");
		boolean isCommandForMe = text.equals("/start@tggroupbot") || text.equals("/help@tggroupbot")
				|| text.equals("/stop@tggroupbot");
		return text.startsWith("/") && !isSimpleCommand && !isCommandForMe;
	}

	private Properties getAppProperties() {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String appConfigPath = rootPath + "application.properties";
		Properties appProp = new Properties();
		try {
			appProp.load(new FileInputStream(appConfigPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appProp;
	}

}