package com.wstmall.api.useradress.editadress;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class DeleteAddress extends AbstractParam {
	public String a = "delAddress";
	public String tokenId;// 个人ID
	public String id;
	@Override
	public String getA() {
		return a;
	}
}
