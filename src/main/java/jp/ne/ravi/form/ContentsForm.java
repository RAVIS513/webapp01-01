package jp.ne.ravi.form;

import java.io.Serializable;

public class ContentsForm implements Serializable{

	private static final long serialVersionUID = -4636071444269742127L;

	private String seqNo;

	private String uploadDate;

	private String title;

	private String largeClass;

	private String middleClass;

	private String smallClass;

	private String overView;

	private String jsp;

	private String css;

	private String jspFileName;

	private String cssFileName;

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

	public String getJsp() {
		return jsp;
	}

	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
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

}
