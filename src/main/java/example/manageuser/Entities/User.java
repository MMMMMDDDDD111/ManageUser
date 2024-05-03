package example.manageuser.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

    @Document(collection = "MYDB")
    public class User {

        @Id
        private Long id;

        @Indexed(unique = true)
        @Field(value = "User_No")
        private String userNo;

        @Field(value = "fullName")
        private String fullName;

        @Field(value = "Hire_Date")
        private Date hireDate;

        public User() {}

        public User( String userNo, String fullName, String hireDate) {
            this.userNo = userNo;
            this.fullName = fullName;
            this.hireDate = new Date();
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

        @Override
        public String toString() {
            return "id:" + this.id + ", userNo: " + userNo //
                    + ", fullName: " + this.fullName + ", hireDate: " + this.hireDate;
        }
}
