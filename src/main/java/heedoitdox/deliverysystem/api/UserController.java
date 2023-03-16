package heedoitdox.deliverysystem.api;

import heedoitdox.deliverysystem.application.AuthenticationService;
import heedoitdox.deliverysystem.application.LoginResponse;
import heedoitdox.deliverysystem.application.UserRequest;
import heedoitdox.deliverysystem.application.UserService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
        @Valid @RequestBody final UserRequest request) {
        final String accessToken = authenticationService.generateAccessToken(request);

        return ResponseEntity.ok().body(LoginResponse.createLoginResponse(accessToken));
    }

}
