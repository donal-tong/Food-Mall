package com.wstmall.bean;

import java.io.Serializable;

/**
 * "shopName":店铺名称,
"shopId":店铺ID,
"goodsId":商品ID,
"goodsName":商品名称,
"goodsThums":商品图片,
"shopPrice":商品价格,
 * @author Administrator
 *
 */
public class RecommendGoodsListBean implements Serializable {
	private String shopName;
	private String shopId;
	private String goodsCatId1;
	private String goodsId;
	private String goodsName;
	private String goodsThums;
	private double shopPrice;
	private String userDistance;
	public String getGoodsCatId1() {
		return goodsCatId1;
	}
	public void setGoodsCatId1(String goodsCatId1) {
		this.goodsCatId1 = goodsCatId1;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsThums() {
		return goodsThums;
	}
	public void setGoodsThums(String goodsThums) {
		this.goodsThums = goodsThums;
	}
	public double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(double shopPrice) {
		this.shopPrice = shopPrice;
	}
	
}
