
package com.wstmall.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



/**
 * 设置sharedprefernces
 * 存取tokenid和cache_json
 * @author ZHY_9
 *
 */
public class MyPref {

	private static MyPref instance;
	private SharedPreferences mSharedPref = null;
	private final String APP_NAME = "com.wstmall";
	private final String TOKENID = "TOKENID";
	private final String CACHE_JSON = "CACHE_JSON";
	
	private MyPref(Context context) {
		mSharedPref = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
	}
	
	public static MyPref getInstance(Context context) {
		instance = new MyPref(context);
		return instance;
	}
	
	public void setCache(String cacheJson){
		Editor editor = mSharedPref.edit();
		editor.putString(CACHE_JSON, cacheJson);
		editor.commit();
	}
	
	public String getCache(){
		String cacheJson = mSharedPref.getString(CACHE_JSON, null);
		return cacheJson;
	}
	
	public void setTokenid(String tokenid) {
		Editor editor = mSharedPref.edit();
		editor.putString(TOKENID, tokenid);
		editor.commit();
	}

	public String getTokenid() {
		String tokenid = mSharedPref.getString(TOKENID, null);
		return tokenid;
	}
	
	
}
