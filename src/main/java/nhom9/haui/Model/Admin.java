package nhom9.haui.Model;

public class Admin {
    private int id;
    private String username;
    private String email;
    private String password;
    private int level; // 0 = staff, 1 = super admin

    // Constructors
    public Admin() {
    }

    public Admin(int id, String username, String email, String password, int level) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    // Optional: Display method
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", level=" + level +
                '}';
    }
}
