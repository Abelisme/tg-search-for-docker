package com.will.tgchannel.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.will.tgchannel.beans.SpringBeanFactory;
import com.will.tgchannel.mappers.TgChannelMapper;
import com.will.tgchannel.model.TgChannel;

@Service
public class TgChannelServiceImpl implements TgChannelService {//extends ServiceImpl<TgChannelMapper, TgChannel>
	@Autowired
	private TgChannelMapper mapper;
//	TgChannelMapper mapper = SpringBeanFactory.getBean("TgChannelMapper");

	@Override
	public int insert(TgChannel tgChannel) throws SQLException {
		int result = mapper.insert(tgChannel);

		return result;
	}

	@Override
	@Async
	public int insertTgChannelList(List<TgChannel> tgChannelList) throws SQLException {
		int result = mapper.insertTgChannelList(tgChannelList);

		return result;
	}

	@Override
	public boolean saveTgChannelList(List<TgChannel> tgChannelList) {
		boolean result = mapper.saveTgChannelList(tgChannelList);

		return result;
	}

	@Override
	public List<TgChannel> queryTgChannelListByKeyWord(String keyword) throws SQLException {
		List<TgChannel> result = mapper.queryTgChannelListByKeyWord(keyword);
		
		return result;
	}

}
