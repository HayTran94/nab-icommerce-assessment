package au.com.nab.icommerce.api.gateway.common;


import au.com.nab.icommerce.common.error.ErrorCodeHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiMessage {

    public static ApiMessage SUCCESS = new ApiMessage(0, "Success");
    public static ApiMessage FAILED = new ApiMessage(-1, "Failed");
    public static ApiMessage INVALID_DATA = new ApiMessage(-2, "Invalid data");
    public static ApiMessage UNKNOWN_EXCEPTION = new ApiMessage(-3, "Unknown exception");
    public static ApiMessage CREATE_FAILED = new ApiMessage(-4, "Create failed");
    public static ApiMessage UPDATE_FAILED = new ApiMessage(-5, "Update failed");
    public static ApiMessage LOGIN_FAILED = new ApiMessage(-6, "Login failed");
    public static ApiMessage INVALID_FACEBOOK_APP_DATA = new ApiMessage(-7, "Invalid Facebook app data");

    // CART ERROR CODE
    public static ApiMessage CART_ITEMS_INVALID = new ApiMessage(-100, "Cart items are invalid");
    public static ApiMessage CART_ITEMS_EMPTY = new ApiMessage(-101, "Cart items is empty");
    public static ApiMessage CART_ADD_ITEMS_FAILED = new ApiMessage(-102, "Add to cart failed");
    public static ApiMessage CART_CLEAR_FAILED = new ApiMessage(-103, "Clear cart failed");
    public static ApiMessage CART_EMPTY = new ApiMessage(-103, "Cart is empty");

    // CUSTOMER ERROR CODE
    public static ApiMessage CUSTOMER_NOT_FOUND = new ApiMessage(-200, "Customer not found");
    public static ApiMessage CUSTOMER_VIOLATION = new ApiMessage(-200, "Customer violation");

    // PRODUCT ERROR CODE
    public static ApiMessage PRODUCT_NOT_FOUND = new ApiMessage(-300, "Product not found");

    // ORDER ERROR CODE
    public static ApiMessage ORDER_CREATE_FAILED = new ApiMessage(-400, "Place order failed");
    public static ApiMessage ORDER_NOT_FOUND = new ApiMessage(-401, "Order not found");
    public static ApiMessage ORDER_INVALID_STATUS_FLOW = new ApiMessage(-402, "Can not change order status from %s to %s");


    private int code;
    @Setter
    private String message;
    @Setter
    private Object data;

    ApiMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public ApiMessage clone() {
        ApiMessage instance = new ApiMessage(code, message);
        instance.setData(this.getData());
        return instance;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ErrorCodeHelper.isSuccess(this.code);
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    @JsonIgnore
    public ApiMessage setMessageArgs(Object... args) {
        ApiMessage instance = this.clone();
        String message = String.format(instance.getMessage(), args);
        instance.setMessage(message);
        return instance;
    }

    public static ApiMessage success(Object data) {
        ApiMessage instance = ApiMessage.SUCCESS.clone();
        instance.setData(data);
        return instance;
    }
}
