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

/**
 * Servlet implementation class ProductsDelete
 */
@WebServlet("/Products/ProductsDelete")
public class ProductsDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("id");
        System.out.println("Sản phẩm ID: " + uid);
        
        Connection cnn = null; // ✅ Khai báo đúng phạm vi ở đây

        try {
            // ✅ Không được khai báo lại cnn ở đây
            cnn = new ConnectJDBC().getConnection();
            cnn.setAutoCommit(false);

            // Xóa các comment
            String deleteCommentsSql = "DELETE FROM Comments WHERE product_id = ?";
            try (PreparedStatement pst = cnn.prepareStatement(deleteCommentsSql)) {
                pst.setInt(1, Integer.parseInt(uid));
                pst.executeUpdate();
            }

            // Xóa chi tiết đơn hàng
            String deleteOrderDetailsSql = "DELETE FROM OrderDetails WHERE product_id = ?";
            try (PreparedStatement pst = cnn.prepareStatement(deleteOrderDetailsSql)) {
                pst.setInt(1, Integer.parseInt(uid));
                pst.executeUpdate();
            }

            // Xóa sản phẩm khỏi giỏ hàng
            String deleteCartSql = "DELETE FROM Cart WHERE product_id = ?";
            try (PreparedStatement pst = cnn.prepareStatement(deleteCartSql)) {
                pst.setInt(1, Integer.parseInt(uid));
                pst.executeUpdate();
            }

            // Xóa sản phẩm chính
            String deleteProductSql = "DELETE FROM Products WHERE id = ?";
            try (PreparedStatement pst = cnn.prepareStatement(deleteProductSql)) {
                pst.setInt(1, Integer.parseInt(uid));
                pst.executeUpdate();
            }

            cnn.commit(); // ✅ Nếu không lỗi thì commit

            // Chuyển hướng sau khi xóa thành công
            response.sendRedirect(request.getContextPath() + "/Products/ProductCatage");

        } catch (SQLException e) {
            if (cnn != null) {
                try {
                    cnn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            response.getWriter().println("Lỗi khi xóa sản phẩm!");
        } finally {
            if (cnn != null) {
                try {
                    cnn.setAutoCommit(true);
                    cnn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}