package com.wstmall.domain;

import android.graphics.Bitmap;

/**
 * 商家信息
 * 
 * @author pzl
 * @param image
 *            商店图片</br> name 店名 </br> distance 距离 </br>price 其配送价格</br>
 *            description 商店描述 </br> stars 评价
 * 
 */
public class BusinessInfo {
	private Bitmap image;
	private String name;
	private double distance;
	private double price;
	private String description;
	private double stars;

	public BusinessInfo(Bitmap image, String name, double distance,
			double price, String description, double stars) {
		super();
		this.image = image;
		this.name = name;
		this.distance = distance;
		this.price = price;
		this.description = description;
		this.stars = stars - 1;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

}
