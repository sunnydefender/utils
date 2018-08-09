package com.sky.framework.common.dto.base;

import com.sky.framework.common.enums.Result;

import java.io.Serializable;

public class BaseResultDTO implements Serializable {
	private static final long serialVersionUID = 2045849073156171052L;
	private Result result;
	private String failCode;
	private String failReason;
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
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

	private BaseResultDTO(Result resultStatus, String failCode, String failReason) {
		this.result = resultStatus;
		this.failCode = failCode;
		this.failReason = failReason;
	}

	public static BaseResultDTO success() {
		return new BaseResultDTO(Result.SUCCESS, null, null);
	}

	public static BaseResultDTO accepted() {
		return new BaseResultDTO(Result.ACCEPTED, null, null);
	}

	public static BaseResultDTO fail() {
		return new BaseResultDTO(Result.FAIL, null, null);
	}

	public static BaseResultDTO fail(String failCode) {
		return new BaseResultDTO(Result.FAIL, failCode, null);
	}

	public static BaseResultDTO fail(String failCode, String failReason) {
		return new BaseResultDTO(Result.FAIL, failCode, failReason);
	}

}
