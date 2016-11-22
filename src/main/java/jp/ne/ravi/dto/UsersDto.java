package jp.ne.ravi.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class UsersDto implements Serializable{

	private static final long serialVersionUID = -1315181966282451475L;

	private String userId;

	private String newPass;

	private String oldPass;

	private Timestamp passLimit;

	private String tmpPass;

	private Timestamp tmpPassLimit;

	private String mailAddress;

	private String authLevel;

	private Timestamp lastPassChangeTime;

	private Timestamp lastLoginTime;

	private Timestamp createTime;

	private Timestamp updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public Timestamp getPassLimit() {
		return passLimit;
	}

	public void setPassLimit(Timestamp passLimit) {
		this.passLimit = passLimit;
	}

	public String getTmpPass() {
		return tmpPass;
	}

	public void setTmpPass(String tmpPass) {
		this.tmpPass = tmpPass;
	}

	public Timestamp getTmpPassLimit() {
		return tmpPassLimit;
	}

	public void setTmpPassLimit(Timestamp tmpPassLimit) {
		this.tmpPassLimit = tmpPassLimit;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getAuthLevel() {
		return authLevel;
	}

	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;
	}

	public Timestamp getLastPassChangeTime() {
		return lastPassChangeTime;
	}

	public void setLastPassChangeTime(Timestamp lastPassChangeTime) {
		this.lastPassChangeTime = lastPassChangeTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
