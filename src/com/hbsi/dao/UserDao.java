package com.hbsi.dao;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;

public interface UserDao {
     //��������û��ķ���
	User addUser(User user);
	//���巽����ѯ�����û���Ϣ
	User lookUser(User user);
	//��ȡ�ܼ�¼��
	int doCount(DoPage dopage);
    //��ȡ��ҳ��
	int doTotalPage(DoPage dopage);
	//��ѯ��ǰҳҪ��ʾ������
	DoPage doFindAll(DoPage dopage);
	//������ɾ���û�����
	boolean deleteUser(int id);
	//�����һ���ɾ�����û�����
	boolean retrieveUser(int id);
	//�޸�����
	boolean updatePwd(User user);
}
