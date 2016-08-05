package com.wstmall.bean;

import java.io.Serializable;
/**
 * 	addressId":"地址ID",
 	* areaId1":"省份ID",
	"areaId2":"城市ID",
	"areaId3":"县区ID",
	"communityId":"社区ID"
	"userName":"收件人名称",
	"userPhone":收件人手机,
	"userTel":"收件人电话",
	"address":"收件人地址",
	"postCode":"邮编",
	"isDefault":"是否默认 1：默认地址 0：非默认地址",
	"areaName":"收件县区和社区"
 * @author Administrator
 *
 */
public class UserAdressBean implements Serializable {
	public String addressId;
	public String areaId1;
	public String areaId2;
	public String areaId3;
	public String communityId;
	public String userName;
	public String userPhone;
	public String userTel;
	public String address;
	public String postCode;
	public String isDefault;
	public String areaName;
	public UserAdressBean(String addressId, String areaId1, String areaId2,
			String areaId3, String communityId, String userName,
			String userPhone, String userTel, String address, String postCode,
			String isDefault, String areaName) {
		super();
		this.addressId = addressId;
		this.areaId1 = areaId1;
		this.areaId2 = areaId2;
		this.areaId3 = areaId3;
		this.communityId = communityId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.userTel = userTel;
		this.address = address;
		this.postCode = postCode;
		this.isDefault = isDefault;
		this.areaName = areaName;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAreaId1() {
		return areaId1;
	}
	public void setAreaId1(String areaId1) {
		this.areaId1 = areaId1;
	}
	public String getAreaId2() {
		return areaId2;
	}
	public void setAreaId2(String areaId2) {
		this.areaId2 = areaId2;
	}
	public String getAreaId3() {
		return areaId3;
	}
	public void setAreaId3(String areaId3) {
		this.areaId3 = areaId3;
	}
	public String getCommunityId() {
		return communityId;
	}
	public void setCommunityId(String communityId) {
		this.communityId = communityId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
