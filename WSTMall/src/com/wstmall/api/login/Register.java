
package com.wstmall.api.login;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class Register extends AbstractParam{

	private final String a = "register";
	public String registerKey;
	
	@Override
	public String getA() {
		return a;
	}
}
