package jp.ne.ravi.web.dto;

import java.io.Serializable;

public class WebMenuSubDto implements Serializable{

	private static final long serialVersionUID = 1771378415886084665L;

	private String name;

	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
