package jp.ne.ravi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ne.ravi.form.LoginForm;
import jp.ne.ravi.service.NoteManagerLoginService;
import jp.ne.ravi.session.SessionManager;
import jp.ne.ravi.utils.DebugUtil;

@Scope("prototype")
@Controller
public class NoteManagerLoginController {

	@Autowired
	private NoteManagerLoginService noteManagerLoginService;

	@Autowired
	private DebugUtil debugUtil;

	@ModelAttribute
	public LoginForm setupModelAttribute() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}

	/**
	 * 管理 ログイン画面.
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpSession session) {
//		session.invalidate();
		session.removeAttribute("sessionManager");
		noteManagerLoginService.login(model, session);
		return "index.jsp";
	}

	/**
	 * 管理 ログイン処理.
	 * @param loginForm
	 * @param attributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String checkLogin(LoginForm loginForm, RedirectAttributes attributes, HttpSession session) {
		if (noteManagerLoginService.checkLogin(loginForm, attributes)) {
			// ログイン成功
			SessionManager sessionManager = new SessionManager();
			sessionManager.setLoginForm(loginForm);
			session.setAttribute("sessionManager", sessionManager);
			debugUtil.outConsole("login Success : " + session.getId());
			return "redirect:/manager";
		} else {
			// ログイン失敗
			debugUtil.outConsole("login Failure");
			return "redirect:/login";
		}
	}
}
