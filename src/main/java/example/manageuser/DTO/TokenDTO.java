package example.manageuser.DTO;

public class TokenDTO {
    private String userID;
    private String accessToken;
    private String refreshToken;

    public TokenDTO(String userID, String accessToken, String refreshToken) {
        this.userID = userID;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
