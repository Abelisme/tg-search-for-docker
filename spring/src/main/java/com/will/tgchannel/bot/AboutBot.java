package com.will.tgchannel.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class AboutBot extends TelegramLongPollingBot{

	@Override
	public void onUpdateReceived(Update update) {
		try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText() || message.hasLocation()) {
                	if (isCommandForOther(message.getText())) {
                        return;
                    }
                	if(message.getText().startsWith("/stop")){
                		return;
                	}
                	if(message.getText().startsWith("about")) {
                		SendMessage sendMessageRequest = new SendMessage();
                		sendMessageRequest.setChatId(update.getMessage().getChatId().toString());
                		sendMessageRequest.setText("這個機器人提供中文使用者查詢中文搜尋爲主要功能,\n"
                				+ "做好玩的，不然人生只有上班才寫程式不能做自己的小玩具也太可憐了八QAQ");
        				try {
        					execute(sendMessageRequest); // Call method to send the message
        				} catch (TelegramApiException e) {
        					e.getStackTrace();
        				}
                	}
                }
            }
        } catch (Exception e) {
//            BotLogger.error(LOGTAG, e);
        }
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "";
	}

	private static boolean isCommandForOther(String text) {
        boolean isSimpleCommand = text.equals("/start") || text.equals("/help") || text.equals("/stop");
        boolean isCommandForMe = text.equals("/start@weatherbot") || text.equals("/help@weatherbot") || text.equals("/stop@weatherbot");
        return text.startsWith("/") && !isSimpleCommand && !isCommandForMe;
    }
	
}
