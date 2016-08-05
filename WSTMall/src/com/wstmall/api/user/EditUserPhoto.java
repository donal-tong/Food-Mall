
package com.wstmall.api.user;

import java.io.File;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.GET)
public class EditUserPhoto extends AbstractParam{
	
	private String a = "editUserPhoto";
	
	public String tokenId;//tokenId
	public String userPhoto;//用户头像，不要传缩略图。 

	@Override
	public String getA() {
		return a;
	}
	
}
