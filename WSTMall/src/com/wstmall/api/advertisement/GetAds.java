package com.wstmall.api.advertisement;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetAds extends AbstractParam {
	private String a="getAds";
	public String areaId2;
	@Override
	public String getA() {
		return a;
	}
}
