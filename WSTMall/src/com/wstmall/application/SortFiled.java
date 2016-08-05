package com.wstmall.application;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class SortFiled {
	private static List<String> goodsSortFiled;
	private static List<String> shopSortFiled;
	public static float height;
	public static float width;
	public static int currentSortItem = 0;
	private static AnimationSet atSet;
	private static AnimationSet atSetIn;
	private static AnimationSet atSetScLa;
	private static AnimationSet atSetImLa;
	private static AnimationSet atSetImTra;
	public static List<String> getGoodsSortFiled() {
		if (goodsSortFiled == null) {
			goodsSortFiled = new ArrayList<String>();
			goodsSortFiled.add("价格由低到高");
			goodsSortFiled.add("价格由高到低");
			goodsSortFiled.add("销量由低到高");
			goodsSortFiled.add("销量由高到低");
			goodsSortFiled.add("好评由低到高");
			goodsSortFiled.add("好评由高到低");
		}
		return goodsSortFiled;
	}

	public static List<String> getShopsSortFiled() {
		if (shopSortFiled == null) {
			shopSortFiled = new ArrayList<String>();
			shopSortFiled.add("距离由近到远");
			shopSortFiled.add("距离由远到近");
			shopSortFiled.add("配送费由低到高");
			shopSortFiled.add("配送费由高到低");
			shopSortFiled.add("好评由低到高");
			shopSortFiled.add("好评由高到低");
		}
		return shopSortFiled;
	}

	public static AnimationSet getFadeIn() {
		if (atSetIn == null) {
			atSetIn = new AnimationSet(true);
			AlphaAnimation al = new AlphaAnimation(0, 1);
			al.setDuration(1000);
			atSetIn.addAnimation(al);
		}
		return atSetIn;
	}

	public static AnimationSet getFadeOut() {
		if (atSet == null) {
			atSet = new AnimationSet(true);
			AlphaAnimation al = new AlphaAnimation(1, 0);
			al.setDuration(1000);
			atSet.addAnimation(al);
		}
		return atSet;
	}
	public static AnimationSet getScaleIn() {
		if (atSetScLa == null) {
			atSetScLa = new AnimationSet(true);
			ScaleAnimation al = new ScaleAnimation(1f, 1.3f, 1f, 1f);
			al.setDuration(800);
			atSetScLa.addAnimation(al);
		}
		return atSetScLa;
	}
	
	public static AnimationSet getatSetImLa(float x,float y,float tx,float ty) {
			atSetImLa = new AnimationSet(true);
			ScaleAnimation al = new ScaleAnimation(1f, x, 1f, y);
			al.setDuration(5000);
			TranslateAnimation tra=new TranslateAnimation(5f, -tx, 5f, -ty);
			tra.setDuration(5000);
			atSetImLa.addAnimation(tra);
			atSetImLa.addAnimation(al);
		
		return atSetImLa;
	}
	/**
	 * dp 转像素
	 * @param context
	 * @param dpValue
	 * @return
	 */
	 public static int dip2px(Context context, float dpValue) {  
         final float scale = context.getResources().getDisplayMetrics().density;  
         return (int) (dpValue * scale + 0.5f);  
     } 
	 public float getWindowsWidth(Context context){
			DisplayMetrics metric = new DisplayMetrics();  
			((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);  
		    return metric.widthPixels;     // 屏幕宽度（像素）  
	 }
	 public float getWindowsHeight(Context context){
			DisplayMetrics metric = new DisplayMetrics();  
			((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);  
		    return metric.heightPixels;     // 屏幕高度（像素）  
	 }
}
