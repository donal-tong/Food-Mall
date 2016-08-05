package com.wstmall.api.useradress;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetUserAddress extends AbstractParam {
	public String a="getUserAddress";
	public String tokenId;//个人ID
	@Override
	public String getA() {
		return a;
	}
}
