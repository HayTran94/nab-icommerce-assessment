package au.com.nab.icommerce.api.gateway.common;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiMessage {

    public static ApiMessage SUCCESS = new ApiMessage(0, "Success");
    public static ApiMessage FAILED = new ApiMessage(-1, "Failed");
    public static ApiMessage UNKNOWN_EXCEPTION = new ApiMessage(-2, "Unknown exception");
    public static ApiMessage NOT_FOUND = new ApiMessage(-3, "Not found");
    public static ApiMessage CREATE_FAILED = new ApiMessage(-4, "Create failed");
    public static ApiMessage UPDATE_FAILED = new ApiMessage(-4, "Update failed");

    // CART ERRORS CODE
    public static ApiMessage CART_ITEMS_INVALID = new ApiMessage(-100, "Cart items are invalid");
    public static ApiMessage CART_ITEMS_EMPTY = new ApiMessage(-101, "Cart items is empty");
    public static ApiMessage ADD_TO_CART_FAILED = new ApiMessage(-102, "Add to cart failed");
    public static ApiMessage CLEAR_CART_FAILED = new ApiMessage(-103, "Clear cart failed");

    private int code;
    private String message;
    @Setter
    private Object data;

    ApiMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiMessage clone() {
        ApiMessage instance = new ApiMessage(code, message);
        instance.setData(this.getData());
        return instance;
    }

    public static ApiMessage success(Object data) {
        ApiMessage instance = ApiMessage.SUCCESS.clone();
        instance.setData(data);
        return instance;
    }
}
