package jp.ne.ravi.web.dto;

import java.io.Serializable;
import java.util.List;

public class WebClassDto implements Serializable{

	private static final long serialVersionUID = 924324750548995520L;

	private String mainClass;

	private List<WebClassSubDto> subClass;

	public String getMainClass() {
		return mainClass;
	}

	public void setMainClass(String mainClass) {
		this.mainClass = mainClass;
	}

	public List<WebClassSubDto> getSubClass() {
		return subClass;
	}

	public void setSubClass(List<WebClassSubDto> subClass) {
		this.subClass = subClass;
	}
}
