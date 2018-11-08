<%@ page language="java" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
</head>
<body>
    <h4>hello,world</h4>
    <form method="post" action="/jdbc/post">
        <INPUT name="id" type="text">
        <INPUT name="name" type="text">
        <INPUT type="submit">
    </form>
<h3>${name}</h3>
</body>
</html>