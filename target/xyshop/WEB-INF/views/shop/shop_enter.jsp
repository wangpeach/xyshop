<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.mx.home.utils.Config"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String adminUuid = (String)request.getSession().getAttribute("adminUuid");
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
    <title>管理后台 - 商家入驻处理</title>
    <jsp:include page="../head/baseHead.jsp"></jsp:include>
    <jsp:include page="../head/tableCss.jsp"></jsp:include>
    <jsp:include page="../head/layerDate.jsp"></jsp:include>
</head>
<body class="gray-bg">
	<div class="row wrapper border-bottom white-bg page-heading">
        <div class="col-sm-4">
            <h2>商家入驻处理管理界面</h2>
        </div>
    </div>
    <div class="wrapper wrapper-content">
        <div class="row">
        	<input type="hidden" id="adminUuid" value="<%=adminUuid%>" />
            <div class="col-sm-12">
	            <table id="shoplist"></table>
            </div>
        </div>
    </div>
    
	<jsp:include page="../head/scripts.jsp"></jsp:include>
	<script>seajs.use("shop/shopEnter");</script>
</body>
</html>