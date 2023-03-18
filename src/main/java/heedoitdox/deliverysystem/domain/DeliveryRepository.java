package heedoitdox.deliverysystem.domain;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Page<Delivery> findByRequestedAtBetweenAndUserId(
        LocalDateTime start,
        LocalDateTime end,
        Long userId,
        Pageable pageable
    );
}
