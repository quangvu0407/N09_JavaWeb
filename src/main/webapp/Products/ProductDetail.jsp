<%@ page import="java.util.List"%>
<%@ page import="nhom9.haui.Model.Product"%>
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
	<%
	String idParam = request.getParameter("id");
	Product selectedProduct = null;

	if (idParam != null) {
		List<Product> productList = (List<Product>) session.getAttribute("productList");
		for (Product p : productList) {
			if (p.getCode().equals(idParam)) {
		selectedProduct = p;
		break;
			}
		}
	}
	%>
	<div class="container mt-4">
		<div class="row mb-4 border p-3 rounded shadow-sm">
			<div class="col-md-6">
				<%
				if (selectedProduct != null) {
				%>
				<img
					src="${pageContext.request.contextPath}/image/<%= selectedProduct.getThumbnail() %>"
					alt="Hình ảnh sản phẩm" class="img-fluid rounded">
				<%
				} else {
				%>
				<p>Không tìm thấy sản phẩm này.</p>
				<%
				}
				%>
			</div>
			<div class="col-md-4">
				<h4><%=selectedProduct.getName()%></h4>
				<p><%=selectedProduct.getDescription()%></p>
				<p>
					<strong>Giá:</strong><%=selectedProduct.getPrice()%>
					VND
				</p>
				<p>
					Số lượng hiện có:
					<%=selectedProduct.getQuantity()%></p>
				<div class="counter-container">
					<button onclick="decrease()">−</button>
					<div id="number" class="number-display">1</div>
					<button onclick="increase()">+</button>
				</div>
				<a href="#" onclick="addToCart()" class="btn btn-primary">Thêm
					vào giỏ</a>
			</div>
		</div>
	</div>
	<script>
		let count = 1;

		function updateDisplay() {
			document.getElementById("number").innerText = count;
		}

		function increase() {
			if (count < <%=selectedProduct.getQuantity()%>
		) {
				count++;
				updateDisplay();
			}

		}

		function decrease() {
			if (count > 1) {
				count--;
				updateDisplay();
			}
		}

		function addToCart() {
		    const id = <%=selectedProduct.getId()%>;
		    const price = <%=selectedProduct.getPrice()%> * count;
		    const name = "<%=selectedProduct.getName()%>";
		    const thumbnail = "<%=selectedProduct.getThumbnail()%>";
		    
		    // Hiển thị thông báo
		    alert("Đã thêm '" + name + "' vào giỏ hàng!");
		    console.log("Gửi dữ liệu: id =", id, "price =", price, "name =", name, "thumbnail =", thumbnail, "count =", count);

		    // Chuyển hướng tới trang giỏ hàng với các tham số
		    window.location.href = "${pageContext.request.contextPath}/ProductCard?id=" + id + "&name=" + encodeURIComponent(name) + "&quantity=" + count + "&price=" + price + "&image=" + encodeURIComponent(thumbnail) +"&code="+ "<%=idParam%>
		";
		}
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
		crossorigin="anonymous"></script>
</body>
</html>