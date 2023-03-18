package heedoitdox.deliverysystem.domain;

import static heedoitdox.deliverysystem.domain.UserFixture.회원정보;

import heedoitdox.deliverysystem.application.DeliveryAddressRequest;
import heedoitdox.deliverysystem.application.DeliveryListRequest;
import heedoitdox.deliverysystem.application.DeliveryListResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryFixture {

    public static Address 주소 = Address.create("관악구", "도림천");

    public static Delivery 배달정보_요청
        = Delivery.create(20000L, LocalDateTime.now(), DeliveryStatus.REQUESTED, 주소, 회원정보);

    public static Delivery 배달정보_완료
        = Delivery.create(20000L, LocalDateTime.now(), DeliveryStatus.COMPLETED, 주소, 회원정보);

    public static List<Delivery> deliveryListOf() {
        List<Delivery> list = new ArrayList<>();
        list.add(배달정보_요청);
        list.add(배달정보_요청);
        list.add(배달정보_요청);

        return list;
    }

    public static List<DeliveryListResponse> deliveryListResponseOf() {
        return deliveryListOf().stream().map(DeliveryListResponse::new)
            .collect(Collectors.toList());
    }

    public static DeliveryAddressRequest 요청_배달주소 = new DeliveryAddressRequest("메인주소", "상세주소");

}
