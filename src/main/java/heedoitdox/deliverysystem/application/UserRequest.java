package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.User;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "아이디는 공백일 수 없습니다.")
    private String identifier;
    @NotBlank(message = "패스워드는 공백일 수 없습니다.")
    private String password;
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    public UserRequest(String identifier, String password, String name) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
    }

    public User toEntity(String encodedPassword) {
        return User.createUser(identifier, encodedPassword, name);
    }
}
