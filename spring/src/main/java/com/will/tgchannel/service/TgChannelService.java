package com.will.tgchannel.service;

import java.sql.SQLException;
import java.util.List;

import com.will.tgchannel.model.TgChannel;

public interface TgChannelService {
	int insert(TgChannel tgChannel) throws SQLException;

	int insertTgChannelList(List<TgChannel> tgChannelList) throws SQLException;

	boolean saveTgChannelList(List<TgChannel> tgChannelList) throws SQLException;

	List<TgChannel> queryTgChannelListByKeyWord(String keyword) throws SQLException;
	
}
