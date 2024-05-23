package example.manageuser.Model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "roles")
public class Role {

    @Field(value = "RoleName")
    private ERole name;

    public Role() {}
    public Role(ERole name) {
        this.name = name;
    }


    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }


}


