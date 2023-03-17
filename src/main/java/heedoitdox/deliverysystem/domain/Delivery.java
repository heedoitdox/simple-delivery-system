package heedoitdox.deliverysystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private Long price;

    @Column(name = "requested_at", columnDefinition = "datetime")
    private LocalDateTime requestedAt;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    public static Delivery create(
        Product product,
        Long price,
        LocalDateTime requestedAt,
        DeliveryStatus deliveryType,
        Address address
    ) {
        Delivery delivery = new Delivery();
        delivery.product = product;
        delivery.price = price;
        delivery.requestedAt = requestedAt;
        delivery.status = deliveryType;
        delivery.address = address;

        return delivery;
    }
}
