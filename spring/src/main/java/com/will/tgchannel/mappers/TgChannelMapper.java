package com.will.tgchannel.mappers;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.will.tgchannel.model.TgChannel;

@Mapper
//@Repository
public interface TgChannelMapper{
	int insert(TgChannel tgChannel);

	int insertTgChannelList(List<TgChannel> tgChannelList) throws SQLException;

	boolean saveTgChannelList(List<TgChannel> tgChannelList);
	
	List<TgChannel> queryTgChannelListByKeyWord(String message);
	
}
