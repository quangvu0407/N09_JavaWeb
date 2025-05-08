<%@ page import="java.util.List"%>
<%@ page import="nhom9.haui.Model.Product"%>
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
			<li><a href="Home.jsp">Trang chủ</a></li>
			<%
			nhom9.haui.Model.Admin admin = (nhom9.haui.Model.Admin) session.getAttribute("admin");
			if (admin != null) {
			%>
			<li><a href="#">Danh Sách Sản phẩm</a></li>
			<%
			}
			%>
			<li><a href="#">Đơn hàng</a></li>
			<li><a href="#">Liên hệ</a></li>
			<li class="search-form">
				<form action="${pageContext.request.contextPath}/ProductSearch"
					method="post">
					<input type="text" name="product" placeholder="Search Products..." />
					<button type="submit">Search</button>
				</form>
			</li>
			<li><a href="${pageContext.request.contextPath}/ProductCartList"><img
					alt="" src="${pageContext.request.contextPath}/image/iconCart.jpg"></a>
			<li class="bnt_logout"><a href="../index.jsp">Log Out</a></li>
		</ul>
	</nav>

	<h1>Bóng</h1>
	<div class="filter-buttons">
		<a href="Home.jsp?category=1">Bóng đá</a> <a
			href="Home.jsp?category=2">Bóng chuyền</a> <a
			href="Home.jsp?category=3">Bóng rổ</a> <a href="Home.jsp?category=4">Bóng
			futsal</a>
	</div>

	<!-- Danh sách sản phẩm -->
	<div class="content">
		<%
		List<Product> productList = (List<Product>) session.getAttribute("productList");
		String categoryParam = request.getParameter("category");
		int selectedCategory = (categoryParam != null) ? Integer.parseInt(categoryParam) : 1;

		String shoeCategoryParam = request.getParameter("shoeCategory");
		int selectedShoeCategory = (shoeCategoryParam != null) ? Integer.parseInt(shoeCategoryParam) : 4;

		if (productList != null) {
			for (Product product : productList) {
				if (product.getCategoryId() == selectedCategory) {
		%>
		<div class="item">
			<a href="ProductDetail.jsp?id=<%=product.getCode()%>"><img
				src="${pageContext.request.contextPath}/image/<%= product.getThumbnail() %>"
				alt=""></a> <a href="ProductDetail.jsp?id=<%=product.getCode()%>"><%=product.getName()%></a>
			<p><%=String.format("%,d", product.getPrice())%>
				VND
			</p>
		</div>
		<%
		}
		}
		} else {
		%>
		<p>Không có sản phẩm để hiển thị.</p>
		<%
		}
		%>
	</div>

	<h1 class="football">Giày Thể Thao</h1>
	<div class="filter1-buttons">
		<a href="Home.jsp?shoeCategory=5">Giày pickleball</a> <a
			href="Home.jsp?shoeCategory=6">Giày đá bóng</a> <a
			href="Home.jsp?shoeCategory=7">Giày cầu lông</a>
	</div>
	<div class="content">
		<%
		if (productList != null) {
			for (Product product : productList) {
				if (product.getCategoryId() == selectedShoeCategory) {
		%>
		<div class="item">
			<a href="ProductDetail.jsp?id=<%=product.getCode()%>"><img
				src="${pageContext.request.contextPath}/image/<%= product.getThumbnail() %>"
				alt=""></a> <a href="ProductDetail.jsp?id=<%=product.getCode()%>"><%=product.getName()%></a>
			<p><%=String.format("%,d", product.getPrice())%>
				VND
			</p>
		</div>
		<%
		}
		}
		} else {
		%>
		<p>Không có sản phẩm để hiển thị.</p>
		<%
		}
		%>
	</div>

	<h1>Thiết bị tập</h1>
	<div class="content">
		<%
		if (productList != null) {
			for (Product product : productList) {
				if (product.getCategoryId() == 8) {
		%>
		<div class="item">
			<a href="ProductDetail.jsp?id=<%=product.getCode()%>"><img
				src="${pageContext.request.contextPath}/image/<%= product.getThumbnail() %>"
				alt=""></a> <a href="ProductDetail.jsp?id=<%=product.getCode()%>"><%=product.getName()%></a>
			<p><%=String.format("%,d", product.getPrice())%>
				VND
			</p>
		</div>
		<%
		}
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