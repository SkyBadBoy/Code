package com.code.domain;


import java.io.Serializable;

/**
 * 放进redis中的对象，必须Serializable序列化
 * @author Majian
 *
 */
public class User implements Serializable{


	private String id;
	
	private String name;

	private String openid;

	private String headImg;

	private String status;

	private String createTime;

	private String modifyTime;

	private String blessCount;
	private String count;

	private int YEAR;
	private int MONTH;
	private int DAY;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public String getHeadImg() {
		return headImg;
	}

	public String getStatus() {
		return status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public String getBlessCount() {
		return blessCount;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public void setBlessCount(String blessCount) {
		this.blessCount = blessCount;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public int getYEAR() {
		return YEAR;
	}

	public void setYEAR(int YEAR) {
		this.YEAR = YEAR;
	}

	public int getMONTH() {
		return MONTH;
	}

	public void setMONTH(int MONTH) {
		this.MONTH = MONTH;
	}

	public int getDAY() {
		return DAY;
	}

	public void setDAY(int DAY) {
		this.DAY = DAY;
	}
}
