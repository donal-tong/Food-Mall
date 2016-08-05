package com.wstmall.domain;

import java.util.HashMap;
import java.util.List;

public class ShippingAdressInfo {
	private String province;
	private List<String> city;
	HashMap<String, List<String>> citytodistric = null;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public List<String> getCity() {
		return city;
	}

	public void setCity(List<String> city) {
		this.city = city;
	}

	public HashMap<String, List<String>> getCitytodistric() {
		return citytodistric;
	}

	public void setCitytodistric(HashMap<String, List<String>> citytodistric) {
		this.citytodistric = citytodistric;
	}
}
