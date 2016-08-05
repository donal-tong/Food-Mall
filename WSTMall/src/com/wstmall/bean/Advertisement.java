package com.wstmall.bean;

import java.io.Serializable;

public class Advertisement implements Serializable {
	public String adId;//广告ID
	public String adName;//广告标题
	public String adURL;//点击广告要打开的url
	public String adFile;//广告图片图片
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	public String getAdURL() {
		return adURL;
	}
	public void setAdURL(String adURL) {
		this.adURL = adURL;
	}
	public String getAdFile() {
		return adFile;
	}
	public void setAdFile(String adFile) {
		this.adFile = adFile;
	}
	
}
