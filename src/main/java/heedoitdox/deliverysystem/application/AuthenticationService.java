package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserErrorCode;
import heedoitdox.deliverysystem.domain.UserRepository;
import heedoitdox.deliverysystem.exception.RestApiException;
import heedoitdox.deliverysystem.security.JwtTokenProvider;
import javax.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateAccessTokenByRegister(UserRequest request) {
        if (userRepository.findByIdentifier(request.getIdentifier()).isPresent()) {
            throw new RestApiException(UserErrorCode.DUPLICATED_IDENTIFIER);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User savedUser = userRepository.save(request.toEntity(encodedPassword));

        return jwtTokenProvider.create(savedUser.getIdentifier());
    }

    public String generateAccessTokenByLogin(LoginRequest request) {
        User user = userRepository.findByIdentifier(request.getIdentifier())
            .orElseThrow(() -> new RestApiException(UserErrorCode.INVALID_IDENTIFIER));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RestApiException(UserErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.create(user.getIdentifier());
    }
}
