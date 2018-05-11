<%--
  Created by IntelliJ IDEA.
  User: nguyenthanhlinh
  Date: 5/11/18
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Product</title>
</head>
<body>
<h1>Delete Product</h1>
<p>
    <a href="/products">Back to products list</a>
</p>
<form method="post">
    <h3>Are you sure?</h3>
    <fieldset>
        <legend>Product information</legend>
        <table>
            <tr>
                <td>Name: </td>
                <td>${requestScope["product"].getName()}</td>
            </tr>
            <tr>
                <td>Email: </td>
                <td>${requestScope["product"].getEmail()}</td>
            </tr>
            <tr>
                <td>Address: </td>
                <td>${requestScope["product"].getAddress()}</td>
            </tr>
            <tr>
                <td><input type="submit" value="Delete Customer"></td>
                <td><a href="/products">Back to product list</a></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
