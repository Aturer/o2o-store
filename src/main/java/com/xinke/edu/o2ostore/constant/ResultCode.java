package com.xinke.edu.o2ostore.constant;

/**
 * @Description
 * @Author lqq
 * @Date 2023/02/28 20:47
 */
public enum ResultCode {
    SUCCESS(0, "success"),
    FAILURE(-1, "fail"),
    USER_NOT_FOUND(401, "Current User Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    UN_AUTHORIZED(401, "Request Unauthorized"),
    NOT_FOUND(404, "404 Not Found"),
    METHOD_NOT_SUPPORTED(405, "Method Not Supported"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "Media Type Not Supported"),
    FORBIDDEN(403, "403 Forbidden"),
    PARAM_VALID_ERROR(400, "Parameter Validation Error"),
    ERROR_409(409, "您已经在其它地方登录"),
    FAIL_FALL_BACK(9998, "fall back");

    final int code;
    final String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ResultCode fromCode(long code) {
        ResultCode[] ecs = values();
        ResultCode[] var3 = ecs;
        int var4 = ecs.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ResultCode ec = var3[var5];
            if ((long) ec.getCode() == code) {
                return ec;
            }
        }

        return SUCCESS;
    }
}
