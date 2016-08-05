package com.wstmall.api.goods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetGoodsCats extends AbstractParam{
	
	private String a = "getGoodsCats";

	@Override
	public String getA() {
		return a;
	}
	
}
