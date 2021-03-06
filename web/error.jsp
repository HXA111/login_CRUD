<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/9/26
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="utf-8">
    <title>出错了</title>
    <link rel="stylesheet" href="css/error.css">
    <script language="javaScript" src="js/error.js"></script>
</head>
<body>
  <div id="errorDiv">
    <div id="errorHint">
      <c:if test="${empty currentUser}">
        <p id="errorInfo">${info}</p>
        <p><span id="leaveTime">10</span>秒后自动返回到登录界面</p>
        <p>不能跳转，请<a href="login.html">点击这儿</a></p>
      </c:if>
      <c:if test="${!empty currentUser}">
        <p id="errorInfo">${info}</p>
        <p><span id="leaveTime">10</span>秒后自动返回到主界面</p>
        <p>不能跳转，请<a href="main.jsp">点击这儿</a></p>
      </c:if>
    </div>
  </div>
</body>
</html>
