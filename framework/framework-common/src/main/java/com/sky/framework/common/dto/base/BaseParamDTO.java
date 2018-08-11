package com.sky.framework.common.dto.base;

import com.sky.framework.common.enums.UserAgent;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BaseParamDTO implements Serializable {
	private static final long serialVersionUID = 8399649882058356349L;

	private String requestRefNo;

	/** 客户端代理 */
	private UserAgent userAgent;

	/** 版本号 */
	private String version;

	/** 语言 */
	private String lang;

	private String ip;

	private String region;

	public String getRequestRefNo() {
		return requestRefNo;
	}

	public void setRequestRefNo(String requestRefNo) {
		this.requestRefNo = requestRefNo;
	}

	public UserAgent getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(UserAgent userAgent) {
		this.userAgent = userAgent;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
