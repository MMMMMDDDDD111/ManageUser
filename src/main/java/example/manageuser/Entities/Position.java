package example.manageuser.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "PositionDB")
public class Position {
    private String positionName;
    private List<User> userList;

    public Position(String positionName, List<User> userList) {
        this.positionName = positionName;
        this.userList = userList;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
