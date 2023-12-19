package com.hhj.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),   //表示操作成功
    USERNAME_ERROR(501,"usernameError"),  //表示传入的用户名错误
    PASSWORD_ERROR(503,"passwordError"),  //表示传入的密码错误
    NOTLOGIN(504,"notLogin"),   //表示当前未登录
    USERNAME_USED(505,"用户名已被占用");  //表示用户名已经被使用

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
