<%@ page import="java.util.List"%>
<%@ page import="nhom9.haui.Products.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ</title>
<link rel="stylesheet" href="Home.css">
</head>
<body>
	<nav class="navbar">
		<a href="Home.jsp"><img
			src="${pageContext.request.contextPath}/image/logo.jpg" alt="Logo"></a>
		<ul class="menu">	
			<li><a href="${pageContext.request.contextPath}/ProductList">Trang chủ</a></li>
			<li><a href="#">Danh Sách Sản phẩm</a></li>
			<li><a href="#">Đơn hàng</a></li>
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

	<div class="content">
		<%
		// Lấy danh sách sản phẩm từ request
		List<Product> productList = (List<Product>) session.getAttribute("productList");

		if (productList != null) {
			for (Product product : productList) {
		%>
		<div class="item">
			<a href="#"><img
				src="${pageContext.request.contextPath}/image/<%= product.getThumbnail() %>"
				alt=""></a> <a href="#"><%=product.getName()%></a>
			<p><%=product.getPrice()%>
				VND
			</p>
		</div>
		<%
		}
		} else {
		%>
		<p>Không có sản phẩm để hiển thị.</p>
		<%
		}
		%>
	</div>
</body>
</html>