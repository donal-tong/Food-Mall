package com.wstmall.domain;

import android.graphics.Bitmap;

public class GoodsInfo {
	private Bitmap goodsimg;
	private String businessname;
	private String goodsdescribe;
	private double goodsstar;
	private double goodsprice;
	private double freightprice;
	private double goodsdistance;
	public GoodsInfo(Bitmap goodsimg, String businessname,
			String goodsdescribe, double goodsstar, double goodsprice,
			double freightprice, double goodsdistance) {
		super();
		this.goodsimg = goodsimg;
		this.businessname = businessname;
		this.goodsdescribe = goodsdescribe;
		this.goodsstar = goodsstar;
		this.goodsprice = goodsprice;
		this.freightprice = freightprice;
		this.goodsdistance = goodsdistance;
	}
	public Bitmap getGoodsimg() {
		return goodsimg;
	}
	public void setGoodsimg(Bitmap goodsimg) {
		this.goodsimg = goodsimg;
	}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getGoodsdescribe() {
		return goodsdescribe;
	}
	public void setGoodsdescribe(String goodsdescribe) {
		this.goodsdescribe = goodsdescribe;
	}
	public double getGoodsstar() {
		return goodsstar;
	}
	public void setGoodsstar(double goodsstar) {
		this.goodsstar = goodsstar;
	}
	public double getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(double goodsprice) {
		this.goodsprice = goodsprice;
	}
	public double getFreightprice() {
		return freightprice;
	}
	public void setFreightprice(double freightprice) {
		this.freightprice = freightprice;
	}
	public double getGoodsdistance() {
		return goodsdistance;
	}
	public void setGoodsdistance(double goodsdistance) {
		this.goodsdistance = goodsdistance;
	}
	
}
