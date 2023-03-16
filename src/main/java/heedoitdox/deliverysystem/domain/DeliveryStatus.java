package heedoitdox.deliverysystem.domain;

public enum DeliveryStatus {
    REQUESTED("REQUESTED"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    DeliveryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
