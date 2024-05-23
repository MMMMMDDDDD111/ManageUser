package example.manageuser.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "User")
    public class Users {

        @Field(value = "username")
        public String userName;

        @Field(value = "password")
        public String password;

        @Field(value = "email")
        public String email;

        @Field(value = "roles")
        public Set<Role> roles = new HashSet<>();

        public Users(String userName, String password, String email, Set<Role> roles) {
            this.userName = userName;
            this.password = password;
            this.email = email;
            this.roles = roles;
        }

        public Users() {

        }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Users{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}

