package example.manageuser.Repositories;

import example.manageuser.Model.RegisterRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisterRepository extends MongoRepository<RegisterRequest, String> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    RegisterRequest save(RegisterRequest registerRequest);
}
