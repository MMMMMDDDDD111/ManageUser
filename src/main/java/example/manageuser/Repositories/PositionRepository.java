package example.manageuser.Repositories;

import example.manageuser.Model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

public interface PositionRepository extends MongoRepository<Position, Long> {
    @Query("{$or: [ { 'id': ?0 }, { 'positionName': ?1 } ]}")
    Optional<Position> findByIdOrPositionName(Long id, String positionName);

    //authentication(Admin,Manager)
    void deleteById(Long id);
}
