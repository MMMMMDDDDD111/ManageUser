package example.manageuser.Services;

import example.manageuser.Model.Position;
import example.manageuser.Model.RegisterRequest;
import example.manageuser.Model.User;
import example.manageuser.Repositories.AuthRepository;
import example.manageuser.Repositories.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository UserRepository;

    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }
    public User addUser(String userNo, String fullName, Date hireDate, Position position) {
        User newUser = new User(userNo, fullName, hireDate, position);
        return UserRepository.save(newUser);
    }
    public User updateUser(User user) {
        return UserRepository.save(user);
    }

}

