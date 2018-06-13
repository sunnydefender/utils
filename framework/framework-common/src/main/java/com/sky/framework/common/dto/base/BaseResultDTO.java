package com.sky.framework.common.dto.base;

import com.sky.framework.common.exception.ErrorCode;

import java.io.Serializable;

public class BaseResultDTO implements Serializable {
	private static final long serialVersionUID = 2045849073156171052L;
	private String result;
	private String failCode;
	private String failReason;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFailCode() {
		return failCode;
	}
	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public BaseResultDTO() {

	}

	private BaseResultDTO(String result, String failCode, String failReason) {
		this.result = result;
		this.failCode = failCode;
		this.failReason = failReason;
	}

	public static BaseResultDTO success() {
		return new BaseResultDTO(ErrorCode.SUCCESS, null, null);
	}

	public static BaseResultDTO accepted() {
		return new BaseResultDTO(ErrorCode.ACCEPTED, null, null);
	}

	public static BaseResultDTO fail() {
		return new BaseResultDTO(ErrorCode.FAIL, null, null);
	}

	public static BaseResultDTO fail(String failCode) {
		return new BaseResultDTO(ErrorCode.FAIL, failCode, null);
	}

	public static BaseResultDTO fail(String failCode, String failReason) {
		return new BaseResultDTO(ErrorCode.FAIL, failCode, failReason);
	}

}
