package example.manageuser.Repositories;

import example.manageuser.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {

    User findByUserNo(String userNo);

    List<User> findByFullNameLike(String fullName);

    List<User> findByHireDateGreaterThan(Date hireDate);

    void deleteAll();

}
