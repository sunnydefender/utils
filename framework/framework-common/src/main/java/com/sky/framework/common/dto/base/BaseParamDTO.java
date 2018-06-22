package com.sky.framework.common.dto.base;

import com.sky.framework.common.enums.UserAgent;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BaseParamDTO implements Serializable {
	private static final long serialVersionUID = 8399649882058356349L;

	private String requestRefNo;

	@NotNull
	private UserAgent userAgent;

	@NotBlank
	private String version;

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
}
