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

import nhom9.haui.Model.Product;
import nhom9.haui.Model.Users;
import nhom9.haui.Model.Admin;
import nhom9.haui.jdbc.ConnectJDBC;

@WebServlet("/ProductLogin")
public class ProductLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProductLogin() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String username = null;
    	String password = null;
        username = request.getParameter("username");
        password = request.getParameter("password");

        try (Connection cnn = new ConnectJDBC().getConnection()) {

            // Kiểm tra trong bảng Users
            try (PreparedStatement pst = cnn.prepareStatement(
                    "SELECT * FROM Users WHERE email = ? AND password = ?")) {
                pst.setString(1, username);
                pst.setString(2, password);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Users user = new Users(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("address")
                        );
                        request.getSession().setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + "/ProductList");
                        return;
                    }
                }
            }

            // Kiểm tra trong bảng Admins
            try (PreparedStatement pst = cnn.prepareStatement(
                    "SELECT * FROM Admins WHERE email = ? AND password = ?")) {
                pst.setString(1, username);
                pst.setString(2, password);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Admin admin = new Admin(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getInt("level")
                        );
                        request.getSession().setAttribute("admin", admin);
                        response.sendRedirect(request.getContextPath() + "/ProductList");
                        return;
                    }
                }
            }

            // Sai tài khoản hoặc mật khẩu
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            response.sendRedirect(request.getContextPath() + "/Products/Login.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi truy vấn CSDL!");
        }
    }
}
