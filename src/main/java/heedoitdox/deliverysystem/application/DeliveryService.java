package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.Delivery;
import heedoitdox.deliverysystem.domain.DeliveryRepository;
import heedoitdox.deliverysystem.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Page<DeliveryListResponse> findByPeriod(
        Pageable pageable,
        User user,
        DeliveryListRequest request
    ) {
        LocalDate endDate;
        if(request.getEndDate() == null) endDate = request.getStartDate();
        else {
            if (Period.between(request.getStartDate(), request.getEndDate()).getDays() > 2L) {
                throw new IllegalArgumentException("최대 3일까지의 기간을 조회할 수 있어요.");
            }
            if (request.getStartDate().isAfter(request.getEndDate())) {
                throw new IllegalArgumentException("종료 날짜가 시작 날짜보다 빠를 수 없어요.");
            }
            endDate = request.getEndDate();
        }

        LocalDateTime start = LocalDateTime.of(request.getStartDate(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.MAX);
        Page<Delivery> deliveryList = deliveryRepository
            .findByRequestedAtBetweenAndUserId(start, end, user.getId(), pageable);

        List<DeliveryListResponse> response =
            deliveryList.stream().map(DeliveryListResponse::new).collect(Collectors.toList());

        return new PageImpl<>(response, pageable, deliveryList.getTotalElements());
    }
}
