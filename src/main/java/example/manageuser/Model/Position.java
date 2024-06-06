package example.manageuser.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "PositionDB")
public class Position {

    @Id
    private String id;

    @Field(value = "positionName")
    private String positionName;

    @DBRef
    @JsonIgnore
    private List<User> users;

    public Position() {}

    public Position(String id, List<User> users, String positionName) {
        this.id = id;
        this.users = users;
        this.positionName = positionName;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                ", users=" + users +
                '}';
    }
}
