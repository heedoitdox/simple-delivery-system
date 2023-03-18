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
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private Long price;

    @Column(name = "requested_at", columnDefinition = "datetime")
    private LocalDateTime requestedAt;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    private Address address;

    public static Delivery create(
        Long price,
        LocalDateTime requestedAt,
        DeliveryStatus deliveryType,
        Address address
    ) {
        Delivery delivery = new Delivery();
        delivery.id = 0L;
        delivery.price = price;
        delivery.requestedAt = requestedAt;
        delivery.status = deliveryType;
        delivery.address = address;

        return delivery;
    }
}
