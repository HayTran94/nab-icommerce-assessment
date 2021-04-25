package au.com.nab.icommerce.common.error;

public class ErrorCode {
    public static final int SUCCESS = 0;
    public static final int FAILED = -1;
    public static final int INVALID_DATA = -2;

    // ORDER ERROR CODE
    public static final int ORDER_STATUS_INVALID_FLOW = -100;

    // CART ERROR CODE
    public static final int CART_EMPTY = -200;
}
