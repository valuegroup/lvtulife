package com.lvtulife.base.component.message;

/**
 * 自定义异常处理
 * @author lvtulife
 *
 */
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 2238006192506658944L;
	private Integer code;
	private String detailMsg;

	public CustomException(int code) {
		this.code = code;
	}

	public CustomException(int code, String detailMsg) {
		super(detailMsg);
		this.code = code;
		this.detailMsg = detailMsg;
	}

	public CustomException(String detailMsg) {
		super(detailMsg);
		this.code = Code.C400;
		this.detailMsg = detailMsg;
	}

	public Integer getCode() {
		return code;
	}

	public String getDetailMsg() {
		return detailMsg;
	}
}
