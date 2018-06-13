package com.sky.framework.redis.enums.key;

import com.sky.framework.common.mybatis.StringValuedEnum;

public enum Member implements StringValuedEnum {
    K_CAPTCHA_CODE("captcha_code"),
    K_MEMBER_PWD_SALT("member_pwd_salt"),
    K_TRADE_PWD_SALT("trade_pwd_salt"),
    K_MEMBER_REGISTER("member_register"),
    ;

    public static String buildMemberRegisterKeyByEmail(String s) {
        return K_MEMBER_REGISTER.getValue() + ":" + s;
    }

    private String value;

    private Member(String value) {
        this.value = value;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

    @Override
    public java.lang.String getValue() {
        return null;
    }
}
