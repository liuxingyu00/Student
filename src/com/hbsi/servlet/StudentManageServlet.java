package com.hbsi.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hbsi.bean.DoPage;
import com.hbsi.bean.Student;
import com.hbsi.bean.User;
import com.hbsi.dao.StuDao;
import com.hbsi.dao.UserDao;
import com.hbsi.impl.StudentDaoImpl;
import com.hbsi.impl.UserDaoImpl;


public class StudentManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//��ȡ�������action��ֵ
	    String action =request.getParameter("action");
	    StuDao sd = new StudentDaoImpl();
	    if(action.equals("sturegister")) {//�ж�action�Ƿ�����ע��ҳ��
	    	//��ȡ�û��������Ϣ
	    	String userid = request.getParameter("id");
	    	int uid = Integer.parseInt(userid);
	    	String id = request.getParameter("sid");
	    	int sid = Integer.parseInt(id);
	    	String sname = request.getParameter("sname");
	    	String age = request.getParameter("age");
	    	String sex = request.getParameter("sex");
	    	String address = request.getParameter("address");
	    	String phone = request.getParameter("phone");
	    	//����һ������
	    	Student student = new Student();
	    	student.setId(uid);
	    	student.setSid(sid);
	    	student.setSname(sname);
	    	student.setAge(age);
	    	student.setSex(sex);
	    	student.setAddress(address);
	    	student.setPhone(phone);
	    	boolean flag = sd.addStu(student);
	    	if(flag) {
	    		request.setAttribute("regError", "ѧ��ע��ɹ����뷵�ص�½��");
				this.gotoPage("public/register.jsp", request, response);
	    	}
	    	
	    }
	    if(action.equals("addStuInfo")) {
	    	//��ȡ�û��������Ϣ
	    	String userid = request.getParameter("id");
	    	int uid = Integer.parseInt(userid);
	    	String id = request.getParameter("sid");
	    	int sid = Integer.parseInt(id);
	    	String sname = request.getParameter("sname");
	    	String age = request.getParameter("age");
	    	String sex = request.getParameter("sex");
	    	String address = request.getParameter("address");
	    	String phone = request.getParameter("phone");
	    	//����һ������
	    	Student student = new Student();
	    	student.setId(uid);
	    	student.setSid(sid);
	    	student.setSname(sname);
	    	student.setAge(age);
	    	student.setSex(sex);
	    	student.setAddress(address);
	    	student.setPhone(phone);
	    	boolean flag = sd.addStu(student);
	    	if(flag) {
	     		this.gotoPage("/stuManage?action=StuList", request, response);
	    	}
	    }
	    if(action.equals("StuList")) {
	    	//����Dopage���󣬳�ʼ�����������ΪĬ��ֵ
			DoPage dopage = new DoPage();
			//��ȡ��ǰҳ���������ȡ�����еĵ�ǰҳ�룩
			String pageNum=request.getParameter("page");
			int pageNo=0;//�������pageNo��ʾ��ǰ�ǵڼ�ҳ
			if(pageNum==null) {//���û�д�������ܻ�ȡ��page������ֵ
				pageNo=1;
			}else {//����������л�ȡ���˲���page��ֵ
				pageNo=Integer.parseInt(pageNum);//�ѵõ��Ĳ���ֵת��Ϊ�������Ƹ�pageNum
				
			}
			//1.����dopage�����nowpage����ֵΪpageNo
			dopage.setNowPage(pageNo);
			//�������л�ȡ�������sql��ֵ
			String sqlStr = "";
			int id=0;
			String stuid= request.getParameter("stuId");
			try {
				id=Integer.parseInt(stuid);
				System.out.print(id);
				if(id == 0) {//���û�еõ�����sql��ֵ
					sqlStr="";
				}else {
                    sqlStr=" where sid ="+id;
				}
			
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			//2.����dopage�����sqlStr��ֵ
			dopage.setSql(sqlStr);
			//3.����dopage�����pageSize������ֵ
			dopage.setPageSize(5);
			//��ȡ�ܵļ�¼��
			int totalCount = sd.doCount(dopage);
			if(totalCount == 0) {
				System.out.print("δ�ҵ�����������ȷ��ID");
				request.setAttribute("errorMsg","δ�ҵ����û�����������ȷ��ID");
				System.out.print(request);
				this.gotoPage("admin/showStu.jsp", request, response);
			}else {
				//4.����dopage�����count����ֵ
				dopage.setCount(totalCount);
				//��ȡ��ҳ��
				int totalPage=sd.doTotalPage(dopage);
				//5.����dopage��totalPage����ֵ
				dopage.setTotalPage(totalPage);
				//6.����ǰ�����ã�dopage������5������ֵ����Ϊ����
				dopage=sd.doFindAll(dopage);
				//��6�����Է�װ�õ�dopage��������Ϊ��������
				request.setAttribute("doPage", dopage);
				this.gotoPage("admin/showStu.jsp", request, response);
			}

	    }
	    if(action.equals("show")) {
			//��ȡ�Ự����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����½�û�����
			User user=(User)session.getAttribute("user");
			//�����½�û���ѧ������ȡ�û�id��Ϊѧ����sid
			int sid=0;
	    	Student student = new Student();

			if(user.getUsertypes().equals("student")) {
				sid=user.getId();
		    	student.setId(sid);
		    	sd.lookStu(student);
                request.setAttribute("student", student);
		    	this.gotoPage("student/stuShow.jsp", request, response);
			}else {//�����½�û�����ѧ��
				//��ȡѧ��id��ֵ
				sid=Integer.parseInt(request.getParameter("id"));
		    	student.setId(sid);
		    	sd.lookStu(student);
                request.setAttribute("student", student);
		    	this.gotoPage("student/updateStu.jsp", request, response);
			}

	    }
        if(action.equals("update")) {
        	//��ȡ�û��������Ϣ
	    	String userid = request.getParameter("id");
	    	int uid = Integer.parseInt(userid);
	    	String sname = request.getParameter("sname");
	    	String age = request.getParameter("age");
	    	String sex = request.getParameter("sex");
	    	String address = request.getParameter("address");
	    	String phone = request.getParameter("phone");
	    	//����һ������
	    	Student student = new Student();
	    	student.setId(uid);
	    	student.setSname(sname);
	    	student.setAge(age);
	    	student.setSex(sex);
	    	student.setAddress(address);
	    	student.setPhone(phone);
	    	boolean flag = sd.updateStu(student);
	    	//��ȡ�Ự����
			HttpSession session = request.getSession();
			//�ӻỰ������ȡ����½�û�����
			User user=(User)session.getAttribute("user");
	    	if(flag) {
	    		if(user.getUsertypes().equals("student")) {
	    			 this.gotoPage("/stuManage?action=show", request, response);
	    		}else if(user.getUsertypes().equals("admin")) {
	    			this.gotoPage("/stuManage?action=StuList", request, response);
	    		}
				
	    	}else {
	    		System.out.print("ʧ��");
	    		System.out.print(uid);
	    	}
	    }
        if(action.equals("delete")) {
        	int id=0;//�������id��ʼֵΪ0
			//��ȡ���������id��ֵ
			String idStr= request.getParameter("id");
			try {
				id=Integer.parseInt(idStr);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean flag=sd.deleteStu(id);
			if(flag) {
				this.gotoPage("/stuManage?action=StuList", request, response);
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
