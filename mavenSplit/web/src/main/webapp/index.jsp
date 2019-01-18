<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2019/1/12
  Time: 21:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="test1">测试1</a>
<a href="test2">测试2</a>

<form action="test3" method="post">
    转出账号<input type="text" name="sid"><br>
    转入账号<input type="text" name="tid"><br>
    金额<input type="text" name="money"><br>
    <input type="submit" value="转账">

</form>
</body>
</html>
