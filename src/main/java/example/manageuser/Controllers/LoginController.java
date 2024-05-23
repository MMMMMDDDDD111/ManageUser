package example.manageuser.Controllers;

import example.manageuser.Config.JWT.JwtUtil;
import example.manageuser.DTO.TokenDTO;
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


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new AuthenticationResponse(jwt, refreshToken));
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenDTO refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            if (jwtUtil.validateRefreshToken(refreshToken)) {
                String username = jwtUtil.extractUsername(refreshToken);
                UserDetails userDetails = authService.loadUserByUsername(username);

                String newAccessToken = jwtUtil.generateToken(userDetails.getUsername());
                ResponseCookie newJwtCookie = jwtUtil.generateJwtCookie(userDetails);

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, newJwtCookie.toString())
                        .body(new AuthenticationResponse(newAccessToken, refreshToken));
            } else {
                return new ResponseEntity<>("Invalid refresh token", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

