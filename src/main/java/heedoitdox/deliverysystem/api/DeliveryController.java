package heedoitdox.deliverysystem.api;

import static heedoitdox.deliverysystem.exception.CommonErrorCode.INVALID_PARAMETER;

import heedoitdox.deliverysystem.application.DeliveryAddressRequest;
import heedoitdox.deliverysystem.application.DeliveryListRequest;
import heedoitdox.deliverysystem.application.DeliveryListResponse;
import heedoitdox.deliverysystem.application.DeliveryService;
import heedoitdox.deliverysystem.domain.User;
import heedoitdox.deliverysystem.exception.RequestParamBindException;
import heedoitdox.deliverysystem.security.LoginUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/delivery")
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping()
    public ResponseEntity<Page<DeliveryListResponse>> getList(
        @LoginUser User user,
        @PageableDefault(size = 5) Pageable pageable,
        @Valid @ModelAttribute DeliveryListRequest request,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new RequestParamBindException(INVALID_PARAMETER, result.getFieldErrors());
        }
        Page<DeliveryListResponse> response = deliveryService.findByPeriod(pageable, user, request);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> patchAddress(
        @LoginUser User user,
        @PathVariable("id") Long id,
        @Valid @RequestBody DeliveryAddressRequest request
    ) {
        deliveryService.updateAddress(id, user, request);

        return ResponseEntity.ok().build();
    }
}
