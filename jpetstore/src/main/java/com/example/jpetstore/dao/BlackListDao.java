package com.example.jpetstore.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.jpetstore.domain.BlackList;

public interface BlackListDao {
	
	BlackList getDetail(String username) throws DataAccessException;
	
	void removeDetail() throws DataAccessException;
	
	List<BlackList> getReasonByUsername(String username) throws DataAccessException;
	
	//시라씨 부분
	BlackList getBlack(String blackId) throws DataAccessException;

	void insertBlackList(BlackList black) throws DataAccessException;

}
