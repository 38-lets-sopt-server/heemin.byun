package org.sopt.global.api.response;

import org.sopt.global.api.code.ResponseCode;

public class BaseResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private BaseResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(ResponseCode code, T data) {
        return new BaseResponse<>(true, code.getMessage(), data);
    }

    public static BaseResponse<Void> success(ResponseCode code) {
        return new BaseResponse<>(true, code.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(false, "🚫 " + message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}