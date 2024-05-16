package example.manageuser.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "PositionDB")
public class Position {

    @Id
    private long id;

    @Field(value = "positionName")
    private String positionName;

    public Position() {}

    public Position(long id, String positionName) {
        this.id = id;
        this.positionName = positionName;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
