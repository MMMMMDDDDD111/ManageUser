package example.manageuser.Repositories;

import example.manageuser.Entities.Position;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PositionRepository extends MongoRepository<Position, Long> {
    @Query(value = "{ 'users.fullName' : ?0 }", fields = "{ 'functions': 1 }")
    List<Position> findFunctionsByUserFullName(String fullName);

}
