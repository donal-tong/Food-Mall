package com.wstmall.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.wstmall.domain.ShippingAdressInfo;

public class AdressService {
	public static List<ShippingAdressInfo> getadressinfo(InputStream xml)
			throws IOException {
		List<ShippingAdressInfo> infolist = null;
		XmlPullParser parser = Xml.newPullParser();
		ShippingAdressInfo adressinfo = null;
		List<String> city = null;
		List<String> district = null;
		HashMap<String, List<String>> citytodistric = null;
		try {
			parser.setInput(xml, "UTF-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					infolist = new ArrayList<ShippingAdressInfo>();
					break;
				case XmlPullParser.START_TAG:
					if ("province".equals(parser.getName())) {
						String province = parser.getAttributeValue(0);
						adressinfo = new ShippingAdressInfo();
						city = new ArrayList<String>();
						citytodistric = new HashMap<String, List<String>>();
						adressinfo.setProvince(province);
					} else if ("city".equals(parser.getName())) {
						district = new ArrayList<String>();
						city.add(parser.getAttributeValue(0));
						adressinfo.setCity(city);
					} else if ("district".equals(parser.getName())) {
						district.add(parser.getAttributeValue(0));
					} else {

					}
				case XmlPullParser.END_TAG:
					if ("province".equals(parser.getName())) {
						infolist.add(adressinfo);
					} else if ("city".equals(parser.getName())) {
						citytodistric.put(city.get(city.size() - 1), district);
						adressinfo.setCitytodistric(citytodistric);
					}
				}

				event = parser.next();
			}

		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return infolist;

	}
}
