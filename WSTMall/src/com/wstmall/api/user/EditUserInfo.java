
package com.wstmall.api.user;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class EditUserInfo extends AbstractParam{

	private final String a = "editUserInfo";
	public String tokenId;
	public String userName;//用户名称
	public String userSex;//性别 1:男 2:女 3:保密 
	
	@Override
	public String getA() {
		return a;
	}
}
