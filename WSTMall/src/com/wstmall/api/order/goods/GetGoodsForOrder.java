package com.wstmall.api.order.goods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
@RequestType(type = HttpMethod.GET)
public class GetGoodsForOrder extends AbstractParam {
	public String a="groupGoodsForOrder";
	public String tokenId;
	public String goodsIds;
	@Override
	public String getA() {
		return a;
	}
}
