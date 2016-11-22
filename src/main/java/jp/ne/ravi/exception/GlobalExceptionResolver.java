package jp.ne.ravi.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import jp.ne.ravi.utils.DebugUtil;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver{

	@Autowired
	DebugUtil debugUtil;

	Logger logger = LogManager.getLogger(GlobalExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		logger.error("例外エラー", ex);

		ModelAndView mav = new ModelAndView();

		if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
			mav.addObject("message", ex.getMessage());
		} else {
			mav.addObject("message", "内部サーバーエラー ページを表示できません");
		}
		mav.setViewName("error.jsp");
		return mav;
	}

}
