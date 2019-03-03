package com.technical.test.exception;

/**
 * @version 1.0
 * @author milindbangar
 * Custom Daily Trading Exception class 
 *
 */

public class DailyTradingCustomException extends Exception{

	private static final long serialVersionUID = 1L;

	public DailyTradingCustomException() {
		super();
	}

	/**
	 * @param e
	 *
	 */

	public DailyTradingCustomException(Exception e) {
		super(e);
	}

	/**
	 *
	 * @param message
	 *
	 */
	public DailyTradingCustomException(String message) {
		super(message);
	}

	/**
	 *
	 * @param cause
	 */

	public DailyTradingCustomException(Throwable cause){
		super(cause);
	}

	/**
	 *
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DailyTradingCustomException(String message,Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message,cause,enableSuppression,writableStackTrace);
	}


	/**
	 *
	 * @param message
	 * @param cause
	 */
	public DailyTradingCustomException(String message, Throwable cause){
		super(message,cause);
	}

}
