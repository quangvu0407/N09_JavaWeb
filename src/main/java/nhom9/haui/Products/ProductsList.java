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

import nhom9.haui.jdbc.ConnectJDBC;

/**
 * Servlet implementation class StudentList
 */
@WebServlet("/ProductList")
public class ProductsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	List<Product> productList = new ArrayList<>();

    	try (Connection cnn = new ConnectJDBC().getConnection();
    	     PreparedStatement pst = cnn.prepareStatement("SELECT * FROM Products");
    	     ResultSet rs = pst.executeQuery()) {

    	    while (rs.next()) {
    	        Product p = new Product(
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
    	        productList.add(p);
    	    }

    	    request.setAttribute("productList", productList);
    	    request.getRequestDispatcher("Products/Home.jsp").forward(request, response);

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    response.getWriter().println("Lỗi khi truy vấn CSDL!");
    	}
    }
}