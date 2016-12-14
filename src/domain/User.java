package domain;

public class User extends Entity {
    private String login;
    private String password;
    private Role role;
    private String fullName;
    private Integer zipCode;
    private String address;

    
    public User(String login, String password, Role role,
            String fullName, Integer zipCode, String address) {
        super();
        this.login = login;
        this.password = password;
        this.role = role;
        
        this.fullName = fullName;
        this.zipCode = zipCode;
        this.address = address; 
    }

    public User() {
        super();
    }
    
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
            return "User [login="+login + ", password=" + password + ", role="
                            + role + ", fullName=" + fullName + ", zipCode=" + zipCode
                            + ", address=" + address + "]";
            // переделать формат
    }

}