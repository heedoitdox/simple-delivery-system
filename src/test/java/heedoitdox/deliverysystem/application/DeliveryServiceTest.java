package heedoitdox.deliverysystem.application;

import static heedoitdox.deliverysystem.domain.DeliveryFixture.deliveryListOf;
import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import heedoitdox.deliverysystem.domain.Delivery;
import heedoitdox.deliverysystem.domain.DeliveryRepository;
import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.security.AuthenticationFailedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {
    @Mock
    private DeliveryRepository deliveryRepository;

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
}