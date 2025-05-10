package nhom9.haui.Products;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nhom9.haui.Model.Admin;
import nhom9.haui.Model.Cart;
import nhom9.haui.Model.Users;
import nhom9.haui.jdbc.ConnectJDBC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/ProductOrderDetail")
public class ProductOrderDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductOrderDetail() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form
    	request.setCharacterEncoding("UTF-8");
        String phone = request.getParameter("phone"); 
        String address = request.getParameter("address");
        String[] selectedIds = request.getParameterValues("selectedIds"); 

        if (selectedIds == null || selectedIds.length == 0) {
        	response.sendRedirect(request.getContextPath() + "/ProductCartList");
            return;
        }
        else {
        	System.out.print(";ay ra dc");
        }
        Users user = (Users) request.getSession().getAttribute("user");
        Admin admin= (Admin) request.getSession().getAttribute("admin");
        if (user != null) {
            if (phone != null && !phone.isEmpty() && !phone.equals(user.getPhone())) {
                user.setPhone(phone);
            }
            if (address != null && !address.isEmpty() && !address.equals(user.getAddress())) {
                user.setAddress(address);
            }

            // Cập nhật thông tin người dùng
            try (Connection cnn = new ConnectJDBC().getConnection()) {
                String sql = "UPDATE Users SET phone = ?, address = ? WHERE id = ?";
                try (PreparedStatement pst = cnn.prepareStatement(sql)) {
                    pst.setString(1, user.getPhone());
                    pst.setString(2, user.getAddress());
                    pst.setInt(3, user.getId());
                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("Lỗi khi cập nhật thông tin!");
                return;
            }
        }
        else if (admin != null) {
            if (phone == null || phone.isEmpty()) {
                phone = admin.getPhone();
            }
            if (address == null || address.isEmpty()) {
                address = admin.getAddress();
            }
        }

        HttpSession session = request.getSession();
        Object userObject = session.getAttribute("user");
        Object adminObject = session.getAttribute("admin");

        String customerName = "";
        String email = "";

        // Lấy thông tin khách hàng từ session
        if (userObject != null) {
            customerName = user.getUsername();
            email = user.getEmail();
        } else if (adminObject != null) {
            admin = (Admin) adminObject;
            customerName = admin.getUsername(); 
            email = admin.getEmail();
        }

        // Lấy thời gian đơn hàng
        LocalDateTime orderTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedOrderTime = orderTime.format(formatter);

        // Lấy giỏ hàng từ session
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");

        // Kiểm tra nếu có sản phẩm trong giỏ hàng
        if (cartList != null) {
            for (String selectedId : selectedIds) {
        		try {
        			Connection cnn = new ConnectJDBC().getConnection();

        			String deleteSql = "DELETE FROM Cart WHERE id = ?;";
        			PreparedStatement pst = cnn.prepareStatement(deleteSql);
        			pst.setInt(1, Integer.parseInt(selectedId));
        			pst.executeUpdate();

        			pst.close();
        			cnn.close();

        		} catch (SQLException e) {
        			e.printStackTrace();
        			response.getWriter().println("Lỗi khi xóa sản phẩm vào giỏ hàng!");
        		}
                for (Cart item : cartList) {
                    if (String.valueOf(item.getId()).equals(selectedId)) {
                        double totalAmount = item.getPrice(); 
                        // Lưu đơn hàng vào cơ sở dữ liệu (1 đơn hàng cho mỗi sản phẩm)
                        try (Connection conn = new ConnectJDBC().getConnection()) {
                            String query = "INSERT INTO Orders (customer_name, email, phone, address, order_date, total, paid_amount) " +
                                           "VALUES (?, ?, ?, ?, ?, ?, ?)";

                            try (PreparedStatement pst = conn.prepareStatement(query)) {
                                pst.setString(1, customerName); 
                                pst.setString(2, email); 
                                pst.setString(3, phone); 
                                pst.setString(4, address); 
                                pst.setString(5, formattedOrderTime);
                                pst.setDouble(6, totalAmount); 
                                pst.setDouble(7, 0); // chưa thanh toán

                                // Thực thi câu lệnh INSERT
                                pst.executeUpdate();
                            }

                            // Lưu chi tiết sản phẩm trong đơn hàng
                            String detailQuery = "INSERT INTO OrderDetails (order_id, product_id, price) VALUES (?, ?, ?)";
                            try (PreparedStatement pstDetail = conn.prepareStatement(detailQuery)) {
                                // Lấy ID đơn hàng mới tạo (ví dụ thông qua câu lệnh SELECT LAST_INSERT_ID() nếu dùng MySQL)
                                int orderId = getLastInsertId(conn); // Giả sử có hàm này để lấy ID đơn hàng
                                pstDetail.setInt(1, orderId);
                                pstDetail.setInt(2, item.getProductId());
                                pstDetail.setDouble(3, item.getPrice());
                                pstDetail.executeUpdate();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            response.getWriter().println("Lỗi khi lưu đơn hàng vào CSDL!");
                        }

                        break; 
                    }
                }
            }
        }

        response.sendRedirect(request.getContextPath() + "/Products/OrderList");
    }

    private int getLastInsertId(Connection conn) throws SQLException {
        String sql = "SELECT LAST_INSERT_ID()"; // Giả sử bạn đang dùng MySQL
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0; // Nếu không có ID mới (lỗi)
    }
}

