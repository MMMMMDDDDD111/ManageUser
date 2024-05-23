package example.manageuser.Response;

public class AuthenticationResponse {
    private final String jwt;
    private String refreshToken;

    public AuthenticationResponse(String jwt, String refreshToken) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

    public String getJwt() {
        return jwt;
    }
}
