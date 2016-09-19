package com.lvtulife.base.component.message;

import com.lvtulife.base.model.BaseMessageCode;

/**
 * 通用业务消息处理类
 * @author lvtulife
 *
 * @param <T>
 */
public class Message<T> {
	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 业务是否执行成功
	 */
	private boolean success = false;
	/**
	 * 业务消息
	 */
	private String info;
	/**
	 * 业务详细消息
	 */
	private String detail;
	/**
	 * 结果反馈
	 */
	private T data;

	public Message(int code) {
		initMessage(code);
	}

	public Message(int code, String detailInfo, T datas) {
		initMessage(code);
		this.detail = detailInfo;
		this.data = datas;
	}

	public int getCode() {
		return code;
	}

	public boolean getSuccess() {
		return success;
	}

	public String getInfo() {
		return info;
	}

	public String getDetail() {
		if (detail == null)
			detail = "";
		return detail;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Message<T> setDetail(String detail) {
		this.detail = detail;
		return this;
	}

	public Message<T> setCode(int code) {
		initMessage(code);
		return this;
	}

	public Message<T> setCode(int code, String detail, T data) {
		initMessage(code);
		this.detail = detail;
		this.data = data;
		return this;
	}

	private void initMessage(int code) {
		BaseMessageUtils util = BaseMessageUtils.getInstence();
		BaseMessageCode bean = util.getCodeBean(code);
		this.code = code;
		this.success = util.getCodeSuccess(code);
		this.info = bean.getCodeInfo();
	}

}
