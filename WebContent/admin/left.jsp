<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>left页面</title>
<link rel="stylesheet" href="/sx/css/bootstrap.min.css"/>
<style type="text/css">
  body{
    padding-top:20px;
  } 
</style>
</head>
<body style="text-align: center;">
<div class="container">

  <div class="list-group">
    <ul class="list-group">
    <li class="list-group-item active">管理员导航栏</li>
    </ul>
    <a href="/sx/public/right.jsp" class="list-group-item list-group-item-action" target="main">首页</a>
    <a href="/sx/userManage?action=UserList" class="list-group-item list-group-item-action" target="main">用户管理</a>
    <a href="/sx/stuManage?action=StuList" class="list-group-item list-group-item-action" target="main">学生管理</a>
    <a href="/sx/admin/addStu.jsp" class="list-group-item list-group-item-action" target="main">增加学生用户</a>
    <a href="/sx/public/updatePassword.jsp" class="list-group-item list-group-item-action" target="main">修改密码</a>
  </div>

</div>
</body>
</html>