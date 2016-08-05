package com.wstmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * shopInfo:{ 店铺信息 shopId:店铺ID, shopName:店铺名称, deliveryType:配送方式 0-商城配送 1-门店配送,
 * shopAtive:店铺状态 1-营业中 0-休息中, deliveryTime:配送服务时间, isInvoice:是否可开发票,
 * deliveryStartMoney:店铺起送价格(要满足这个价格才允许下单), deliveryFreeMoney:包邮起步价，
 * deliveryMoney:配送费, serviceStartTime:店铺服务开始时间, serviceEndTime:店铺服务结束时间 },
 * goods:[{ 商品列表 goodsThums:商品缩略图, goodsId:商品ID， shopPrice：店铺价格, isBook:是否可预定，
 * goodsName:商品名称, goodsStock:商品库存, goodsAttrId:商品属性ID（下单时要传回来给我）, goodsSn:商品编号,
 * }], communitys:[{ 店铺会配送的社区列表 shopId:店铺ID,
 * communityId:会配送的社区（下单的收货人所在社区必须在这些社区里边）
 **/
public class GoodsForOrder implements Serializable {
	public String shopId;
	public String shopName;
	public String deliveryType;
	public String shopAtive;
	public String deliveryTime;
	public int isInvoice;
	public double deliveryStartMoney;
	public double deliveryFreeMoney;
	public double deliveryMoney;
	public double goodsTotalPrice;//单间店铺商品总价钱
	public boolean isMinDeliveryPrice=true;//达到多少钱 判断能不能下单
	public double serviceStartTime;
	public double serviceEndTime;
	public boolean isdelivery=true;// 判断是否在配送区域
	
	public List<GoodsListBean> goodslist;
	public List<String> communityId = new ArrayList<String>();
}
