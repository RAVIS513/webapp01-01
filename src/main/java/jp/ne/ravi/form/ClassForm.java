package jp.ne.ravi.form;

import java.io.Serializable;

public class ClassForm implements Serializable{

	private static final long serialVersionUID = 8820192767316404497L;

	private String largeClass;

	private String middleClass;

	private String smallClass;

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
}
