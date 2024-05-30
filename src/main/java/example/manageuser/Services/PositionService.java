package example.manageuser.Services;

import example.manageuser.DTO.PositionDTO;
import example.manageuser.Model.Position;
import example.manageuser.Model.User;
import example.manageuser.Repositories.PositionRepository;
import example.manageuser.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;

    public Position createPosition(PositionDTO positionRequest) {
        Position position = new Position();
        position.setPositionName(positionRequest.getPositionName());
        return savePosition(position);
    }

    public List<User> retrieveAssignedUsers(List<Long> userIds) {
        return userRepository.findAllById(userIds);
    }

    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    public void assignUsersToPosition(Position position, List<User> assignedUsers) {
        for (User user : assignedUsers) {
            user.setPosition(position);
        }
        userRepository.saveAll(assignedUsers);
        position.setUsers(assignedUsers);
        positionRepository.save(position);
    }
}
