package com.xinke.edu.o2ostore.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.beans.Transient;
import com.xinke.edu.o2ostore.constant.ResultCode;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/28 20:43
 */
public class RestResponse<T> {
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private boolean success = true;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RestResponse)) {
            return false;
        } else {
            RestResponse<?> other = (RestResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                label42: {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg == null) {
                            break label42;
                        }
                    } else if (this$msg.equals(other$msg)) {
                        break label42;
                    }

                    return false;
                }

                boolean var10000;
                label61: {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data != null) {
                            break label61;
                        }
                    } else if (!this$data.equals(other$data)) {
                        break label61;
                    }

                    var10000 = this.isSuccess() == other.isSuccess();
                    return var10000;
                }

                var10000 = false;
                return var10000;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RestResponse;
    }

    @Override
    public String toString() {
        return "RestResponse(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ", success=" + this.isSuccess() + ")";
    }

    public RestResponse(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public static <T> RestResponse.RestResponseBuilder<T> builder() {
        return new RestResponse.RestResponseBuilder();
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isFail() {
        return !this.isSuccess();
    }

    @JsonIgnore
    @Transient
    public boolean isOk() {
        return this.code == ResultCode.SUCCESS.getCode() && this.success;
    }

    public static <T> RestResponse<Object> ok() {
        return restResult((Object)null, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static <T> RestResponse<T> ok(T data) {
        return restResult(data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static <T> RestResponse<T> ok(T data, String msg) {
        return restResult(data, ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> RestResponse<Object> failed() {
        return restResult((T)null, ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMsg(), false);
    }

    public static <T> RestResponse<String> failed(String msg) {
        msg = msg.length() > 1000 ? msg.substring(0, 1000) : msg;
        return (RestResponse<String>) restResult((T)null, ResultCode.FAILURE.getCode(), msg, false);
    }

    public static <T> RestResponse<Object> failed(ResultCode resultCode) {
        return restResult((Object)null, resultCode.getCode(), resultCode.getMsg(), false);
    }

    public static <T> RestResponse<Object> failed(ResultCode resultCode, String message) {
        return restResult((Object)null, resultCode.getCode(), message, false);
    }

    public static <T> RestResponse<T> failed(T data) {
        return restResult(data, ResultCode.FAILURE.getCode(), ResultCode.FAILURE.getMsg(), false);
    }

    public static <T> RestResponse<T> failed(T data, String msg) {
        return restResult(data, ResultCode.FAILURE.getCode(), msg, false);
    }

    public static <T> RestResponse<Object> failed(int code, String msg) {
        return restResult((Object)null, code, msg, false);
    }

    public static <T> RestResponse<T> build(T data, int code, String msg) {
        return restResult(data, code, msg);
    }

    private static <T> RestResponse<T> restResult(T data, int code, String msg) {
        return restResult(data, code, msg, true);
    }

    private static <T> RestResponse<T> restResult(T data, int code, String msg, boolean success) {
        RestResponse<T> restResponse = new RestResponse();
        restResponse.setCode(code);
        restResponse.setData(data);
        restResponse.setMsg(msg);
        restResponse.setSuccess(success);
        return restResponse;
    }

    public RestResponse() {
    }

    public static class RestResponseBuilder<T> {
        private int code;
        private String msg;
        private T data;
        private boolean success;

        public RestResponseBuilder() {
        }

        public RestResponse.RestResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }

        public RestResponse.RestResponseBuilder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public RestResponse.RestResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public RestResponse.RestResponseBuilder<T> success(boolean success) {
            this.success = success;
            return this;
        }

        public RestResponse<T> build() {
            return new RestResponse(this.code, this.msg, this.data, this.success);
        }

        @Override
        public String toString() {
            return "RestResponse.RestResponseBuilder(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ", success=" + this.success + ")";
        }
    }
}
