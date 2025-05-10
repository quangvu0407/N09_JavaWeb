package nhom9.haui.Products;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nhom9.haui.Model.Admin;
import nhom9.haui.Model.Users;
import nhom9.haui.jdbc.ConnectJDBC;

/**
 * Servlet implementation class AddComment
 */
@WebServlet("/Products/AddComment")
public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String content = request.getParameter("comment");
		String product_id = request.getParameter("product_id");
		Users user = (Users) request.getSession().getAttribute("user");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		String email = "";
		int id;
		LocalDateTime now = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String comment_date = now.format(formatter);
		if(admin != null) {
		    email = admin.getEmail();
		    id = admin.getId();
		} else if(user != null) {
		    email = user.getEmail();
		    id = user.getId();
		} else {
		    // Trường hợp không có ai đăng nhập
		    response.sendRedirect(request.getContextPath() + "/Login.jsp");
		    return;
		}
		try {
			Connection cnn = new ConnectJDBC().getConnection();

			String insertSql = "INSERT INTO Comments (email, content, comment_date, product_id, user_id) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pst = cnn.prepareStatement(insertSql);
			pst.setString(1, email);
			pst.setString(2, content);
			pst.setString(3, comment_date);
			pst.setInt(4, Integer.parseInt(product_id));
			pst.setInt(5, id);
			pst.executeUpdate();

			pst.close();
			cnn.close();
			System.out.println("Đã thêm comment!");
			// Quay lại trang chi tiết sản phẩm
			response.sendRedirect(request.getContextPath() + "/Products/ProductDetail?id=" + product_id);

		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Lỗi khi thêm sản phẩm vào giỏ hàng!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
