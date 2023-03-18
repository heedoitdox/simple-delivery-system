package heedoitdox.deliverysystem.domain;

import heedoitdox.deliverysystem.application.DeliveryListRequest;
import heedoitdox.deliverysystem.application.DeliveryListResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryFixture {
    public static Address 주소 = Address.create("관악구", "도림천");
    public static Delivery 배달정보
        = Delivery.create(20000L, LocalDateTime.now(), DeliveryStatus.REQUESTED, 주소);
    public static List<Delivery> deliveryListOf() {
        List<Delivery> list = new ArrayList<>();
        list.add(배달정보);
        list.add(배달정보);
        list.add(배달정보);

        return list;
    }

    public static List<DeliveryListResponse> deliveryListResponseOf() {
        return deliveryListOf().stream().map(DeliveryListResponse::new).collect(Collectors.toList());
    }

}
