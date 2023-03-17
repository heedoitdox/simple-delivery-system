package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserRepository;
import heedoitdox.deliverysystem.exception.RestApiException;
import heedoitdox.deliverysystem.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static heedoitdox.deliverysystem.domain.UserFixture.회원정보요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    private static final String VALID_ACCESS_TOKEN = "validToken";
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationService authenticationService;

    @DisplayName("유저를 등록할 수 있다.")
    @Test
    void register() {
        UserRequest request = 회원정보요청;
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findByIdentifier(any())).thenReturn(optionalUser);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        given(passwordEncoder.encode(request.getPassword())).willReturn(encodedPassword);

        User expected = 회원정보;
        given(userRepository.save(any(User.class))).willReturn(expected);
        given(jwtTokenProvider.create(expected.getIdentifier())).willReturn(VALID_ACCESS_TOKEN);

        String actual = authenticationService.generateAccessToken(request);

        assertThat(actual).isEqualTo(VALID_ACCESS_TOKEN);
    }

    @DisplayName("요청받은 아이디가 중복된다면 에러가 발생한다.")
    @Test
    void duplicatedEmailError() {
        UserRequest request = 회원정보요청;
        Optional<User> optionalUser = Optional.of(회원정보);
        when(userRepository.findByIdentifier(any())).thenReturn(optionalUser);

        assertThatThrownBy(() -> authenticationService.generateAccessToken(request))
                .isInstanceOf(RestApiException.class);
    }
}