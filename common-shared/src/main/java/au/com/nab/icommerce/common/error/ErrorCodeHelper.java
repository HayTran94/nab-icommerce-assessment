package au.com.nab.icommerce.common.error;

import com.google.protobuf.Int32Value;

public class ErrorCodeHelper {
    public static boolean isSuccess(int code) {
        return code >= 0;
    }

    public static boolean isSuccess(Int32Value code) {
        return isSuccess(code.getValue());
    }

    public static boolean isFail(int code) {
        return code < 0;
    }

    public static boolean isFail(Int32Value code) {
        return isFail(code.getValue());
    }
}
