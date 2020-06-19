package com.hbsi.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.AdminDao;
import com.hbsi.dao.StuDao;
import com.hbsi.dao.UserDao;
import com.hbsi.impl.AdminDaoImpl;
import com.hbsi.impl.StudentDaoImpl;
import com.hbsi.impl.UserDaoImpl;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username  = request.getParameter("username");
		String password = request.getParameter("password");
		String usertypes = request.getParameter("usertypes");
		//��ȡ�û��ĻỰ����
		HttpSession session = request.getSession();
		//�Ѵ������л�ȡ���û��������롢�û������Ϣ��װΪUser��������ֵ
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setUsertypes(usertypes);
		//��UserDaoʵ���ഴ��UserDao����
		UserDao ud = new UserDaoImpl();
		//���ýӿڶ����lookUser������֤�û�����ֵ�Ƿ���user����
		User u = ud.lookUser(user);
		//������صĶ���u��usertypes����ֵ��error��˵������û���û��������Ϣ
		if(u.getUsertypes().equals("error")) {
			request.setAttribute("errorMsg","�û����������������������");
			//Ȼ������ת�����½ҳ��
			this.gotoPage("public/login.jsp",request,response);
			
		}else {
			if(u.getDel_status().equals("1")) {
				request.setAttribute("errorMsg","���û���ɾ���������һ�����ϵ����Ա");
				//Ȼ������ת�����½ҳ��
				this.gotoPage("public/login.jsp",request,response);
			}
			if(u.getDel_status().equals("2")) {
				//���û���������Ϊ�Ự����ֵ
				session.setAttribute("user", u);
				if(u.getUsertypes().equals("admin")) {
					//�����½�û��ǹ���Ա���������Ա��ҳ��
					this.gotoPage("admin/aIndex.jsp", request, response);
				
				}
				if(u.getUsertypes().equals("student")) {
					//�����½�û���ѧ�����������Ա��ҳ��
					 int id = u.getId();
		        	 Student student = new Student();
		        	 student.setId(id);
		        	 StuDao sd = new StudentDaoImpl();
		        	 Student s = sd.lookStudentName(student);
		        	 if(s.getSname() == null ) {
		        		request.setAttribute("id", u.getId());
		     			this.gotoPage("student/studentInfo.jsp", request, response);
		        	 }else {
		        		 this.gotoPage("student/sIndex.jsp", request, response);
		        	 }
				}
			}
		}
	}
	 //����˽�з�����ʵ������ת��
		private void gotoPage(String url,HttpServletRequest request,HttpServletResponse response)
		  throws ServletException,IOException{
			RequestDispatcher dispatcher=request.getRequestDispatcher(url);
			dispatcher.forward(request,response);
		}

}
