package com.wstmall.api.recommendgoods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetGoodsCatAndGoods extends AbstractParam {
	public String a="getGoodsCatAndGoods";
	public String areaId2;
	@Override
	public String getA() {
		return a;
	}
}
