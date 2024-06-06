package example.manageuser.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

    @Document(collection = "MYDB")
    public class User implements Serializable {

        @Id
        private Long id;

        @Indexed(unique = true)
        @Field(value = "User_No")
        private String userNo;

        @Field(value = "fullName")
        private String fullName;

        @Field(value = "Hire_Date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date hireDate;

        @DBRef
        private Position position;

        public User() {

        }

        public User(String userNo, String fullName, Date hireDate, Position position) {
            this.userNo = userNo;
            this.fullName = fullName;
            this.hireDate = hireDate;
            this.position = position;
        }
        
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUserNo() {
            return userNo;
        }

        public void setUserNo(String userNo) {
            this.userNo = userNo;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public Date getHireDate() {
            return hireDate;
        }

        public void setHireDate(Date hireDate) {
            this.hireDate = hireDate;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }
    }
