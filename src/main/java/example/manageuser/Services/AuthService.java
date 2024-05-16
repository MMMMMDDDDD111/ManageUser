package example.manageuser.Services;

import example.manageuser.Model.*;
import example.manageuser.Repositories.AuthRepository;
import example.manageuser.Repositories.RoleRepository;
import example.manageuser.Repositories.UsersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AuthService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    @Lazy
    private PasswordEncoder encoder;


//    public void registerUser(RegisterRequest signUpRequest) {
//        logger.info("Registering user: {}", signUpRequest.getUserName());
//
//        if (authRepository.existsByUserName(signUpRequest.getUserName())) {
//            logger.error("Error: Username {} is already taken!", signUpRequest.getUserName());
//            throw new RuntimeException("Error: Username is already taken!");
//        }
//
//        if (authRepository.existsByEmail(signUpRequest.getEmail())) {
//            logger.error("Error: Email {} is already in use!", signUpRequest.getEmail());
//            throw new RuntimeException("Error: Email is already in use!");
//        }
//
////        Users user = new Users(signUpRequest.getUserName(),
////                encoder.encode(signUpRequest.getPassword()),
////                signUpRequest.getEmail());
//
//        Set<String> strRoles = signUpRequest.getRoles();
//        Set<Role> roles = new HashSet<>();
//
//        if (strRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.Role_User)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//            logger.info("Assigned default role USER to the user {}", signUpRequest.getUserName());
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Role adminRole = roleRepository.findByName(ERole.Role_Admin)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//                        logger.info("Assigned role ADMIN to the user {}", signUpRequest.getUserName());
//                        break;
//
//                    case "mod":
//                        Role modRole = roleRepository.findByName(ERole.Role_Manager)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//                        logger.info("Assigned role MANAGER to the user {}", signUpRequest.getUserName());
//                        break;
//
//                    default:
//                        Role userRole = roleRepository.findByName(ERole.Role_User)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                        logger.info("Assigned default role USER to the user {}", signUpRequest.getUserName());
//                }
//            });
//        }
//
//        user.setRoles(roles);
//        authRepository.save(user);
//        logger.info("User {} registered successfully", signUpRequest.getUserName());
//    }

    public ResponseEntity<?> createUser(RegisterRequest registerRequest) {
        try {
            if (usersRepo.existsByUserName(registerRequest.getUserName())) {
                return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
            }

            if (usersRepo.existsByEmail(registerRequest.getEmail())) {
                return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
            }

            Users user = new Users();
            user.setUserName(registerRequest.getUserName());
            user.setPassword(encoder.encode(registerRequest.getPassword()));
            user.setEmail(registerRequest.getEmail());

            Set<String> userRoles = new HashSet<>();
            for (String roleName : registerRequest.getRoles()) {
                ERole eRole = ERole.valueOf(roleName.toUpperCase());
                Role role = roleRepository.findByName(eRole)
                        .orElseThrow(() -> new RuntimeException("Error: Role " + eRole + " is not found."));
                userRoles.add(role.getName().toString());
            }

            usersRepo.save(user);
            user.setRoles(userRoles);
            logger.info("User saved: {}", user);

            authRepository.save(registerRequest);
            logger.info("Registered user: {}", registerRequest);

            UserResponse userResponse = new UserResponse(registerRequest.getUserName(), encoder.encode(registerRequest.getPassword()),registerRequest.getEmail());

            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while creating user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserDetails loadUserByUsername(String username) {
        Users user = usersRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), new ArrayList<>()
        );
    }

    public boolean existsByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

}

