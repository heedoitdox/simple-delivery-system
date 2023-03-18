package heedoitdox.deliverysystem.api;

import heedoitdox.deliverysystem.application.AuthenticationService;
import heedoitdox.deliverysystem.domain.UserErrorCode;
import heedoitdox.deliverysystem.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static heedoitdox.deliverysystem.domain.UserFixture.요청로그인정보;
import static heedoitdox.deliverysystem.domain.UserFixture.요청회원정보;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends RestControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void 회원가입_성공() throws Exception {
        String validAccessToken = "Bearer <token>";
        when(authenticationService.generateAccessTokenByRegister(any()))
            .thenReturn(validAccessToken);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(요청회원정보)))
            .andExpect(status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.accessToken").value(validAccessToken))
            .andDo(document("post-register",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("identifier").description("아이디")
                        .attributes((key("constraint").value("아이디는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("password").description("패스워드")
                        .attributes((key("constraint").value("패스워드는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("name").description("이름")
                        .attributes((key("constraint").value("이름은 공백일 수 없습니다.")))
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("accessToken").description("액세스 토큰")
                )
            ));
    }

    @Test
    void 회원가입_실패() throws Exception {
        given(authenticationService.generateAccessTokenByRegister(any()))
            .willThrow(new RestApiException(UserErrorCode.DUPLICATED_IDENTIFIER));

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(요청회원정보)))
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value("DUPLICATED_IDENTIFIER"))
            .andDo(document("post-register-bad-request",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("identifier").description("아이디")
                        .attributes((key("constraint").value("아이디는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("password").description("패스워드")
                        .attributes((key("constraint").value("패스워드는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("name").description("이름")
                        .attributes((key("constraint").value("이름은 공백일 수 없습니다.")))
                )
            ));
    }

    @Test
    void 로그인_성공() throws Exception {
        String validAccessToken = "Bearer <token>";
        when(authenticationService.generateAccessTokenByLogin(any()))
            .thenReturn(validAccessToken);
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(요청로그인정보)))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.accessToken").value(validAccessToken))
            .andDo(document("post-login",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("identifier").description("아이디")
                        .attributes((key("constraint").value("아이디는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("password").description("패스워드")
                        .attributes((key("constraint").value("패스워드는 공백일 수 없습니다.")))
                ),
                PayloadDocumentation.responseFields(
                    PayloadDocumentation.fieldWithPath("accessToken").description("액세스 토큰")
                )
            ));
    }

    @Test
    void 로그인_실패_사용자_정보가_틀린_경우() throws Exception {
        given(authenticationService.generateAccessTokenByLogin(any()))
            .willThrow(new RestApiException(UserErrorCode.INVALID_IDENTIFIER));

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(요청로그인정보)))
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value("INVALID_IDENTIFIER"))
            .andDo(document("post-login-bad-request",
                PayloadDocumentation.requestFields(
                    PayloadDocumentation.fieldWithPath("identifier").description("아이디")
                        .attributes((key("constraint").value("아이디는 공백일 수 없습니다."))),
                    PayloadDocumentation.fieldWithPath("password").description("패스워드")
                        .attributes((key("constraint").value("패스워드는 공백일 수 없습니다.")))
                )
            ));
    }
}