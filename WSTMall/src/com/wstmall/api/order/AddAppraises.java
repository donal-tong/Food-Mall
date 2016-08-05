package com.wstmall.api.order;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class AddAppraises extends AbstractParam {
	public String a="addAppraises";
	public String tokenId;
	public String orderId;
	public String goodsId;
	public int goodsScore;
	public int timeScore;
	public int serviceScore;
	public String content;
	@Override
	public String getA() {
		return a;
	}
}
