package heedoitdox.deliverysystem.api;

import static heedoitdox.deliverysystem.domain.DeliveryFixture.deliveryListResponseOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import heedoitdox.deliverysystem.application.DeliveryListResponse;
import heedoitdox.deliverysystem.application.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class DeliveryControllerTest extends RestControllerTest {

    @MockBean
    private DeliveryService deliveryService;

    @Test
    void 배달_목록_조회_성공() throws Exception {
        Page<DeliveryListResponse> response = new PageImpl<>(deliveryListResponseOf());
        when(deliveryService.findByPeriod(any(), any(), any())).thenReturn(response);

        mockMvc.perform(get("/api/delivery")
                .header(HttpHeaders.AUTHORIZATION, "Bearer <accessToken>")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "asc")
                .param("startDate", "2023-03-25")
                .param("endDate", "2023-03-26"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andDo(document("get-delivery-list",
                requestHeaders(
                    headerWithName("Authorization").description("인증 토큰")
                ),
                requestParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("사이즈 번호"),
                    parameterWithName("sort").description("정렬"),
                    parameterWithName("startDate").description("조회 시작 날짜"),
                    parameterWithName("endDate").description("조회 끝 날짜")
                ),
                responseFields(
                    fieldWithPath("content").type(JsonFieldType.ARRAY).description("결과 데이터"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("배달 ID"),
                    fieldWithPath("content[].price").type(JsonFieldType.NUMBER).description("배달비"),
                    fieldWithPath("content[].requestedAt").type(JsonFieldType.STRING)
                        .description("요청 일시"),
                    fieldWithPath("content[].status").type(JsonFieldType.STRING).description(
                        "배달 상태: REQUESTED(요청), IN_PROGRESS(배달중), COMPLETED(완료), CANCELED(취소)"),
                    fieldWithPath("content[].address").type(JsonFieldType.OBJECT)
                        .description("배달지"),
                    fieldWithPath("content[].address.mainAddress").type(JsonFieldType.STRING)
                        .description("배달지: 메인주소"),
                    fieldWithPath("content[].address.subAddress").type(JsonFieldType.STRING)
                        .description("배달지: 상세주소"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                        .description("전체 데이터 수"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 여부"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 여부"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER)
                        .description("데이터의 수"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("사이즈"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER).description("번호"),
                    fieldWithPath("sort").type(JsonFieldType.OBJECT).description("정렬"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("비어있음 여부"),
                    fieldWithPath("pageable").type(JsonFieldType.STRING).description("-"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬됨 여부"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("정렬안됨 여부"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("비어있음 여부")
                )));
    }
}