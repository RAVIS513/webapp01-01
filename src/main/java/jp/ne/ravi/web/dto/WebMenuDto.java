package jp.ne.ravi.web.dto;

import java.io.Serializable;
import java.util.List;

public class WebMenuDto implements Serializable{

	private static final long serialVersionUID = -808043125104845457L;

	private WebMenuSubDto main;

	private List<WebMenuSubDto> sub;

	public WebMenuSubDto getMain() {
		return main;
	}

	public void setMain(WebMenuSubDto main) {
		this.main = main;
	}

	public List<WebMenuSubDto> getSub() {
		return sub;
	}

	public void setSub(List<WebMenuSubDto> sub) {
		this.sub = sub;
	}

}
