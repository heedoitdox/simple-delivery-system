package heedoitdox.deliverysystem.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String mainAddress;
    private String subAddress;

    public static Address create(String main, String sub) {
        Address address = new Address();
        address.mainAddress = main;
        address.subAddress = sub;

        return address;
    }
}
