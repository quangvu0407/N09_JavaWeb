package nhom9.haui.Products;

import nhom9.haui.Model.Order;
import nhom9.haui.jdbc.ConnectJDBC;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Products/OrderList")
public class OrderList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OrderList() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách đơn hàng từ CSDL
        List<Order> orderList = getAllOrders();
        System.out.println("Số lượng đơn hàng: " + orderList.size());
        
        // Đặt danh sách đơn hàng vào request để chuyển tiếp tới JSP
        request.getSession().setAttribute("orderList", orderList);

        response.sendRedirect(request.getContextPath() + "/Products/OrderDetail.jsp");
    }

    private List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT o.id, o.customer_name, o.email, o.phone, o.address, o.order_date, o.total, o.paid_amount, p.name, p.price, pr.discount_percent " +
                       "FROM Orders o " +
                       "JOIN OrderDetails od ON o.id = od.order_id " +
                       "JOIN Products p ON od.product_id = p.id " +
                       "LEFT JOIN Promotions pr ON p.promotion_id = pr.id";

        try (Connection conn = new ConnectJDBC().getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Date orderDate = rs.getDate("order_date");
                int total = rs.getInt("total");
                int paidAmount = rs.getInt("paid_amount");
                String productName = rs.getString("name");
                int discount = rs.getInt("discount_percent");
                int price = rs.getInt("price");
                int quantity = 0;
                if(discount == 0) {
                	quantity += total/price;
                }
                else{
                	quantity += total/(price * (100-discount)/100);
                }

                // Tạo đối tượng Order và thêm vào danh sách
                Order order = new Order(id, customerName, email, phone, address, orderDate, total, paidAmount, productName, quantity);
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }
}
