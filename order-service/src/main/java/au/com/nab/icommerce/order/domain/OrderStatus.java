package au.com.nab.icommerce.order.domain;

public enum OrderStatus {
    UNKNOWN,
    INIT,
    PROCESSING,
    DELIVERING,
    COMPLETED,
    DROP_OFF_FAIL,
}
