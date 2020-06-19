package com.hbsi.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hbsi.bean.Admin;
import com.hbsi.dao.AdminDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class AdminDaoImpl implements AdminDao {
	Connection conn =null;
	PreparedStatement pstat=null;
	ResultSet rs = null;
	//��ӹ���Ա��Ϣ
	public boolean addAdmin(Admin admin) {
		boolean flag =false;
		conn=ConnectionFactory.getConnection();
		String sql="insert into admin(aid,aname,sex,phone) values(?,?,?,?)";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1, admin.getAid());
			pstat.setString(2, admin.getAname());
			pstat.setString(3, admin.getSex());
			pstat.setString(4, admin.getPhone());
			int i=pstat.executeUpdate();
			if(i>0) {//˵�����û���ӳɹ�
				flag=true;//���������ݳɹ����Ѳ���ֵ��Ϊtrue
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return flag;
	}
	//����id��ѯ�û�������
	public Admin lookAdminName(Admin admin) {
		conn=ConnectionFactory.getConnection();
		String sql = "select aname from admin where aid=?";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1, admin.getAid());
			rs=pstat.executeQuery();
			if(rs.next()) {
				//�ѽ�������ֶ���Ϊid���ֶ�ֵȡ������Ϊ��������Ϊuser���������id��ֵ
				admin.setAname(rs.getString("aname"));
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return admin;
	}

}
