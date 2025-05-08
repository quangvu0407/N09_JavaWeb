package nhom9.haui.Model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cart {
	private int id;
    private int productId;
    private String productName;
    private int quantity;
    private int price;
    private String image;
    private String username;
    public Cart(int id, int productid, String name, int quantity, int price, String image, String username) {
    	this.id = id;
        this.productId = productid;
        this.productName = name;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.username = username;
    }

	// Getter và Setter
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}