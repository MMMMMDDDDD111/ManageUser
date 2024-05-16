package example.manageuser.Controllers;

import example.manageuser.Model.Position;

import example.manageuser.Repositories.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("/api/position")
public class PositionController {
    private static final Logger logger = LoggerFactory.getLogger(Position.class);

    @Autowired
    private PositionRepository positionrepository;

    private final PositionRepository positionRepository;

    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping("/page")
    public String index(){
        return "addPosition";
    }

    @PostMapping("/addPosition")
    public ResponseEntity<Position> addPosition(@Validated @RequestBody Position position) {
        try {
            logger.info("Received addUser request with user: {}", position);
            Position savedPosition = positionrepository.save(position);
            logger.info("User saved successfully: {}", savedPosition);
            return new ResponseEntity<>(savedPosition, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while adding user: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePosition/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable("id") Long id, @Validated @RequestBody Position newPosition) {
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
    public ResponseEntity<String> removeById(@PathVariable Long id) {
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
