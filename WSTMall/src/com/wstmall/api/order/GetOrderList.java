package com.wstmall.api.order;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetOrderList extends AbstractParam {
	public String a="getOrderList";
	public String tokenId;
	public int status;
	public int p;
	@Override
	public String getA() {
		return a;
	}
}
