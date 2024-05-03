package example.manageuser.Repositories;

import com.mongodb.client.result.UpdateResult;
import example.manageuser.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    @Autowired
    MongoTemplate mongoTemplate;

    public long getMaxUserId() {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "id")).limit(1);
        User maxObject = mongoTemplate.findOne(query, User.class);
        if (maxObject == null) {
            return 0L;
        }
        return maxObject.getId();
    }

    @Override
    public long UpdateUser(String userNo, String fullName, Date hireDate) {
        Query query = new Query(Criteria.where("empNo").is(userNo));
        Update update = new Update();
        update.set("fullName", fullName);
        update.set("hireDate", hireDate);

        UpdateResult result = this.mongoTemplate.updateFirst(query, update, User.class);

        if (result != null) {
            return result.getModifiedCount();
        }

        return 0;
    }
}
