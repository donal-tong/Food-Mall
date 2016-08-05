package com.wstmall.api.useradress.districts;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetDistricts extends AbstractParam {
	public String a="getDistricts";

	public String areaId2;//城市ID
	@Override
	public String getA() {
		return a;
	}
}
