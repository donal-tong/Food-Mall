
package com.wstmall.api;

import java.io.File;

import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;


@RequestType(type = HttpMethod.POST)
public class UploadPic extends AbstractParam{
	
	private String a = "uploadPic";
	
	public File Filedata;//照片对象
	public String dir="users";//照片目录 “users”--必须传这个过来 testa

	@Override
	public String getA() {
		return a;
	}
	
}
