<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2019/1/12
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>成功</title>
</head>
<body>

<center>
    <h1>成功页面</h1>
    <c:if test="${requestScope.list!=null}" >
    <table border="1px" cellspacing="0">
        <thead>
        <tr>
            <th>姓名</th>
            <th>零钱</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="account" items="${requestScope.list}">
            <tr>
                <td>${account.name}</td>
                <td> ${account.money}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</center>

</body>
</html>
