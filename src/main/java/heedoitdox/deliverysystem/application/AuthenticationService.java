package heedoitdox.deliverysystem.application;

import static heedoitdox.deliverysystem.domain.UserErrorCode.DUPLICATED_IDENTIFIER;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_IDENTIFIER;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_PASSWORD;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_PASSWORD_FORMAT;

import heedoitdox.deliverysystem.domain.PasswordValidator;
import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserErrorCode;
import heedoitdox.deliverysystem.domain.UserRepository;
import heedoitdox.deliverysystem.exception.RestApiException;
import heedoitdox.deliverysystem.security.JwtTokenProvider;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidator passwordValidator;

    public String generateAccessTokenByRegister(UserRequest request) {
        if (userRepository.findByIdentifier(request.getIdentifier()).isPresent()) {
            throw new RestApiException(DUPLICATED_IDENTIFIER);
        }
        if(!passwordValidator.validate(request.getPassword())) {
            throw new RestApiException(INVALID_PASSWORD_FORMAT);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User savedUser = userRepository.save(request.toEntity(encodedPassword));

        return jwtTokenProvider.create(savedUser.getIdentifier());
    }

    public String generateAccessTokenByLogin(LoginRequest request) {
        User user = userRepository.findByIdentifier(request.getIdentifier())
            .orElseThrow(() -> new RestApiException(INVALID_IDENTIFIER));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RestApiException(INVALID_PASSWORD);
        }

        return jwtTokenProvider.create(user.getIdentifier());
    }
}
