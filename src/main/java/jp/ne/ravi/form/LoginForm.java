package jp.ne.ravi.form;

import java.io.Serializable;

public class LoginForm implements Serializable{

	private static final long serialVersionUID = -5013063997295737683L;

	private String id;

	private String pass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
