package com.wstmall.api.order;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrdersStatus extends AbstractParam {
	public String a="getOrdersStatus";
	public String tokenId;
	@Override
	public String getA() {
		return a;
	}
}
