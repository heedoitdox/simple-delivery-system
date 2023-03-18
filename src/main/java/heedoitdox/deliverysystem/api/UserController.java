package heedoitdox.deliverysystem.api;

import heedoitdox.deliverysystem.application.*;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
        @Valid @RequestBody final UserRequest request) {
        final String accessToken = authenticationService.generateAccessTokenByRegister(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new LoginResponse(accessToken));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody final LoginRequest request) {
        final String accessToken = authenticationService.generateAccessTokenByLogin(request);

        return ResponseEntity.ok().body(new LoginResponse(accessToken));
    }
}
