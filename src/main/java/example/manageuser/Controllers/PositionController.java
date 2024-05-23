package example.manageuser.Controllers;

import example.manageuser.DTO.PositionDTO;
import example.manageuser.Model.Position;
import example.manageuser.Model.User;

import example.manageuser.Repositories.PositionRepository;
import example.manageuser.Repositories.UserRepository;
import example.manageuser.Response.ResponseMessage;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RestController
@RequestMapping("/api/position")
public class PositionController {
    private static final Logger logger = LoggerFactory.getLogger(Position.class);

    @Autowired
    private PositionRepository positionrepository;

    @Autowired
    private UserRepository userrepository;

    private final PositionRepository positionRepository;

    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping("/page")
    public String index(){
        return "addPosition";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Position>> getAllPositions() {
        try {
            logger.info("Retrieving all positions");
            List<Position> positions = positionrepository.findAll();
            logger.info("Found {} positions", positions.size());
            return new ResponseEntity<>(positions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving positions: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addPosition")
    public ResponseEntity<?> addPosition(@Validated @RequestBody PositionDTO positionRequest) {
        try {
            // Tạo một đối tượng Position mới và thiết lập tên
            Position position = new Position();
            position.setPositionName(positionRequest.getPositionName());

            // Lấy danh sách User từ cơ sở dữ liệu
            List<User> users = userrepository.findAllById(positionRequest.getUserIds());

            // Kiểm tra xem tất cả các User có tồn tại không
            if (users.size() != positionRequest.getUserIds().size()) {
                return new ResponseEntity<>(new ResponseMessage("Some users not found"), HttpStatus.BAD_REQUEST);
            }

            Position savedPosition = positionRepository.save(position);

            for (User user : users) {
                user.setPosition(savedPosition);
            }
            userrepository.saveAll(users);

            savedPosition.setUsers(users);
            positionRepository.save(savedPosition);

            return new ResponseEntity<>(savedPosition, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while adding position: {}", e.getMessage());
            return new ResponseEntity<>(new ResponseMessage("An error occurred while adding the position"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updatePosition/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable("id") String id, @Validated @RequestBody Position newPosition) {
        try {
            Optional<Position> existingPositionOptional = positionRepository.findById(id);
            if (!existingPositionOptional.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Position existingPosition = existingPositionOptional.get();
            existingPosition.setPositionName(newPosition.getPositionName());
            Position updatedPosition = positionRepository.save(existingPosition);
            return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @RequestMapping("/deleteAllPosition")
    public String deleteAllUsers() {
        this.positionrepository.deleteAll();
        return "Deleted All Successfully";
    }

    @DeleteMapping("/deleteByID/{id}")
    public ResponseEntity<String> removeById(@PathVariable String id) {
        Optional<Position> positionOptional = positionrepository.findById(id);
        if (positionOptional.isPresent()) {
            Position deletedUser = positionOptional.get();
            positionrepository.deleteById(id);
            return ResponseEntity.ok("Deleted User: " + deletedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
