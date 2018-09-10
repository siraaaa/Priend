package com.example.jpetstore.dao.mybatis.mapper;



import java.util.List;

import com.example.jpetstore.domain.BlackList;

public interface BlackListMapper {

	BlackList getDetail(String username);
	void removeDetail();
	List<BlackList> getReasonByUsername(String username);
	
	//½Ã¶ó¾¾ ºÎºÐ
	BlackList getBlack(String blackId);

	void insertBlackList(BlackList black);
}
