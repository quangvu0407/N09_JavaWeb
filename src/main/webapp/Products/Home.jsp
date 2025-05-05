<%@ page import="java.util.List" %>
<%@ page import="nhom9.haui.Products.Product" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
    <link rel="stylesheet" href="Home.css">
</head>
<style>
body {
    margin: 0;
    padding: 0;
    background-color: blanchedalmond;
}

.navbar {
    width: 100%;
    background-color: white;
    color: white;
    padding: 15px 30px;
    display: flex;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    border-color: black;
}

.navbar img {
    margin-right: 20px;
    width: 50px;
    font-weight: bold;
}

.menu {
    display: flex;
    list-style: none;
    padding: 0;
    margin: 0 auto;
    align-items: center;
    gap: 30px;
    margin-left: 3%;
}

.menu li {
    margin-left: 20px;
}

.bnt_logout a{
	margin-left: 200px	;
	width: 50px;
	border-color: black;
	font-size: 20px;
	text-decoration: underline;
}

.navbar a {
    font-size: 25px;
    text-decoration: none;
    color: black;
    transition: color 0.3s red;
}

.navbar a:hover {
    text-decoration: underline;
    color: red;
}

.navbar form {
    width: 200%
}

.navbar form button {
    height: 35px;
    width: 100px;
    margin-left: 10px;
    border-color: red;
    transition: 0.3s color red;
}

.navbar form button:hover {
    color: red;
}

.navbar form input {
    width: 70%;
    height: 30px;
    border-radius: 20px;
    border-color: red;
}

.content {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    margin-top: 200px; 
    gap: 20px;
    max-width: 90%; 
    margin: 180px auto 0 auto; 
  }


.content img {
    width: 100%;
    height: 200px;
    display: block;
    object-fit: cover;
    border-radius: 8px;
}

.item a{
    text-decoration: none;
    color: black;
}

.item p{
    font-size: 20px;
    margin-top: 10px;
}
</style>
<body>
    <nav class="navbar">
        <a href="Home.jsp"><img src="./image/logo.jpg" alt=""></a>
        <ul class="menu">
            <li><a href="#">Trang chủ</a></li>
            <li><a href="#">Danh Sách Sản phẩm</a></li>
            <li><a href="#">Liên hệ</a></li>
            <li>
                <form action="ProductSearch" method="get">
				    <input type="text" name="eq" placeholder="Search Products..."/>
				    <button type="submit">Search</button>
				</form>
            </li>
            <li class="bnt_logout"><a href="#">Log Out</a></li>
        </ul>
    </nav>
    
    <div class="content">
        <%
            // Lấy danh sách sản phẩm từ request
            List<Product> productList = (List<Product>) request.getAttribute("productList");

            if (productList != null) {
                for (Product product : productList) {
        %>
            <div class="item">
                <a href="#"><img src="${pageContext.request.contextPath}/image/<%= product.getThumbnail() %>" alt=""></a>
                <a href="#"><%= product.getName() %></a>
                <p><%= product.getPrice() %> VND</p>
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