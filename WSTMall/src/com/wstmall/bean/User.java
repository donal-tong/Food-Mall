
package com.wstmall.bean;

import java.io.Serializable;

import com.wstmall.util.EAJson;


public class User implements Serializable {

	public String userId;//用户ID
	public String loginName;//登录账号
	public String userName;//用户名称，这个值不一定有，用户有填才会有值
	public String userPhoto;//用户头像
	public String userScore;//用户积分
	public String userSex;//性别
	public String userType;//0:普通会员  1:店鋪用户  2:两者都是
	
	public String getName(){
		if(userName==null||userName.equals("")){
			return loginName;
		}else{
			return userName;
		}
	}
	
	public String getSex(){
		if(userSex.equals("1")){
			return "男";
		}else if(userSex.equals("2")){
			return "女";
		}else{
			return "保密";
		}
	}
	
	public String getSexNum(String sexName){
		if(sexName.equals("男")){
			return "1";
		}else if(sexName.equals("女")){
			return "2";
		}else{
			return "0";
		}
	}
	
}
