package com.wstmall.api;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetCitys extends AbstractParam{
	
	private String a = "getCitys";
	
	public String key;//城市名称
	public String key2;//县区名称，可以不填

	@Override
	public String getA() {
		return a;
	}
	
}
