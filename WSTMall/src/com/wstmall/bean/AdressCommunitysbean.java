package com.wstmall.bean;

import java.io.Serializable;
/**
 * "communityId":"社区ID",
 *"communityName":"社区名称"
 * @author Administrator
 *
 */
public class AdressCommunitysbean implements Serializable {
	public String communityId;
	public String communityName;
	
	public AdressCommunitysbean(String communityId, String communityName) {
		super();
		this.communityId = communityId;
		this.communityName = communityName;
	}
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	
}
