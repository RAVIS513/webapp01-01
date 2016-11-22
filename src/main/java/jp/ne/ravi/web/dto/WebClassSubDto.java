package jp.ne.ravi.web.dto;

import java.io.Serializable;
import java.util.List;

public class WebClassSubDto implements Serializable{

	private static final long serialVersionUID = -4323637326097716080L;

	private String mainClass;

	private List<String> subClass;

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public List<String> getSubClass() {
		return subClass;
	}

	public void setSubClass(List<String> subClass) {
		this.subClass = subClass;
	}
}
