package com.will.tgchannel.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
//@TableName("tg_channel")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TgChannel {
	private String id;

	private String channelName;

	private String fromName;

	private Date messageSendDate;

	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getMessageSendDate() {
		return messageSendDate;
	}

	public void setMessageSendDate(Date messageSendDate) {
		this.messageSendDate = messageSendDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
