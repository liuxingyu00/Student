package com.hbsi.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbsi.bean.User;
import com.hbsi.dao.UserDao;
import com.hbsi.impl.UserDaoImpl;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action =request.getParameter("action");
		if(action.equals("register")) {
			//��ȡ�û���д����Ϣ
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String usertypes = request.getParameter("usertypes");
			Date d =new Date();//��ȡ��ǰ���ں�ʱ��
			DateFormat df = DateFormat.getDateTimeInstance();//��ȡ���ں�ʱ��ĸ�ʽ��������
			String ctime =df.format(d);
			//����User����,���Գ�ʼ��ΪĬ��ֵ
			User user =new User();
			//�û�ȡ��������ֵ����Ϊuser���������ֵ
			user.setUsername(username);
			user.setPassword(password);
			user.setUsertypes(usertypes);
	        user.setCtime(ctime);
			//����UserDao����
			UserDao ud = new UserDaoImpl();
			//��������û��ķ���
			User u = ud.addUser(user);
			if(u.getUsertypes().equals("error")) {//˵��û���û�ע��ɹ�
				//����һ���������ԣ���ʾ����û�û�гɹ�
	        	request.setAttribute("regError", "�û�ע��δ�ɹ���������ע��");
	        	//����ע��ҳ��
	        	this.gotoPage("public/register.jsp", request, response);
			}else if(u.getUsertypes().equals("admin")) {
//				request.setAttribute("regError", "����Աע��ɹ����뷵�ص�½");
				request.setAttribute("aid", u.getId());
				this.gotoPage("admin/adminInfo.jsp", request, response);
			}else if(u.getUsertypes().equals("student")) {
				request.setAttribute("id", u.getId());
				this.gotoPage("student/studentInfo.jsp", request, response);
			}
		}
		if(action.equals("addstu")) {
			//��ȡ�û���д����Ϣ
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String usertypes = request.getParameter("usertypes");
			Date d =new Date();//��ȡ��ǰ���ں�ʱ��
			DateFormat df = DateFormat.getDateTimeInstance();//��ȡ���ں�ʱ��ĸ�ʽ��������
			String ctime =df.format(d);
			//����User����,���Գ�ʼ��ΪĬ��ֵ
			User user =new User();
			//�û�ȡ��������ֵ����Ϊuser���������ֵ
			user.setUsername(username);
			user.setPassword(password);
			user.setUsertypes(usertypes);
	        user.setCtime(ctime);
			//����UserDao����
			UserDao ud = new UserDaoImpl();
			//��������û��ķ���
			User u = ud.addUser(user);
			if(u.getUsertypes().equals("error")) {//˵��û���û�ע��ɹ�
				//����һ���������ԣ���ʾ����û�û�гɹ�
	        	request.setAttribute("regError", "ѧ�����ʧ�ܣ����������");
	        	//����ע��ҳ��
	        	this.gotoPage("admin/addStu.jsp", request, response);
			}else if(u.getUsertypes().equals("student")) {
				request.setAttribute("id", u.getId());
				this.gotoPage("admin/addStuInfo.jsp", request, response);
			}
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		doGet(request, response);
	}
	//����˽�з�����ʵ������ת��
	private void gotoPage(String url,HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
	    RequestDispatcher dispatcher=request.getRequestDispatcher(url);
	    dispatcher.forward(request,response);
			}


}
