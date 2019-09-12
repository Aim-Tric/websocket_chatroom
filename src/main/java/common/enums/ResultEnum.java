package common.enums;

public enum ResultEnum {
    SUCCESS(200, "操作成功！"),
    FAILD(300, "操作失败"),
    ERROR(400, "系统错误！"),

    USER_NOT_EXIST(401, "用户不存在"),
    USER_PASSWORD_ERROR(402, "用户密码错误"),
    USER_EXIST(403, "用户已存在"),
    PHONE_UNVAILED(402, "请填写正确的手机号"),

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
