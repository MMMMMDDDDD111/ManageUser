package example.manageuser.Controllers;
import example.manageuser.Entities.Position;
import example.manageuser.Entities.User;
import example.manageuser.Repositories.PositionRepository;
import example.manageuser.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;

    @Autowired
    private UserRepository userrepository;

    public UserController(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Validated @RequestBody User user) {
        try {
            logger.info("Received addUser request with user: {}", user);

            Position position = user.getPosition();

            if (position != null && position.getId() != 0L) {
                Optional<Position> existingPosition = positionRepository.findById(position.getId());

                if (existingPosition.isPresent()) {
                    position = existingPosition.get();
                } else {
                    logger.error("Position with id {} not found", position.getId());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            user.setPosition(position);
            User savedUser = userRepository.save(user);
            logger.info("User saved successfully: {}", savedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while adding user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            logger.info("Received updateUser request with id: {} and user: {}", id, user);

            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()) {
                logger.error("User with id {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            User existingUser = userOptional.get();
            existingUser.setFullName(user.getFullName());
            Date hireDate = user.getHireDate();
            if (hireDate != null) {
                existingUser.setHireDate(hireDate);
            }
            Position userPosition = user.getPosition();
            if (userPosition != null) {
                Optional<Position> existingPosition = positionRepository.findByIdOrPositionName(userPosition.getId(), userPosition.getPositionName());
                if (existingPosition.isPresent()) {
                    existingUser.setPosition(existingPosition.get());
                } else {
                    logger.error("Position with id {} or name {} not found", userPosition.getId(), userPosition.getPositionName());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            User updatedUser = userRepository.save(existingUser);
            logger.info("User updated successfully: {}", updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @GetMapping("/showAllUsers")
    public String showAllUsers() {

        List<User> users = this.userrepository.findAll();

        return users.toString();
    }

    @GetMapping("/findUserNo/{userNo}")
    public ResponseEntity<User> findUserByUserNo(@PathVariable("userNo") String userNo) {
        User user = userrepository.findByUserNo(userNo);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @GetMapping("/showUsersHiredAfter/{hireDate}")
    public String showUsersHiredAfter(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date hireDate) {
        List<User> users = this.userRepository.findByHireDateGreaterThan(hireDate);
        return users.toString();
    }

    @ResponseBody
    @RequestMapping("/showFullNameLike")
    public String showFullNameLike(@RequestParam("fullName") String fullName) {
        logger.info("Received request to find users with full name like: {}", fullName);

        List<User> users = this.userrepository.findByFullNameLike("%" + fullName + "%");

        StringBuilder html = new StringBuilder();
        for (User usr : users) {
            html.append(usr).append("<br>");
        }

        return html.toString();
    }

    @ResponseBody
    @RequestMapping("/deleteAllUser")
    public String deleteAllUsers() {
        this.userrepository.deleteAll();
        return "Delete All Successfully";
    }

    @DeleteMapping("/deleteByID/{id}")
    public ResponseEntity<String> removeById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User deletedUser = userOptional.get();
            userRepository.deleteById(id);
            return ResponseEntity.ok("Deleted User: " + deletedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}



