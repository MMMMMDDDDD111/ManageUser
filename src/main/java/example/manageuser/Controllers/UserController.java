package example.manageuser.Controllers;
import example.manageuser.Model.Position;
import example.manageuser.Model.RegisterRequest;
import example.manageuser.Model.User;
import example.manageuser.Repositories.PositionRepository;
import example.manageuser.Repositories.UserRepository;
import example.manageuser.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final UserService userService;

    @Autowired
    private UserRepository userrepository;

    public UserController(UserRepository userRepository, PositionRepository positionRepository, UserService userService) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ResponseEntity<User> showForm(@Validated @RequestBody User user) {
        try {
                logger.info("Save successfully: {}", user);
            User savedUser = userrepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Save to be error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Validated @RequestBody User user) {
        try {
            logger.info("Received addUser request with user: {}", user);

            //kiem tra userNO user ton tai trong csdl
            User existingUser = userRepository.findByUserNo(user.getUserNo());
            if (existingUser != null) {
                logger.error("User with User No {} already exists", user.getUserNo());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            //kiem tra id position ton tai trong csdl
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

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateEmployee = userService.updateUser(user);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }

    @GetMapping("/showAllUsers")
    public List<User> showAllUsers() {
        return userRepository.findAll();
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
        return users.toString();
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



