package com.sky.framework.common.enums;

import com.sky.framework.common.mybatis.IntegerValuedEnum;

public enum UserAgent implements IntegerValuedEnum {
	BROWSER(1),
	IOS(2),
	ANDROID(3),
	;

	private int value;

	private UserAgent(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}
