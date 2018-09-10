package com.example.jpetstore.dao.mybatis.mapper;

import java.util.List;

import com.example.jpetstore.domain.Mileage;

public interface MileageMapper {
	//���� �߰��� �޼ҵ�
	
   void insert_mileage_history(String user_id,int mileage);
   
   int display_mileage(String user_id);
   
   void update_mileage(String user_id,int mileage);
   
   //20180528 ���ϸ��� �޼ҵ�
   
   List<Mileage> getMileageListByUsername(String username);

}
