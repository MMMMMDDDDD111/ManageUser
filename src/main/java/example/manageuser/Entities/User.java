package example.manageuser.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

    @Document(collection = "MYDB")
    public class User {
        private static final Logger logger = LoggerFactory.getLogger(User.class);

        @Id
        private Long id;

        @Indexed(unique = true)
        @Field(value = "User_No")
        private String userNo;

        @Field(value = "fullName")
        private String fullName;

        @Field(value = "Hire_Date")
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
            logger.info("User created with User No: {}, Full Name: {}, Hire Date: {}", userNo, fullName, hireDate);
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", userNo='" + userNo + '\'' +
                    ", fullName='" + fullName + '\'' +
                    ", hireDate=" + hireDate +
                    ", position=" + position +
                    '}';
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
