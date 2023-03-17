package heedoitdox.deliverysystem.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final long INVALID_EXPIRATION_TIME = -100L;
    private static final String INVALID_TOKEN = "@#$ㄷㄹㄴㄷ523$";

    @DisplayName("유효 기간이 지난 토큰은 유효성 검사에 실패한다.")
    @Test
    void expiredTokenFail() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(INVALID_EXPIRATION_TIME);
        String token = jwtTokenProvider.create("binzzang810");

        boolean actual = jwtTokenProvider.isValid(token);

        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("올바르지 않은 형식의 토큰은 유효성 검사에 실패한다.")
    @Test
    void invalidFormatTokenFail() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        Boolean actual = jwtTokenProvider.isValid(INVALID_TOKEN);

        assertThat(actual).isEqualTo(false);
    }

    @DisplayName("아이디를 payload 로 생성한 토큰에서 다시 아이디를 얻는다.")
    @Test
    void getIdentifierFromToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = jwtTokenProvider.create("binzzang810");

        String actual = jwtTokenProvider.getSubject(token);

        assertThat(actual).isEqualTo("binzzang810");
    }
}