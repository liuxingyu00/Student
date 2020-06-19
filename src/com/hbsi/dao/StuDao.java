package com.hbsi.dao;

import com.hbsi.bean.Admin;
import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;

public interface StuDao {
	  //��������û��ķ���
	  boolean addStu(Student student);
	  //���巽����ѯ�����û���Ϣ
	  Student lookStudentName(Student student);
	  //���巽����ѯ�����û���Ϣ
	  Student lookStu(Student student);
	  //��ȡ�ܼ�¼��
	  int doCount(DoPage dopage);
	  //��ȡ��ҳ��
	  int doTotalPage(DoPage dopage);
	  //��ѯ��ǰҳҪ��ʾ������
	  DoPage doFindAll(DoPage dopage);
	  //�޸��û���Ϣ
	  boolean updateStu(Student student);
	  //ɾ���û���Ϣ
	  boolean deleteStu(int id);
}
