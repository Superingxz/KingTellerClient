package com.kingteller.client.netauth.exception;


/**
 * @Title ShareCarException
 * @Package com.kingteller.client.netauth.exception
 * @Description ShareCarException异常处理类
 * @author 王定波
 * @date 2014-6-16 16:30
 * @version V1.0
 */
public class KingTellerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8220556234280073360L;
	private int statusCode = -1;

	public KingTellerException(String msg) {
		super(msg);
	}

	public KingTellerException(Exception cause) {
		super(cause);
	}

	public KingTellerException(String msg, int statusCode) {
		super(msg);
		this.statusCode = statusCode;
	}

	public KingTellerException(String msg, Exception cause) {
		super(msg, cause);
	}

	public KingTellerException(String msg, Exception cause, int statusCode) {
		super(msg, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public KingTellerException() {
		super();
	}

	public KingTellerException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public KingTellerException(Throwable throwable) {
		super(throwable);
	}

	public KingTellerException(int statusCode) {
		super();
		this.statusCode = statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
