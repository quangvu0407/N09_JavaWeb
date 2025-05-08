package nhom9.haui.Products;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import nhom9.haui.Model.Product;
import nhom9.haui.jdbc.ConnectJDBC;

@WebServlet("/ProductSearch")
public class ProductSearch extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("product");

        String sql = "SELECT * FROM Products WHERE name LIKE ? COLLATE utf8mb4_unicode_ci";
        List<Product> productList = new ArrayList<>();

        try (Connection cnn = new ConnectJDBC().getConnection();
             PreparedStatement pst = cnn.prepareStatement(sql)) {

            pst.setString(1, "%" + keyword + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(
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
            	        productList.add(product);
                }
            }
            System.out.println("Danh sách sản phẩm tìm được: " + keyword +" " + productList.size());
            for (Product p : productList) {
                System.out.println(p.getName());
            }

            request.getSession().setAttribute("productList", productList);
            response.sendRedirect(request.getContextPath() + "/Products/ProductSearch.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
