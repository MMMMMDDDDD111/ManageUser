package example.manageuser.Repositories;

import example.manageuser.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUserNo(String userNo);
    List<User> findByFullNameLike(String fullName);

    List<User> findByHireDateGreaterThan(Date hireDate);
    void deleteAll();

    @Query("{fullName:'?0'}")
    List<User> findCustomByFullName(String fullName);
    void removeById(Long id);

}
