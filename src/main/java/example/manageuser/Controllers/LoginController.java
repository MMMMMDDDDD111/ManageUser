package example.manageuser.Controllers;

import example.manageuser.Config.JWT.JwtUtil;
import example.manageuser.Model.LoginRequest;
import example.manageuser.Repositories.UsersRepo;
import example.manageuser.Response.AuthenticationResponse;
import example.manageuser.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

//    @GetMapping("/login")
//    public String login(Model model, CsrfToken token) {
//        model.addAttribute("user");
//        return "login";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);

            String jwt = jwtUtil.generateToken(userDetails.getUsername());

            // Trả về phản hồi với cookie JWT và JWT trong body
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new AuthenticationResponse(jwt));
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

}

