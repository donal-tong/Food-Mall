package com.wstmall.bean;

import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

public abstract class AbstractParam {
	
	private String a;

	public String getString() {
		StringBuilder sb = new StringBuilder("/?");//之后跟的是参数
		reflectFiled(sb);
		return sb.toString().substring(0, sb.length() - 1);
	}

	public String postString() {
		StringBuilder sb = new StringBuilder();
		reflectFiled(sb);
		return sb.toString().substring(0, sb.length() - 1);
	}
	
	public RequestParams getChildFatherRequestParam() {
		RequestParams requestParams = new RequestParams();
		Class clazz = getClass();
		Field[] field = clazz.getDeclaredFields();
		try {
			for (Field f : field) {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equals("a")) {
					if (f.getType() == File.class) {
						requestParams.put(f.getName(), (File) f.get(this));
					} else {
						requestParams.put(f.getName(), f.get(this).toString());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		clazz = getClass().getSuperclass();
		field = clazz.getDeclaredFields();
		try {
			for (Field f : field) {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equals("a")) {
					if (f.getType() == File.class) {
						requestParams.put(f.getName(), (File) f.get(this));
					} else {
						requestParams.put(f.getName(), f.get(this).toString());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return requestParams;
	}

	public RequestParams getRequestParam() {
		RequestParams requestParams = new RequestParams();
		Class clazz = getClass();
		Field[] field = clazz.getDeclaredFields();
		try {
			for (Field f : field) {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equals("a")) {
					if (f.getType() == File.class) {
						requestParams.put(f.getName(), (File) f.get(this));
					} else {
						requestParams.put(f.getName(), f.get(this).toString());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return requestParams;
	}

	private void reflectFiled(StringBuilder sb) {
		Class clazz = getClass();
		Field[] field = clazz.getDeclaredFields();
		try {
			for (Field f : field) {
				f.setAccessible(true);
				if (f.get(this) != null && !f.getName().equals("a")) {
					sb.append(f.getName()).append("=").append(f.get(this).toString()).append("&");
				}
				if (f.getName().equals("a")) {
					a = f.get(this).toString();
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public String getA() {
		return a;
	}
}
