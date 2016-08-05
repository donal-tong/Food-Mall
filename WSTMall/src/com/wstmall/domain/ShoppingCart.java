package com.wstmall.domain;

import java.util.ArrayList;
import java.util.List;

import com.wstmall.bean.GoodsListBean;

public class ShoppingCart {
	public String shopname;
	public String shopID;
	public double freiht;
	public double deliveryFreeMoney;
	public double totalPrice; //同一店铺商品总价钱
	public boolean cbgroup;
	public List<GoodsListBean> child=new ArrayList<GoodsListBean>();

	public ShoppingCart(boolean cbgroup) {
		this.cbgroup=cbgroup;
	}	
}
