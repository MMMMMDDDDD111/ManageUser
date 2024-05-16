package example.manageuser;

import com.mongodb.client.MongoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class ManageUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageUserApplication.class, args);
        System.out.println("Hello World!");
    }
    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient, MongoMappingContext context) {
        MongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(new SimpleMongoClientDatabaseFactory("mongodb+srv://duyvhtgcs190565:123456Aa@cluster0.wdfkxgs.mongodb.net/mydb")), context);
        ((MappingMongoConverter) converter).setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb+srv://duyvhtgcs190565:123456Aa@cluster0.wdfkxgs.mongodb.net/mydb"), converter);
    }
}
