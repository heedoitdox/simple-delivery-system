package heedoitdox.deliverysystem.security;

import heedoitdox.deliverysystem.application.UserService;
import heedoitdox.deliverysystem.domain.User;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginUserResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(LoginUser.class);
        return true;
    }

    @Override
    public User resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) {
        String token = extractBearerToken(webRequest);
        if (Boolean.FALSE.equals(jwtTokenProvider.isValid(token))) {
            throw new AuthenticationFailedException();
        }
        String identifier = jwtTokenProvider.getSubject(token);

        return userService.getByIdentifier(identifier)
            .orElseThrow(UnprocessableException::new);
    }

    private String extractBearerToken(NativeWebRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null) {
            throw new AuthenticationFailedException();
        }
        Map.Entry<String, String> splitToken = splitTokenFormat(authorization);
        if (!Objects.equals(splitToken.getKey(), BEARER)) {
            throw new AuthenticationFailedException();
        }

        return splitToken.getValue();
    }

    private Map.Entry<String, String> splitTokenFormat(String authorization) {
        try {
            String[] tokenFormat = authorization.split(" ");
            return new SimpleEntry<>(tokenFormat[0], tokenFormat[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new AuthenticationFailedException();
        }
    }
}
