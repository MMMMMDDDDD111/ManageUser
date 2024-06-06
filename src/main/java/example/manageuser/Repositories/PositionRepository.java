package example.manageuser.Repositories;

import example.manageuser.Model.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.Optional;

public interface PositionRepository extends MongoRepository<Position, String> {
    @Query("{$or: [ { '_id': ?0 }, { 'positionName': ?1 } ]}")
    Optional<Position> findByIdOrPositionName(String id, String positionName);

    void deleteById(String id);

    Optional<Position> findByPositionName(String positionName);
}

