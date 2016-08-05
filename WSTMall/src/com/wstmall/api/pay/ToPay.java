
package com.wstmall.api.pay;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class ToPay extends AbstractParam {
	private String a = "toPay";
	public String tokenId;
	public String orderId;

	@Override
	public String getA() {
		return a;
	}
}
