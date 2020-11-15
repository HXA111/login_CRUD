package vo;

public class User {
    private String userName;
    private String password;
    private String chrName;
    private String email;
    private String province;
    private String city;

    public User(String userName, String password, String chrName, String email, String province, String city) {
        this.userName = userName;
        this.password = password;
        this.chrName = chrName;
        this.email = email;
        this.province = province;
        this.city = city;
    }

    public User() {
    }

    public User(String userName, String password, String chrName) {
        this.userName = userName;
        this.password = password;
        this.chrName = chrName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChrName() {
        return chrName;
    }

    public void setChrName(String chrName) {
        this.chrName = chrName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", chrName='" + chrName + '\'' +
                ", email='" + email + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
