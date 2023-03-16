package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserErrorCode;
import heedoitdox.deliverysystem.domain.UserRepository;
import heedoitdox.deliverysystem.exception.RestApiException;
import heedoitdox.deliverysystem.security.JwtTokenProvider;
import javax.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String generateAccessToken(UserRequest request) {
        if (userRepository.findByIdentifier(request.getIdentifier()).isPresent()) {
            throw new RestApiException(UserErrorCode.DUPLICATED_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User savedUser = userRepository.save(request.toEntity(encodedPassword));

        return jwtTokenProvider.create(savedUser.getIdentifier());
    }
}
