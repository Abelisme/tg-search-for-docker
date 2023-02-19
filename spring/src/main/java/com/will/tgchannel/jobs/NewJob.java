package com.will.tgchannel.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.will.tgchannel.bot.TgFileBot;

public class NewJob implements Job {
	// private Logger log = Logger.getLogger(NewJob.class);

	public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
		// log.debug("TestJob run successfully...");
		System.out.println("TgFileBot run start...");

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TgFileBot());
			System.out.println("TgFileBot run successfully...");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
