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

import nhom9.haui.Model.Product;
import nhom9.haui.Model.Users;
import nhom9.haui.Model.Admin;
import nhom9.haui.jdbc.ConnectJDBC;

@WebServlet("/Products/ProductLogin")
public class ProductLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductLogin() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // email
        String password = request.getParameter("password");
        List<Product> productList = new ArrayList<>();

        try (Connection cnn = new ConnectJDBC().getConnection()) {

            // Check Users table
            try (PreparedStatement pst = cnn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?")) {
                pst.setString(1, username);
                pst.setString(2, password);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Users user = new Users(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")
                        );
                        request.getSession().setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + "/ProductList");
                        return;
                    }
                }
            }

            // Check Admins table
            try (PreparedStatement pst = cnn.prepareStatement("SELECT * FROM Admins WHERE email = ? AND password = ?")) {
                pst.setString(1, username);
                pst.setString(2, password);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("level")
                        );
                        request.getSession().setAttribute("admin", admin);
                        response.sendRedirect(request.getContextPath() + "/ProductList");
                        return;
                    }
                }
            }

            // Nếu không đúng cả Users và Admins
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi truy vấn CSDL!");
        }
    }
}
