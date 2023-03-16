package heedoitdox.deliverysystem.application;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static LoginResponse createLoginResponse(String accessToken) {
        return new LoginResponse(accessToken);
    }
}
