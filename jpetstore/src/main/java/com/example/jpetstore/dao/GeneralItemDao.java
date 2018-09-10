package com.example.jpetstore.dao;
//�ö�
import org.springframework.dao.DataAccessException;

import com.example.jpetstore.domain.GeneralItem;
import com.example.jpetstore.domain.Inventory;

public interface GeneralItemDao {
	
	public void insertGeneralItem(GeneralItem generalItem);
	
	GeneralItem getGeneralItem(String sitemId);
	
	//ITEM TABLE ���� ���� 
//	int getItemID();
	
	//180623 수정--------------------------
	public void insertQuantity(GeneralItem generalItem);
	
	//업데이트
	public void updateGeneralItem(GeneralItem generalItem);
	public void updateQuantity(GeneralItem generalItem);

}
