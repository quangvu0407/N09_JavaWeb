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

import nhom9.haui.Model.Cart;
import nhom9.haui.jdbc.ConnectJDBC;

/**
 * Servlet implementation class ProductOrder
 */
@WebServlet("/ProductOrder")
public class ProductOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	List<Cart> cartList = new ArrayList<>();
		try {
			Connection cnn = new ConnectJDBC().getConnection();

			String selectSql = "SELECT * FROM Cart";
			PreparedStatement selectPst = cnn.prepareStatement(selectSql);
			ResultSet rs = selectPst.executeQuery();
		while (rs.next()) {
		    Cart cartItem = new Cart(
		    	rs.getInt("id"),
		        rs.getInt("product_id"),
		        rs.getString("product_name"),
		        rs.getInt("quantity"),
		        rs.getInt("price"),
		        rs.getString("image"),
		        rs.getString("username")
		    );
		    cartList.add(cartItem);
		}


		// Gửi dữ liệu đến JSP để hiển thị
        request.getSession().setAttribute("cartList", cartList);
        response.sendRedirect(request.getContextPath() + "/Products/ProductCart.jsp");

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    response.getWriter().println("Lỗi khi truy vấn CSDL!");
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
