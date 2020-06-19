package com.hbsi.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.StuDao;
import com.hbsi.db.ConnectionFactory;
import com.hbsi.db.DBClose;

public class StudentDaoImpl implements StuDao {
	Connection conn =null;
	PreparedStatement pstat=null;
	ResultSet rs = null;
	//���ѧ����Ϣ
	public boolean addStu(Student student) {
		boolean flag =false;
		conn=ConnectionFactory.getConnection();
		String sql="insert into student(id,sid,sname,sex,age,address,phone) values(?,?,?,?,?,?,?)";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1,student.getId());
			pstat.setInt(2, student.getSid());
			pstat.setString(3, student.getSname());
			pstat.setString(4, student.getSex());
			pstat.setString(5, student.getAge());
			pstat.setString(6, student.getAddress());
			pstat.setString(7, student.getPhone());
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
	//�鿴ѧ������
	public Student lookStudentName(Student student) {
		conn=ConnectionFactory.getConnection();
		String sql = "select sname from student where id=?";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1, student.getId());
			rs=pstat.executeQuery();
			if(rs.next()) {
				student.setSname(rs.getString("sname"));
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return student;
	}
	@Override
	public Student lookStu(Student student) {
		conn=ConnectionFactory.getConnection();
		String sql = "select * from student where id=?";
		try {
			pstat=conn.prepareStatement(sql);
			pstat.setInt(1, student.getId());
			rs=pstat.executeQuery();
			if(rs.next()) {
				student.setSid(rs.getInt("sid"));
				student.setSname(rs.getString("sname"));
				student.setSex(rs.getString("sex"));
				student.setAge(rs.getString("age"));
				student.setAddress(rs.getString("address"));
				student.setPhone(rs.getString("phone"));
					}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(rs,pstat,conn);
		}
		return student;
	}
	@Override
	public int doCount(DoPage dopage) {
		//����һ������count�����ܵļ�¼������ʼ��ֵΪ0��
				int count =0;
				//��ȡ�����ݿ������
				conn = ConnectionFactory.getConnection();
				//����sql���,dopage.getsql()�ǲ�ѯ������where�Ӿ�
				String sql ="select count(*) from student "+dopage.getSql();
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
	@Override
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
	@Override
	public DoPage doFindAll(DoPage dopage) {
		//����һ��List�������������ѯ����ÿһ����¼��װ�ɵ�user����
				List list = new ArrayList();//�����б�����ʼ��Ϊ��
				//��ȡ���ݿ������
				conn=ConnectionFactory.getConnection();
				//����sql���
				String sql="select * from student "+dopage.getSql()+" limit "
						+(dopage.getNowPage()-1)*dopage.getPageSize()+","+dopage.getPageSize();
				try {
					//����Ԥ�������
					pstat=conn.prepareStatement(sql);
					//ִ�в�ѯ���ؽ����
					rs=pstat.executeQuery();
					
					//��������
					while(rs.next()) {
						//����һ��studnet�������Գ�ʼ��ΪĬ��
						Student student = new Student();
						//��ѯ�õ���¼���ֶε�ֵ
						 student.setId(rs.getInt("id"));
                         student.setSid(rs.getInt("sid"));
                         student.setSname(rs.getString("sname"));
                         student.setSex(rs.getString("sex"));
                         student.setAge(rs.getString("age"));
                         student.setAddress(rs.getString("address"));
                         student.setPhone(rs.getString("phone"));
						//�ѷ�װ�õ�user������ӵ��б������
						list.add(student);
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
	//�޸�ѧ����Ϣ
	public boolean updateStu(Student student) {
		boolean flag = false;
		conn=ConnectionFactory.getConnection();
		try {
			pstat=conn.prepareStatement("update student set sname=?,sex=?,age=?,address=?,phone=? where id=? ");
			pstat.setString(1, student.getSname());
			pstat.setString(2, student.getSex());
			pstat.setString(3, student.getAge());
			pstat.setString(4, student.getAddress());
			pstat.setString(5, student.getPhone());
			pstat.setInt(6, student.getId());
			
			int i = pstat.executeUpdate();
			if(i>0) {
				flag=true;
			}else {
				System.out.print(student.getId());
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		return flag;
	}
	//ɾ��ѧ����Ϣ
	public boolean deleteStu(int id) {
		boolean flag = false;
		conn=ConnectionFactory.getConnection();
		String sql="delete from student where id ="+id;
		try {
			pstat=conn.prepareStatement(sql);
			int i=pstat.executeUpdate();
			if(i>0) {
				flag=true;
			}
			pstat = conn.prepareStatement("update user set del_status=1 where id="+id);
			pstat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			DBClose.close(pstat, conn);
		}
		
		return flag;
	}

}
