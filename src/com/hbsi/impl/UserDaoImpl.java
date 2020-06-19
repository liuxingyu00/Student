package com.hbsi.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class UserDaoImpl implements UserDao {
	Connection conn =null;
	PreparedStatement pstat=null;
	ResultSet rs = null;

	//����û�
	public User addUser(User user) {
		User u=new User();//����User���󣬳�ʼ������ֵ
		conn=ConnectionFactory.getConnection();
		String sql="insert into user(username,password,usertypes,del_status,ctime) values(?,?,?,?,?)";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			pstat.setString(3, user.getUsertypes());
			pstat.setString(4, "2");
			pstat.setString(5, user.getCtime());
			int i = pstat.executeUpdate();
			u=this.lookUser(user);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return u;
	}

	//��ѯ�û���Ϣ
	public User lookUser(User user) {
		//��ȡ�����ݿ������
		conn=ConnectionFactory.getConnection();
		//����һ��sql���
		String sql="select * from user where username=? and password=? and usertypes=?";
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//Ϊsql����е�3 ������ֵ
			pstat.setString(1, user.getUsername());
			pstat.setString(2, user.getPassword());
			pstat.setString(3, user.getUsertypes());
			//ִ�в�ѯ�����ؽ����
			rs=pstat.executeQuery();
			//��������
			if(rs.next()) {
				//�ѽ�������ֶ���Ϊid���ֶ�ֵȡ������Ϊ��������Ϊuser���������id��ֵ
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUsertypes(rs.getString("usertypes"));
				user.setDel_status(rs.getString("del_status"));
					}else {
						user.setUsertypes("error");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					DBClose.close(rs,pstat,conn);
				}
				
				return user;
	}
	//��ȡ�ܼ�¼��
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
		int count =0;
		//��ȡ�����ݿ������
		conn = ConnectionFactory.getConnection();
		//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
		String sql ="select count(*) from user "+dopage.getSql();
		try {
			pstat=conn.prepareStatement(sql);//����Ԥ�������
			rs=pstat.executeQuery();//ִ�в�ѯ�����ؽ����
			if(rs.next()) {//����������Ϊ��
				count= rs.getInt(1);//�ѽ�����л�ȡ��������ȡ������ֵ������count
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);//�ͷ���Դ
		}
		return count;
	}

	public int doTotalPage(DoPage dopage) {
		//�������totalPage��ʾ��ҳ��,��ʼֵΪ0
		int totalPage=0;
		//�������m�����ܼ�¼������ÿҳ��ʾ��¼������
		int m = this.doCount(dopage)/dopage.getPageSize();
		//��¼�ܼ�¼������ÿҳ��ʾ��¼��������
		if(this.doCount(dopage)%dopage.getPageSize()>0) {
			totalPage=m+1;//��ҳ�������ܼ�¼������ÿҳ��ʾ��¼�����̼�1
		}else {
			totalPage=m;
		}
		return totalPage;
	}

	public DoPage doFindAll(DoPage dopage) {
		//����һ��List�������������ѯ����ÿһ����¼��װ�ɵ�user����
		List list = new ArrayList();//�����б�����ʼ��Ϊ��
		//��ȡ���ݿ������
		conn=ConnectionFactory.getConnection();
		//����sql���
		String sql="select * from user "+dopage.getSql()+" limit "
				+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement(sql);
			//ִ�в�ѯ���ؽ����
			rs=pstat.executeQuery();
			
			//��������
			while(rs.next()) {
				//����һ��user�������Գ�ʼ��ΪĬ��
				User user = new User();
				//��ѯ�õ���¼��id�ֶε�ֵ
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUsertypes(rs.getString("usertypes"));
				user.setDel_status(rs.getString("del_status"));
				user.setCtime(rs.getString("ctime"));
				//�ѷ�װ�õ�user������ӵ��б������
				list.add(user);
			}
			//���б��������Ϊdopage������
			dopage.setList(list);
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);//�ͷ���Դ
		}
		return dopage;
	}

	//��ɾ���û�
	public boolean deleteUser(int id) {
		boolean flag = false;
		conn=ConnectionFactory.getConnection();
		String sql="update user set del_status=1 where id="+id;
		try {
			pstat=conn.prepareStatement(sql);
			int i=pstat.executeUpdate();
			if(i>0) {
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	//�һ���ɾ�����û�
	public boolean retrieveUser(int id) {
		boolean flag = false;
		conn=ConnectionFactory.getConnection();
		String sql="update user set del_status=2 where id="+id;
		try {
			pstat=conn.prepareStatement(sql);
			int i=pstat.executeUpdate();
			if(i>0) {
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	//�޸�����
	public boolean updatePwd(User user) {
		boolean flag=false;
		//��ȡ�����ݿ������
		conn=ConnectionFactory.getConnection();
		try {
			//����Ԥ�������
			pstat=conn.prepareStatement("update user set password=? where id=?");
		    //�ò���user�����password����Ϊ��һ������ֵ
			pstat.setString(1, user.getPassword());
			//Ϊ�ڶ����ʺŸ�ֵ
		    pstat.setInt(2, user.getId());
			//ִ���޸��������
			int i = pstat.executeUpdate();
			if(i>0) {//i>0˵����Ӱ����������0
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return flag;
	}

}
