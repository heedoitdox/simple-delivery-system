package heedoitdox.deliverysystem.application;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliveryAddressRequest {
    @NotBlank(message = "본 주소는 공백할 수 없습니다.")
    private final String mainAddress;
    @NotBlank(message = "상세 주소는 공백할 수 없습니다.")
    private final String subAddress;
}
