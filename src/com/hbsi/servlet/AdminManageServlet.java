package com.hbsi.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hbsi.bean.Admin;
import com.hbsi.dao.AdminDao;
import com.hbsi.impl.AdminDaoImpl;



public class AdminManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//��ȡ�������action��ֵ
		String action =request.getParameter("action");
		AdminDao ad = new AdminDaoImpl();
		//�ж�actionֵ�������
		if(action.equals("adminregister")) {
			String id =request.getParameter("aid");
			int aid=0;
			aid=Integer.parseInt(id);
			String aname = request.getParameter("aname");
			String sex = request.getParameter("sex");
			String phone = request.getParameter("phone");
			//����һ������
			Admin admin = new Admin();
			admin.setAid(aid);
			admin.setAname(aname);
			admin.setSex(sex);
			admin.setPhone(phone);
			boolean flag =  ad.addAdmin(admin);
			if(flag) {
				request.setAttribute("regError", "����Աע��ɹ����뷵�ص�½��");
				this.gotoPage("public/register.jsp", request, response);
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
