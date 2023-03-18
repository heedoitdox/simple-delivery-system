package heedoitdox.deliverysystem.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    private String identifier;
    @NotBlank(message = "패스워드는 공백일 수 없습니다.")
    private String password;
}
