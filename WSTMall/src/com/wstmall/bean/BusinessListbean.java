package com.wstmall.bean;

import java.io.Serializable;
/**
 * 	shopName:店铺名称,
	shopId:店铺ID,
	catName:主营,
	shopAddress:店铺地址,
	shopImg:店铺图标,
	deliveryStartMoney:起送价格,
	userDistance:距离
	shopevaluation：五星评价
 * @author pzl
 *
 */
public class BusinessListbean implements Serializable  {

	public String shopName;//店铺名称,
	public String shopId;//店铺ID,
	public String catName;//主营,
	public String shopAddress;//店铺地址,
	public String shopImg;//店铺图标,
	public double deliveryStartMoney;//起送价格,
	public double userDistance;//距离
	public double totalScore;//商家评分
	
}
