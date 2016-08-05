package com.wstmall.api.useradress.editadress;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
/**
 * tokenId:tokenId
*id:地址ID-如果是新增可以不传
*userName:收件人名称
*areaId1:所在省份ID
*areaId2:所在城市ID
*areaId3:所在县区ID
*communityId:所在社区ID
*userPhone:用户手机
*userTel:用户电话
*address:详细地址
 * @author pzl
 *
 */


public class EditAddress extends AbstractParam {
	public String a="editAddress";
	public String tokenId;//个人ID
	public String id;
	public String userName;
	public String areaId1;
	public String areaId2;
	public String areaId3;
	public String communityId;
	public String isDefault;
	public String userPhone;
	public String userTel;
	public String address;
	
	@Override
	public String getA() {
		return a;
	}
}
