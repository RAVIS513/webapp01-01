package jp.ne.ravi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jp.ne.ravi.utils.DebugUtil;

public class Interceptor implements HandlerInterceptor{

	@Autowired
	private DebugUtil debugUtil;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String contextPath = request.getContextPath();
		if (request.getRequestURI().startsWith(contextPath + "/manager")) {
			if (request.getSession().getAttribute("sessionManager") == null) {
				response.sendRedirect(contextPath + "/error/login");
				debugUtil.outConsole("[Interceptor] SessionLogout");
				return false;
			}
		}
		return true;
	}

}
