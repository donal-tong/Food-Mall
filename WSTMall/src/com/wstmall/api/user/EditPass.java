
package com.wstmall.api.user;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class EditPass extends AbstractParam{

	private final String a = "editPass";
	public String tokenId;
	public String editPassKey;
	
	@Override
	public String getA() {
		return a;
	}
}
