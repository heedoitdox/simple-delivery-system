package heedoitdox.deliverysystem.application;

import heedoitdox.deliverysystem.domain.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddressResponse {
    private final String mainAddress;
    private final String subAddress;

    public static AddressResponse from(Address address) {
        return new AddressResponse(address.getMainAddress(), address.getSubAddress());
    }
}
