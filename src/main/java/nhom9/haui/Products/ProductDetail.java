package nhom9.haui.Products;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nhom9.haui.Model.Comments;
import nhom9.haui.Model.Product;
import nhom9.haui.jdbc.ConnectJDBC;

@WebServlet("/Products/ProductDetail")
public class ProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductDetail() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		List<Comments> commentList = new ArrayList<>();
		Product product = null;

		try (Connection cnn = new ConnectJDBC().getConnection()) {

			// Lấy thông tin sản phẩm
			try (PreparedStatement pst = cnn.prepareStatement(
					"SELECT * FROM Products WHERE id = ?")) {
				pst.setString(1, id);

				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						product = new Product(
								rs.getInt("id"),
								rs.getInt("category_id"),
								rs.getObject("promotion_id") != null ? rs.getInt("promotion_id") : null,
								rs.getString("name"),
								rs.getString("code"),
								rs.getInt("price"),
								rs.getInt("quantity"),
								rs.getString("thumbnail"),
								rs.getString("description"),
								rs.getString("created_at")
						);
					}
				}
			}

			// Lấy danh sách bình luận
			try (PreparedStatement pst = cnn.prepareStatement(
					"SELECT c.*, u.username \r\n"
					+ "FROM Comments c \r\n"
					+ "JOIN Users u ON c.user_id = u.id \r\n"
					+ "WHERE c.product_id = ?")) {
				pst.setString(1, id);

				try (ResultSet rs = pst.executeQuery()) {
					while (rs.next()) {
						Comments comment = new Comments(
								rs.getInt("id"),
								rs.getString("email"),
								rs.getString("content"),
								rs.getDate("comment_date"),
								rs.getInt("product_id"),
								rs.getInt("user_id"),
								rs.getString("username")
						);
						commentList.add(comment);
					}
				}
			}

			// Gửi dữ liệu sang trang JSP
			request.getSession().setAttribute("product", product);
			request.getSession().setAttribute("commentList", commentList);
			response.sendRedirect(request.getContextPath() + "/Products/ProductDetail.jsp");

		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
