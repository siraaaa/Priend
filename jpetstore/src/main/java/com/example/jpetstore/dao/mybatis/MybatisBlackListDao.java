package com.example.jpetstore.dao.mybatis;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.jpetstore.dao.BlackListDao;
import com.example.jpetstore.dao.mybatis.mapper.BlackListMapper;
import com.example.jpetstore.domain.BlackList;

@Repository
public class MybatisBlackListDao implements BlackListDao {
	
	@Autowired
	private BlackListMapper blackListMapper;
	
	@Override
	public BlackList getDetail(String username) throws DataAccessException {
		return blackListMapper.getDetail(username);
	}

	@Override
	public void removeDetail() throws DataAccessException {
		blackListMapper.removeDetail();
	}

	@Override
	public List<BlackList> getReasonByUsername(String username) throws DataAccessException {
		return blackListMapper.getReasonByUsername(username);
	}
	

	@Override
	public BlackList getBlack(String blackId) throws DataAccessException {
		// TODO Auto-generated method stub
		return blackListMapper.getBlack(blackId);
	}

	@Override
	public void insertBlackList(BlackList black) {
		// TODO Auto-generated method stub
		blackListMapper.insertBlackList(black);
	}
	
}
