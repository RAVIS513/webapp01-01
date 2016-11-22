package jp.ne.ravi.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ContentsDto implements Serializable{

	private static final long serialVersionUID = -618752240732953514L;

	private Integer seqNo;

	private Date uploadDate;

	private String jspFileName;

	private String cssFileName;

	private String title;

	private String largeClass;

	private String middleClass;

	private String smallClass;

	private String overView;

	private String status;

	private String createUserId;

	private String updateUserId;

	private String deleteUserId;

	private Timestamp createTime;

	private Timestamp updateTime;

	private Timestamp deleteTime;

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getJspFileName() {
		return jspFileName;
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public String getCssFileName() {
		return cssFileName;
	}

	public void setCssFileName(String cssFileName) {
		this.cssFileName = cssFileName;
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

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getDeleteUserId() {
		return deleteUserId;
	}

	public void setDeleteUserId(String deleteUserId) {
		this.deleteUserId = deleteUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Timestamp deleteTime) {
		this.deleteTime = deleteTime;
	}

}
