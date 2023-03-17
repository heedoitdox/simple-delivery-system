package heedoitdox.deliverysystem.security;

import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import heedoitdox.deliverysystem.application.UserService;
import heedoitdox.deliverysystem.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.NativeWebRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class LoginUserResolverTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserService userService;
    @Mock
    private MethodParameter methodParameter;
    @Mock
    private NativeWebRequest nativeWebRequest;

    @InjectMocks
    private LoginUserResolver loginUserResolver;

    @BeforeEach
    void setUp() {
        LoginUser loginUSer = mock(LoginUser.class);
        given(methodParameter.getParameterAnnotation(any())).willReturn(loginUSer);
    }

    @DisplayName("Authorization 헤더가 없으면 예외가 발생한다.")
    @Test
    void NonExistAuthorizationError() {
        given(nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn(null);

        assertThatThrownBy(() -> loginUserResolver
            .resolveArgument(methodParameter, null, nativeWebRequest, null))
            .isInstanceOf(AuthenticationFailedException.class);
    }

    @DisplayName("요청 헤더에 Authorization 으로 해당하는 사용자 정보를 반환한다.")
    @Test
    void getUserByAuthorization() {
        given(nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn("Bearer token");
        given(jwtTokenProvider.isValid(any())).willReturn(true);
        given(jwtTokenProvider.getSubject(any())).willReturn("binzzang810");
        given(userService.getByIdentifier("binzzang810")).willReturn(Optional.of(회원정보));

        User actual = loginUserResolver
            .resolveArgument(methodParameter, null, nativeWebRequest, null);

        assertThat(actual).isEqualTo(회원정보);
    }

    @DisplayName("Authorization 헤더 형식이 올바르지 않을 경우 예외가 발생한다.")
    @Test
    void invalidFormatAuthorizationError() {
        given(nativeWebRequest.getHeader(HttpHeaders.AUTHORIZATION)).willReturn("token");

        assertThatThrownBy(() -> loginUserResolver
            .resolveArgument(methodParameter, null, nativeWebRequest, null))
            .isInstanceOf(AuthenticationFailedException.class);
    }
}