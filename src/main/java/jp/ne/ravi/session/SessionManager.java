package jp.ne.ravi.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.form.ClassForm;
import jp.ne.ravi.form.ContentsForm;
import jp.ne.ravi.form.LoginForm;

@Component
@Scope("session")
public class SessionManager implements Serializable{

	private static final long serialVersionUID = 2686489163346557967L;

	private LoginForm loginForm;

	private ContentsForm contentsForm;

	private ClassForm classForm;

	public LoginForm getLoginForm() {
		return loginForm;
	}

	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	public ContentsForm getContentsForm() {
		return contentsForm;
	}

	public void setContentsForm(ContentsForm contentsForm) {
		this.contentsForm = contentsForm;
	}

	public ClassForm getClassForm() {
		return classForm;
	}

	public void setClassForm(ClassForm classForm) {
		this.classForm = classForm;
	}
}
