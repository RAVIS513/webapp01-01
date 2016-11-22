package jp.ne.ravi.web.dto;

import java.io.Serializable;

public class WebContentsDto implements Serializable{

	private static final long serialVersionUID = -8196113945861216566L;

	private String seqNo;

	private String uploadDate;

	private String title;

	private String largeClass;

	private String middleClass;

	private String smallClass;

	private String status;

	private String overView;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLargeClass() {
		return largeClass;
	}

	public void setLargeClass(String largeClass) {
		this.largeClass = largeClass;
	}

	public String getMiddleClass() {
		return middleClass;
	}

	public void setMiddleClass(String middleClass) {
		this.middleClass = middleClass;
	}

	public String getSmallClass() {
		return smallClass;
	}

	public void setSmallClass(String smallClass) {
		this.smallClass = smallClass;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

}
