package com.hbsi.dao;

import com.hbsi.bean.Admin;
import com.hbsi.bean.User;

public interface AdminDao {
	  //��������û��ķ���
		boolean addAdmin(Admin admin);
	  //���巽����ѯ�����û���Ϣ
		Admin lookAdminName(Admin admin);
}
