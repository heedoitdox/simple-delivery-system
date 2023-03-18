package heedoitdox.deliverysystem.application;

import static heedoitdox.deliverysystem.domain.DeliveryErrorCode.UNABLE_TO_UPDATE_ADDRESS;
import static heedoitdox.deliverysystem.domain.DeliveryFixture.deliveryListOf;
import static heedoitdox.deliverysystem.domain.DeliveryFixture.배달정보_완료;
import static heedoitdox.deliverysystem.domain.DeliveryFixture.배달정보_요청;
import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static heedoitdox.deliverysystem.domain.UserFixture.회원정보2;
import static heedoitdox.deliverysystem.exception.CommonErrorCode.PERMISSION_DENIED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import heedoitdox.deliverysystem.domain.Delivery;
import heedoitdox.deliverysystem.domain.DeliveryRepository;
import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.exception.RestApiException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    @Mock
    private DeliveryRepository deliveryRepository;

    @Spy
    @InjectMocks
    private DeliveryService deliveryService;

    @DisplayName("배달 목록을 조회한다.")
    @Test
    void lookupDeliveryList() {
        DeliveryListRequest request = new DeliveryListRequest(
            LocalDate.of(2023, 3,15),
            LocalDate.of(2023, 3,  16)
        );
        Pageable pageable = mock(Pageable.class);
        User user = 회원정보;
        given(deliveryRepository.findByRequestedAtBetweenAndUserId(any(), any(), any(), any()))
            .willReturn(new PageImpl<>(deliveryListOf()));

        Page<DeliveryListResponse> actual = deliveryService.findByPeriod(pageable, user, request);
        assertThat(actual.getTotalElements()).isEqualTo( 3);
    }

    @DisplayName("4일 이상의 기간을 조회하면 에러가 발생한다.")
    @Test
    void invalidPeriodExceed4DaysError() {
        DeliveryListRequest request = new DeliveryListRequest(
            LocalDate.of(2023, 3,15),
            LocalDate.of(2023, 3,  20)
        );
        Pageable pageable = mock(Pageable.class);
        User user = 회원정보;

        assertThatThrownBy(() -> deliveryService.findByPeriod(pageable, user, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("종료 날짜가 시작 날짜보다 빠르면 에러가 발생한다.")
    @Test
    void invalidPeriodError() {
        DeliveryListRequest request = new DeliveryListRequest(
            LocalDate.of(2023, 3,20),
            LocalDate.of(2023, 3,  15)
        );
        Pageable pageable = mock(Pageable.class);
        User user = 회원정보;

        assertThatThrownBy(() -> deliveryService.findByPeriod(pageable, user, request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달의 주소를 수정할 수 있다.")
    @Test
    void updateAddress() {
        Delivery delivery = 배달정보_요청;
        User user = 회원정보;
        DeliveryAddressRequest request = new DeliveryAddressRequest("메인수정", "상세수정");
        given(deliveryRepository.findById(any())).willReturn(Optional.of(delivery));

        deliveryService.updateAddress(delivery.getId(), user, request);

        assertThat(delivery.getAddress().getMainAddress()).isEqualTo((request.getMainAddress()));
    }

    @DisplayName("인증된 유저와 조회하려는 배달의 유저가 다르다면 에러가 발생한다.")
    @Test
    void permissionDeniedUserError() {
        Delivery delivery = 배달정보_요청;
        DeliveryAddressRequest request = new DeliveryAddressRequest("메인수정", "상세수정");
        given(deliveryRepository.findById(any())).willReturn(Optional.of(delivery));

        RestApiException e = assertThrows(RestApiException.class, () -> {
            deliveryService.updateAddress(delivery.getId(), 회원정보2, request);
        });
        assertEquals(PERMISSION_DENIED, e.getErrorCode());
    }

    @DisplayName("주소를 수정할 수 없는 배달의 상태라면 에러가 발생한다.")
    @Test
    void unableToUpdateAddressError() {
        Delivery delivery = 배달정보_완료;
        DeliveryAddressRequest request = new DeliveryAddressRequest("메인수정", "상세수정");
        given(deliveryRepository.findById(any())).willReturn(Optional.of(delivery));

        RestApiException e = assertThrows(RestApiException.class, () -> {
            deliveryService.updateAddress(delivery.getId(), delivery.getUser(), request);
        });
        assertEquals(UNABLE_TO_UPDATE_ADDRESS, e.getErrorCode());
    }
}