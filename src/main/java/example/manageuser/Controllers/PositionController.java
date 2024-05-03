package example.manageuser.Controllers;

import example.manageuser.Entities.Position;
import example.manageuser.Repositories.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PositionController {
    @Autowired
    private PositionRepository positionRepository;

    @GetMapping("/userFunctions")
    public List<Position> getUserFunctions(@RequestParam String fullName) {
        return positionRepository.findFunctionsByUserFullName(fullName);
    }
}
