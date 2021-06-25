package exportkit.xd.model;

public class User {
    private String uid;
    private String email;
    private String phone;
    private String name;

    public User() {
    }

    public User(String uid, String email, String phone, String name) {
        this.uid = uid;
        this.email = email;
        this.phone = phone;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
