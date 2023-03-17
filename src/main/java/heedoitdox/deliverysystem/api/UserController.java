package heedoitdox.deliverysystem.api;

import heedoitdox.deliverysystem.application.*;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
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
        final String accessToken = authenticationService.generateAccessTokenByRegister(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(LoginResponse.createLoginResponse(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody final LoginRequest request) {
        final String accessToken = authenticationService.generateAccessTokenByLogin(request);

        return ResponseEntity.ok().body(LoginResponse.createLoginResponse(accessToken));
    }
}
