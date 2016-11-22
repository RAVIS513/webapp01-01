package jp.ne.ravi.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ClassDto implements Serializable{

	private static final long serialVersionUID = 4951688068390192683L;

	private String largeClass;

	private String middleClass;

	private String smallClass;

	private Timestamp createTime;

	private Timestamp updateTime;

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

}
