package au.com.nab.icommerce.api.gateway.common;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiMessage {

    public static ApiMessage SUCCESS = new ApiMessage(0, "Success");
    public static ApiMessage FAILED = new ApiMessage(-1, "Failed");
    public static ApiMessage UNKNOWN_EXCEPTION = new ApiMessage(-2, "Unknown exception");

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
