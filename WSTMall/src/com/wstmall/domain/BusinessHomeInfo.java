package com.wstmall.domain;

import android.graphics.Bitmap;

public class BusinessHomeInfo {
	private Bitmap businessimg;
	private String location;
	private int phonenum;
	private double businessgoodevalution;
	private double businessfreightime;
	private double businessserve;
	private Bitmap[] goodsimg;
	private String[] goodsdescribe;
	private double[] goodsprice;

	public double getBusinessgoodevalution() {
		return businessgoodevalution;
	}
	public void setBusinessgoodevalution(int businessgoodevalution) {
		this.businessgoodevalution = businessgoodevalution;
	}
	public double getBusinessfreightime() {
		return businessfreightime;
	}
	public void setBusinessfreightime(int businessfreightime) {
		this.businessfreightime = businessfreightime;
	}
	public double getBusinessserve() {
		return businessserve;
	}
	public void setBusinessserve(int businessserve) {
		this.businessserve = businessserve;
	}
	public BusinessHomeInfo(Bitmap businessimg, String location, int phonenum,
			double businessgoodevalution, double businessfreightime,
			double businessserve, Bitmap[] goodsimg, String[] goodsdescribe,
			double[] goodsprice) {
		super();
		this.businessimg = businessimg;
		this.location = location;
		this.phonenum = phonenum;
		this.businessgoodevalution = businessgoodevalution;
		this.businessfreightime = businessfreightime;
		this.businessserve = businessserve;
		this.goodsimg = goodsimg;
		this.goodsdescribe = goodsdescribe;
		this.goodsprice = goodsprice;
	}
	public Bitmap getBusinessimg() {
		return businessimg;
	}
	public void setBusinessimg(Bitmap businessimg) {
		this.businessimg = businessimg;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(int phonenum) {
		this.phonenum = phonenum;
	}
	public Bitmap[] getGoodsimg() {
		return goodsimg;
	}
	public void setGoodsimg(Bitmap[] goodsimg) {
		this.goodsimg = goodsimg;
	}
	public String[] getGoodsdescribe() {
		return goodsdescribe;
	}
	public void setGoodsdescribe(String[] goodsdescribe) {
		this.goodsdescribe = goodsdescribe;
	}
	public double[] getGoodsprice() {
		return goodsprice;
	}
	public void setGoodsprice(double[] goodsprice) {
		this.goodsprice = goodsprice;
	}
	
	
}
