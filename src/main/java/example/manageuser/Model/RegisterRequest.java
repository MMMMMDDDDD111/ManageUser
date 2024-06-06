package example.manageuser.Model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;


public class RegisterRequest {

    private String userName;
    private String email;
    private String address;
    private String password;
    private String confirmPassword;
    private Set<String> roles;

    public RegisterRequest() {}

    public RegisterRequest(String userName, String email, String address, String password,
                           Set<String> roles, String confirmPassword) {
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.roles = roles;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
