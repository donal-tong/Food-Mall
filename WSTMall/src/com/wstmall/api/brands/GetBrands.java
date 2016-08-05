package com.wstmall.api.brands;

/**
 * areaId3:县区ID
 brandName:品牌信息 
 */
import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetBrands extends AbstractParam {
	private String a = "getBrands";
	public String areaId3;
	public String brandName;
	@Override
	public String getA() {
		return a;
	}
}
