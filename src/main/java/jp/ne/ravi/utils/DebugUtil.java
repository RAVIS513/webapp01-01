package jp.ne.ravi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.constant.Const;

@Scope("prototype")
@Component
public class DebugUtil {

	@Value("${debug}")
	private String DEBUG;

	/**
	 * コンソール出力
	 * @param message
	 */
	public void outConsole(String message) {
		if (DEBUG.equals(Const.DEBUG_ON)) {
			System.out.println(message);
		}
	}

}
