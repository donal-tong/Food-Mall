package com.wstmall.api.useradress.community;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetCommunitys extends AbstractParam {
	public String a="getCommunitys";
	public String areaId3;//县区ID
	@Override
	public String getA() {
		return a;
	}
}
