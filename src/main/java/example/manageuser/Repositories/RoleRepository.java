package example.manageuser.Repositories;

import example.manageuser.Model.ERole;
import example.manageuser.Model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
