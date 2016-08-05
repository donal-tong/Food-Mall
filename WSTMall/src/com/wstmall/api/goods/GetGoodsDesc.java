
package com.wstmall.api.goods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class GetGoodsDesc extends AbstractParam{
	
	private String a = "getGoodsDesc";
	
	public String id;

	@Override
	public String getA() {
		return a;
	}
	
}
