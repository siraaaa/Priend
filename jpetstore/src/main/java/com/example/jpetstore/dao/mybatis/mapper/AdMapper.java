package com.example.jpetstore.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.jpetstore.domain.Advertisement;

public interface AdMapper {
	//BannerName�ʵ带 status�� ��ġ��
	//��� ó��(C)
	void insertAd(Advertisement Ad);
	//���� ������ ó��(D), ���� ������ �����ߴ� ����� �����ȴ�. �����־�� �ϴ°� �ҵ�. ���̺��� ������ �ϴ� ������ ��� �� ������(���� ����).
	//void deleteAd(String adid);
	//��ϵ� ���� �о���� ó��(R)
	List<Advertisement> advertise(String today);
	//��������
	List<Advertisement> favAdvertise(@Param("favcategory") String favcategory, @Param("today") String today);

	//mypage���� �����û/���� ���/������ ǥ��, itemid�� �˻��� status�� endDate�� ��ȯ. status�� 0�̸� ���� ���/ status�� 1�̰� endDate�� ���ú��� �ڸ� ������, �������� �����û.
	List<Advertisement> getApprovalList();
	void deleteAdByItemId(String itemId);
}
