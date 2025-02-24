package org.example.sastwoc.vo;

import java.util.Map;

public class Result<T> {

    public boolean success;
    public int errCode;
    public String errMsg;
    public T data;

    // 无数据
    public static <T> Result<T> success() {
        return new Result<>(true, 0, "string", (T) Map.of());  // 用空的 Map 替代 null
    }

    // 带数据
    public static <T> Result<T> success(T data) {
        return new Result<>(true, 0, "string", data != null ? data : (T) Map.of());  // 处理 null 数据为 Map
    }

    public static <T> Result<T> fail(int errCode, String errMsg) {
        return new Result<>(false, errCode, errMsg,(T) Map.of());
    }
    
    private Result(boolean success, int errCode, String errMsg, T data) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

}
