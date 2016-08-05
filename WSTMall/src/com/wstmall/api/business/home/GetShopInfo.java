package com.wstmall.api.business.home;


import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
/*isSelfShop:是否自营店0-不是 1-是
shopId:店铺ID 如果是获取自营店则不用传
areaId2:城市ID*/
@RequestType(type = HttpMethod.GET)
public class GetShopInfo extends AbstractParam {
	private String a = "getShopInfo";
	public int isSelfShop;
	public String shopId;
	public String areaId2;
	@Override
	public String getA() {
		return a;
	}
}
