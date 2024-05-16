package example.manageuser.Repositories;

import example.manageuser.Model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<Users, String> {
    Users findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);

    Users findByEmail(String email);
}
