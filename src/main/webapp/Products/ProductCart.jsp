<%@ page import="java.util.List"%>
<%@ page import="nhom9.haui.Products.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<link rel="stylesheet" href="ProductDetail.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT"
	crossorigin="anonymous">
</head>
<body>
	<nav class="navbarmenu">
		<a href="Home.jsp"><img
			src="${pageContext.request.contextPath}/image/logo.jpg" alt="Logo"></a>
		<ul class="menu">
			<li><a href="Home.jsp">Trang chủ</a></li>
			<li><a href="#">Danh Sách Sản phẩm</a></li>
			<li><a href="#">Liên hệ</a></li>
			<li class="search-form">
				<form action="${pageContext.request.contextPath}/ProductSearch"
					method="post">
					<input type="text" name="product" placeholder="Search Products..." />
					<button type="submit">Search</button>
				</form>
			</li>
			<li><a href="Cart"><img alt=""
					src="${pageContext.request.contextPath}/image/iconCart.jpg"></a>
			<li class="bnt_logout"><a href="../index.jsp">Log Out</a></li>
		</ul>
	</nav>