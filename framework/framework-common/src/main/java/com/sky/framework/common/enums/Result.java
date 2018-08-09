package com.sky.framework.common.enums;

import com.sky.framework.common.mybatis.IntegerValuedEnum;

public enum Result implements IntegerValuedEnum {
	/** 成功状态码 */
	SUCCESS(0),

	/** 失败状态码 */
	FAIL(10),

	/** 已受理状态码 */
	ACCEPTED(20)

	;

	private int value;

	private Result(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}
}
