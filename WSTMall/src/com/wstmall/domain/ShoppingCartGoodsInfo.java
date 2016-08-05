package com.wstmall.domain;

import android.graphics.Bitmap;

public class ShoppingCartGoodsInfo {
	private String goodsimg;
	private String goodsdescribe;
	private double goodsprice;
	private double goodscount;
	private boolean cbchild;

	public ShoppingCartGoodsInfo(){
		
	}

	public String getGoodsimg() {
		return goodsimg;
	}

	public void setGoodsimg(String goodsimg) {
		this.goodsimg = goodsimg;
	}

	public String getGoodsdescribe() {
		return goodsdescribe;
	}

	public void setGoodsdescribe(String goodsdescribe) {
		this.goodsdescribe = goodsdescribe;
	}

	public double getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(double goodsprice) {
		this.goodsprice = goodsprice;
	}

	public double getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(double goodscount) {
		this.goodscount = goodscount;
	}

	public boolean isCbchild() {
		return cbchild;
	}

	public void setCbchild(boolean cbchild) {
		this.cbchild = cbchild;
	}
	
}
