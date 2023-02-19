package com.will.tgchannel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Component
public class TgApiConfig {

	@Value("${tgbotapi.botUsername}")
	private String botUsername;
	
	@Value("${tgbotapi.botToken}")
	private String botToken;
	
	@Autowired
	public String getBotUsername(@Value("${tgbotapi.botUsername}") String botUsername) {
		this.botUsername = botUsername;
		return botUsername;
	}
	
	public String getBotToken() {
		return botToken;
	}
}
