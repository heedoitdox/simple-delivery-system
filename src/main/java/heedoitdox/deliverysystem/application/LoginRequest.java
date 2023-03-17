package heedoitdox.deliverysystem.application;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    private String identifier;
    @NotBlank(message = "패스워드는 공백일 수 없습니다.")
    private String password;

    public LoginRequest(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }
}
