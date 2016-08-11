
package com.wstmall.application;

import com.wstmall.bean.CacheBean;
import com.wstmall.bean.City;
import com.wstmall.bean.Point;
import com.wstmall.bean.User;


public class Const {

	public static User user;
	public static Boolean isLogin=false;//判断是否已登录
	public static CacheBean cache;//数据缓存
	public static boolean isNeedSaveCache=false;//是否需要保存缓存
	
	public static final String FILE_NAME="WSTMall";//缓存以及Loading图等存放目录的名称
	
//	public static final String BASE_URL = "http://dev-wstfull.wstmall.com/";
//	public static final String API_BASE_URL = "http://dev-wstfull.wstmall.com/index.php/App/Apis/";
	public static final String BASE_URL = "http://sp.mai022.com/";
	public static final String API_BASE_URL = "http://sp.mai022.com/index.php/App/Apis/";
	public static final Point defaultPoint = new Point(39.1333601320,117.2109781636);
	
}
