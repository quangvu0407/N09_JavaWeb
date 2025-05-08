package nhom9.haui.Products;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nhom9.haui.jdbc.ConnectJDBC;

@WebServlet("/ProductCard")
public class ProductCard extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductCard() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String quantity = request.getParameter("quantity");
		String price = request.getParameter("price");
		String image = request.getParameter("image");
		String code = request.getParameter("code");
		String username = "quangvu0407";

		try {
			Connection cnn = new ConnectJDBC().getConnection();

			String insertSql = "INSERT INTO Cart (product_id, product_name, quantity, price, image, username) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = cnn.prepareStatement(insertSql);
			pst.setInt(1, Integer.parseInt(id));
			pst.setString(2, name);
			pst.setInt(3, Integer.parseInt(quantity));
			pst.setInt(4, Integer.parseInt(price));
			pst.setString(5, image);
			pst.setString(6, username);
			pst.executeUpdate();

			pst.close();
			cnn.close();
			System.out.println("Đã thêm sản phẩm vào giỏ hàng!");
			// Quay lại trang chi tiết sản phẩm
			response.sendRedirect(request.getContextPath() + "/ProductCartList");

		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Lỗi khi thêm sản phẩm vào giỏ hàng!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
