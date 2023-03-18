package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.PasswordValidator;
import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.domain.UserRepository;
import heedoitdox.deliverysystem.exception.RestApiException;
import heedoitdox.deliverysystem.security.JwtTokenProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static heedoitdox.deliverysystem.domain.UserErrorCode.DUPLICATED_IDENTIFIER;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_IDENTIFIER;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_PASSWORD;
import static heedoitdox.deliverysystem.domain.UserErrorCode.INVALID_PASSWORD_FORMAT;
import static heedoitdox.deliverysystem.domain.UserFixture.요청로그인정보;
import static heedoitdox.deliverysystem.domain.UserFixture.요청회원정보_잘못된_비밀번호;
import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static heedoitdox.deliverysystem.domain.UserFixture.요청회원정보;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

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

    private static MockedStatic<PasswordValidator> passwordValidator;

    @BeforeAll
    public static void beforeALl() {
        passwordValidator = mockStatic(PasswordValidator.class);
    }

    @AfterAll
    public static void afterAll() {
        passwordValidator.close();
    }

    @DisplayName("유저가 새로 등록되면 토큰을 반환한다.")
    @Test
    void registerSuccess() {
        UserRequest request = 요청회원정보;
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        given(PasswordValidator.validate(request.getPassword())).willReturn(true);
        given(passwordEncoder.encode(request.getPassword())).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(회원정보);
        given(jwtTokenProvider.create(회원정보.getIdentifier())).willReturn(VALID_ACCESS_TOKEN);
        given(userRepository.findByIdentifier(any())).willReturn(Optional.empty());

        String actual = authenticationService.generateAccessTokenByRegister(request);

        assertThat(actual).isEqualTo(VALID_ACCESS_TOKEN);
    }

    @DisplayName("회원 가입 시 요청받은 아이디가 중복된다면 에러가 발생한다.")
    @Test
    void duplicatedEmailError() {
        UserRequest request = 요청회원정보;
        Optional<User> optionalUser = Optional.of(회원정보);
        given(userRepository.findByIdentifier(any())).willReturn(optionalUser);

        RestApiException e = assertThrows(RestApiException.class, () -> {
            authenticationService.generateAccessTokenByRegister(request);
        });
        assertEquals(DUPLICATED_IDENTIFIER, e.getErrorCode());
    }

    @DisplayName("회원 가입 시 요청받은 패스워드가 조건을 만족하지 않는다면 에러가 발생한다.")
    @Test
    void invalidPasswordFormatError() {
        UserRequest request = 요청회원정보_잘못된_비밀번호;
        given(userRepository.findByIdentifier(any())).willReturn(Optional.empty());
        given(PasswordValidator.validate(any())).willReturn(false);

        RestApiException e = assertThrows(RestApiException.class, () -> {
            authenticationService.generateAccessTokenByRegister(request);
        });
        assertEquals(INVALID_PASSWORD_FORMAT, e.getErrorCode());
    }

    @DisplayName("로그인 정보가 일치한다면 토큰을 반환한다.")
    @Test
    void loginSuccess() {
        LoginRequest request = 요청로그인정보;
        Optional<User> optionalUser = Optional.of(회원정보);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(jwtTokenProvider.create(any())).willReturn(VALID_ACCESS_TOKEN);
        given(userRepository.findByIdentifier(any())).willReturn(optionalUser);

        String actual = authenticationService.generateAccessTokenByLogin(request);

        assertThat(actual).isEqualTo(VALID_ACCESS_TOKEN);
    }

    @DisplayName("로그인 요청으로 존재하지 않는 아이디가 입력됐을 경우 에러가 발생한다.")
    @Test
    void invalidIdentifierError() {
        LoginRequest request = 요청로그인정보;
        given(userRepository.findByIdentifier(any())).willReturn(Optional.empty());

        RestApiException e = assertThrows(RestApiException.class, () -> {
            authenticationService.generateAccessTokenByLogin(request);
        });
        assertEquals(INVALID_IDENTIFIER, e.getErrorCode());
    }

    @DisplayName("로그인 요청으로 일치하지 않는 비밀번호가 입력됐을 경우 에러가 발생한다.")
    @Test
    void invalidPasswordError() {
        LoginRequest request = 요청로그인정보;
        given(userRepository.findByIdentifier(any())).willReturn(Optional.of(회원정보));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        RestApiException e = assertThrows(RestApiException.class, () -> {
            authenticationService.generateAccessTokenByLogin(request);
        });
        assertEquals(INVALID_PASSWORD, e.getErrorCode());
    }
}