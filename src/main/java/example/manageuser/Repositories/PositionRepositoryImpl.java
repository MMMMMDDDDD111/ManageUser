package example.manageuser.Repositories;

import example.manageuser.Entities.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class PositionRepositoryImpl implements PositionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long UpdatePosition(Position position){
         mongoTemplate.updateFirst(
             Query.query(Criteria.where("id").is(position.getId())),
             Update.update("field", "value"),
             Position.class
         );
        return 0;
    }

}
