
package com.wstmall.api.recommendgoods;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class GetGoods extends AbstractParam{

	private final String a = "getGoods";
	public String id;
	
	@Override
	public String getA() {
		return a;
	}
}
