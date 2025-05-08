<%@ page import="java.util.List"%>
<%@ page import="nhom9.haui.Model.Cart"%>
<%@ page import="java.util.ArrayList"%>
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
	rel="stylesheet">
<script>
    function showOrderForm() {
        // Ẩn giỏ hàng
                const checkboxes = document.querySelectorAll('input[name="selectedIds"]:checked');
        if (checkboxes.length === 0) {
            alert("Vui lòng chọn ít nhất một sản phẩm để đặt hàng.");
            return;
        }
        document.getElementById("cartList").style.display = "none";

        // Tạo và hiển thị form đặt hàng
        var orderForm = document.createElement('div');
        orderForm.innerHTML = `
            <div class="container mt-4">
                <h3 class="mt-4">Nhập thông tin giao hàng</h3>
                <form action="ProductOrderDetail" method="post">
                    <div class="mb-3">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="phone" name="phone" required>
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Địa chỉ giao hàng</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                    </div>
                    <div class="text-end">
                        <button type="submit" class="btn btn-primary">Xác nhận đơn hàng</button>
                    </div>
                </form>
            </div>
        `;

        document.body.appendChild(orderForm);
    }
    function validatePhone() {
        const phone = document.getElementById("phone").value.trim();
        const phoneRegex = /^[0-9]{9}$/;

        if (!phoneRegex.test(phone)) {
            alert("Số điện thoại phải gồm đúng 9 chữ số.");
            return false;
        }
        return true;
    }
</script>

</head>
<style>
.container td a {
	text-decoration: none;
}

.container form {
	margin-bottom: 20px;
}
</style>
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
			<li><a href="#">Liên hệ</a></li>
			<li class="search-form">
				<form action="${pageContext.request.contextPath}/ProductSearch"
					method="post">
					<input type="text" name="product" placeholder="Search Products..." />
					<button type="submit">Search</button>
				</form>
			</li>
			<li><a href="${pageContext.request.contextPath}/ProductCartList"><img
					src="${pageContext.request.contextPath}/image/iconCart.jpg"></a></li>
			<li class="bnt_logout"><a href="../index.jsp">Log Out</a></li>
		</ul>
	</nav>

	<%
	List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
	if (cartList == null) {
		cartList = new java.util.ArrayList<>();
	}
	String checkout = request.getParameter("checkout");
	String[] selectedIds = request.getParameterValues("selectedIds");
	%>

	<div id="cartList" class="container mt-4">
		<h2 class="mb-4">Danh sách đơn hàng</h2>

		<form action="ProductCart.jsp" method="post">
			<table class="table table-bordered table-striped">
				<thead class="table-dark">
					<tr>
						<th>Chọn</th>
						<th>Mã sản phẩm</th>
						<th>Tên sản phẩm</th>
						<th>Số lượng</th>
						<th>Giá</th>
						<th>Ảnh</th>
						<th>Hành động</th>
					</tr>
				</thead>
				<tbody>
					<%
					for (Cart p : cartList) {
					%>
					<tr>
						<td><input type="checkbox" name="selectedIds"
							value="<%=p.getId()%>"></td>
						<td><%=p.getProductId()%></td>
						<td><%=p.getProductName()%></td>
						<td><%=p.getQuantity()%></td>
						<td><%=p.getPrice()%> VNĐ</td>
						<td><img
							src="${pageContext.request.contextPath}/image/<%=p.getImage()%>"
							width="80"></td>
						<td><a
							href="${pageContext.request.contextPath}/ProductDeleteCart?id=<%=p.getId()%>">Xóa</a></td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<div class="text-end">
				<button type="button" onclick="showOrderForm()"
					class="btn btn-success">Đặt hàng</button>
			</div>
		</form>

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
