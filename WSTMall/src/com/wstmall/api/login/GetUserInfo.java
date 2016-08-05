
package com.wstmall.api.login;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetUserInfo extends AbstractParam{

	private final String a = "getUserInfo";
	public String tokenId;
	
	@Override
	public String getA() {
		return a;
	}
}
