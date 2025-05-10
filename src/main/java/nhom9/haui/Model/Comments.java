package nhom9.haui.Model;

import java.util.Date;

public class Comments {
	private int id;
	private String email;
	private String content;
	private Date comment_date;
	private int product_id;
	private int user_id;
	private String user_name;
	
	public Comments(int id, String email, String content, Date comment_date, int product_id, int user_id, String user_name) {
		this.id = id;
		this.email = email;
		this.content = content;
		this.comment_date = comment_date;
		this.product_id = product_id;
		this.user_id = user_id;
		this.user_name = user_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getComment_date() {
		return comment_date;
	}

	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}



