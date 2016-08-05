
package com.wstmall.api.goods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class AddAppraises extends AbstractParam{
	
	private String a = "addAppraises";
	
	public String tokenId;
	public String orderId;//订单ID
	public String goodsId;//商品ID
	public int goodsScore=1;//商品评分（1~5）
	public int timeScore=1;//时效评分（1~5）
	public int serviceScore=1;//服务评分（1~5）
	public String content="";//评价详情(至少10个字)

	@Override
	public String getA() {
		return a;
	}
	
}
