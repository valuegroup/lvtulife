<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="java.lang.Runtime"%>
<%
	String toolName = request.getParameter("toolName");
	if(!StringUtils.isBlank(toolName))
		Runtime.getRuntime().exec("cmd /k start "+toolName);		
	
	//执行CMD命令调用系统计算器工具
	//该功能现在无法验证能否在linux平台上使用
	//Runtime.getRuntime().exec("cmd /k start calc");//计算器
	//Runtime.getRuntime().exec("cmd /k start notepad");//opens windows 2000 notepad 记事本 
	//Runtime.getRuntime().exec("cmd /k start mspaint");//microsoft paint 画板 
%>

