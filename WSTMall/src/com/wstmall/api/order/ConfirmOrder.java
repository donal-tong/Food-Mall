package com.wstmall.api.order;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class ConfirmOrder extends AbstractParam {
	public String a = "confirmOrder";
	public String tokenId;
	public String orderId;
	public String type;
	public String rejectionRemarks;

	@Override
	public String getA() {
		return a;
	}
}
