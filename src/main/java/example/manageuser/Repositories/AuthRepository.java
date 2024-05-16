package example.manageuser.Repositories;

import example.manageuser.Model.RegisterRequest;
import example.manageuser.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthRepository extends MongoRepository <RegisterRequest, String>{
    Optional<RegisterRequest> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

}
