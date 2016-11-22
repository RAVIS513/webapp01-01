package jp.ne.ravi.session;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jp.ne.ravi.web.dto.WebMenuDto;

@Component
@Scope("session")
public class SessionNote implements Serializable{

	private static final long serialVersionUID = 6131094308510320815L;

	private String uploadDate;

	private List<WebMenuDto> sideMenuList;

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public List<WebMenuDto> getSideMenuList() {
		return sideMenuList;
	}

	public void setSideMenuList(List<WebMenuDto> sideMenuList) {
		this.sideMenuList = sideMenuList;
	}
}
