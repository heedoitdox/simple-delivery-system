package heedoitdox.deliverysystem.api;

import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import heedoitdox.deliverysystem.security.AuthenticationFailedException;
import heedoitdox.deliverysystem.security.LoginUser;
import heedoitdox.deliverysystem.security.LoginUserResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public abstract class RestControllerTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected RestDocumentationContextProvider restDocumentation;

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private LoginUserResolver loginUserResolver;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();

        when(loginUserResolver.supportsParameter(any())).thenAnswer(invocation ->
            invocation.getMethod().isAnnotationPresent(LoginUser.class));
        NativeWebRequest request = mock(NativeWebRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");
        when(loginUserResolver.resolveArgument(any(), any(), any(), any())).thenAnswer(invocation -> {
            ArgumentCaptor<NativeWebRequest> captor = ArgumentCaptor.forClass(NativeWebRequest.class);
            String token = captor.getValue().getHeader(HttpHeaders.AUTHORIZATION);
            if (token == null || !token.startsWith("Bearer")) {
                throw new AuthenticationFailedException("인증 정보를 확인해 주세요.");
            }
            return 회원정보;
        });
    }
}