package example.manageuser.Controllers;

import example.manageuser.Config.JWT.JwtUtil;
import example.manageuser.Model.*;
import example.manageuser.Repositories.AuthRepository;
import example.manageuser.Repositories.RoleRepository;
import example.manageuser.Repositories.UsersRepo;
import example.manageuser.Services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthRepository AuthRepository;
    private final RoleRepository RoleRepository;
    private final UsersRepo UsersRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(AuthRepository AuthRepository, RoleRepository RoleRepository, UsersRepo UsersRepo) {
        this.AuthRepository = AuthRepository;
        this.RoleRepository = RoleRepository;
        this.UsersRepo = UsersRepo;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest) {
        return authService.createUser(registerRequest);
    }
}


