
package com.wstmall.api.goods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetAppraises extends AbstractParam{
	
	private String a = "getAppraises";
	
	public String id;//商品ID
	public int type;//评论类型 0:全部 1:差评 2:中评 3:好评 
	public int p;

	@Override
	public String getA() {
		return a;
	}
	
}
