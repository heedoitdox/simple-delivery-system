package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.Delivery;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryListResponse {
    private long id;
    private long price;
    private LocalDateTime requestedAt;
    private String status;
    private AddressResponse address;

    public DeliveryListResponse(Delivery delivery) {
        id = delivery.getId();
        price = delivery.getPrice();
        requestedAt = delivery.getRequestedAt();
        status = delivery.getStatus().getValue();
        address = AddressResponse.from(delivery.getAddress());
    }
}
