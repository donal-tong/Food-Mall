package com.wstmall.api.order;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrderDetail extends AbstractParam {
	public String a="getOrderDetail";
	public String tokenId;
	public String orderId;
	@Override
	public String getA() {
		return a;
	}
}
