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
          margin: 0;
          gap: 30px;
        }
        
        .menu li {
          margin-left: 20px;
        }
        
        .navbar a {
          font-size: 25px;
          text-decoration: none;
          color: black;
          transition: color 0.3s red;
        }
        
        .navbar a:hover {
          text-decoration: none;
          color: red;
        }
        
        /* Điều chỉnh form tìm kiếm */
        .navbar form {
          display: flex;
          justify-content: space-between;
          align-items: center;	
          width: 30%;
          max-width: 400px;
          margin-top: 0;
          margin-left: 50px;
        }
        
        .navbar form input {
          min-height: 35px;
          width: 70%;
          height: 30px;
          border-radius: 20px;
          border-color: red;
        }
        
        .navbar form button {
          cursor: pointer;
          height: 35px;
          width: 100px;
          margin-left: 10px;
          border-color: red;
          border-radius: 10px;
          transition: 0.3s color red;
        }
        
        .navbar form button:hover {
          color: red;
        }

        .bnt_cart {
          display: flex;
          justify-content: center;
          align-items: center;
          width: 45px;
          height: 45px;
          overflow: hidden;
          margin-left: 40px; /* cách form tìm kiếm */
          transition: 0.3s;
        }
        
        .bnt_cart img {
          width: 24px;
          height: 24px;
          object-fit: contain;
        }
        
        .bnt_log {
          font-size: 25px;
          padding: 5px 15px;
          border: 2px solid red;
          height: 25px;
          border-radius: 10px;
          text-decoration: none;
          color: black;
          margin-left: auto; 
          transition: 0.3s;
          display: flex;
          justify-content: center;
          text-align: center;
          margin-right: 50px;
        }
        
        .bnt_log:hover {
          color: white;
        }
        
        .content {
          display: grid;
          grid-template-columns: repeat(4, 1fr);
          margin-top: 200px;
          gap: 20px;
          max-width: 90%;
          margin: 200px auto 0 auto;
        }
        
        .content img {
          width: 100%;
          height: 200px;
          display: block;
          object-fit: cover;
          border-radius: 8px;
        }
        
        .item a {
          margin-top: 10px;
          font-size: 20px;
          text-decoration: none;
          color: black;
        }
        
        .item p {
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
          </ul>
          <form action="ProductSearch" method="post">
            <input type="text" name="product" placeholder="Search Products..." />
            <button type="submit">Submit</button>
          </form>
          <a class="bnt_cart" href=""><img src="./image/iconCart.jpg" alt=""></a>
          <a class="bnt_log" href="index.jsp">log out</a>
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
                <a class="name" href="#"><%= product.getName() %></a>
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