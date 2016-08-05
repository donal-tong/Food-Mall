package com.wstmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * "payType":支付方式：0-货到付款 1-在线支付 "orderId":订单ID "orderNo":订单号
 * "orderStatus":订单详细状态 -5:门店同意取消 -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 -1：用户取消 0:未受理
 * 1:已受理 2:打包中 3:配送中 4:已到货 5:门店确认已收货 "needPay":需付金额 "createTime":下单时间
 * "totalMoney":总金额 "shopName":店铺名称 "shopId":店铺ID
 **/

public class OrderBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8770717321354247275L;
	public int isAppraises;
	public String payType;
	public String orderId;
	public String orderNo;
	public int orderStatus;
	public double needPay;
	public String createTime;
	public double totalMoney;
	public String shopName;
	public String shopId;
	public List<GoodsListBean> goodlist=new ArrayList<GoodsListBean>();
	public int shopgoodcount;
	public double shopgoodtotalprice;
}
