package com.will.tgchannel;

import org.mybatis.spring.annotation.MapperScan;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.will.tgchannel.bot.AboutBot;
import com.will.tgchannel.bot.TgFileBot;
import com.will.tgchannel.jobs.NewJob;

@EnableAsync
@SpringBootApplication
@MapperScan(basePackages = "com.will.tgchannel.mappers")
//@ComponentScan(basePackages = {"com.will.tgchannel.mappers"})
public class TgChannelSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TgChannelSearchApplication.class, args);
		//runJob();
		runTgApiBot();
	}

	public static void runJob() {
		try {
			// specify the job' s details..
			JobDetail job = JobBuilder.newJob(NewJob.class).withIdentity("testJob").build();

			// specify the running period of the job
			Trigger trigger = TriggerBuilder.newTrigger()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(500).repeatForever())
					.build();

			// schedule the job
			SchedulerFactory schFactory = new StdSchedulerFactory();
			Scheduler sch = schFactory.getScheduler();
			sch.start();
			sch.scheduleJob(job, trigger);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void runTgApiBot() {
		System.out.println("TgFileBot run start...");
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TgFileBot());
//			botsApi.registerBot(new AboutBot());
			System.out.println("TgFileBot run successfully...");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
}
