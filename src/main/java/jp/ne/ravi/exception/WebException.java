package jp.ne.ravi.exception;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import jp.ne.ravi.utils.DebugUtil;

public class WebException extends RuntimeException{

	@Autowired
	DebugUtil debugUtil;

	private static final long serialVersionUID = -3038887125904747703L;

	private Logger logger = LogManager.getLogger(WebException.class);

	public WebException() {

	}

	/**
	 * メッセージ出力
	 * @param message
	 */
	public WebException(String message) {
		super(message);
		logger.error(message);
		debugUtil.outConsole(message);
	}

	/**
	 * スタックトレース出力
	 * @param cause
	 */
	public WebException(Throwable cause) {
		super(cause);
		cause.printStackTrace();
		logger.error(cause);
	}

	/**
	 * メッセージ & スタックトレース出力
	 * @param message
	 * @param cause
	 */
	public WebException(String message, Throwable cause) {
		super(cause);
		cause.printStackTrace();
		logger.error(message, cause);
		debugUtil.outConsole(message);
	}
}
