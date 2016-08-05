package com.wstmall.adapter;

import java.util.List;

import com.wstmall.activity.widget.HtmlViewActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.Advertisement;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 广告图片适配器
 * 
 * @author panzhiling
 * @param ImageView
 *            [] 轮换的图片 Fragment 当前的Activity
 */
public class AdAdapter extends PagerAdapter {
	protected static final String TAG = "IDIDIDI";
	private ImageView[] mImageViews;
	private Fragment f;
	private List<Advertisement> advertisementlist;

	public AdAdapter(ImageView[] mImageViews, Fragment f, List<Advertisement> advertisementlist) {
		this.mImageViews = mImageViews;
		this.f = f;
		this.advertisementlist = advertisementlist;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//container.removeView(mImageViews[position % mImageViews.length]);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		try {
			mImageViews[position % mImageViews.length].setOnClickListener(new adclick(position));;
			((ViewGroup) container).addView(mImageViews[position % mImageViews.length]);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return mImageViews[position % mImageViews.length];
	}

	private class adclick implements OnClickListener {
		private int position;

		public adclick(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(advertisementlist.get(position % advertisementlist.size()).getAdURL())) {
				Intent intent = new Intent(f.getActivity(), HtmlViewActivity.class);
				intent.putExtra("title", advertisementlist.get(position % advertisementlist.size()).getAdName());
				intent.putExtra("Url", advertisementlist.get(position % advertisementlist.size()).getAdURL());
				f.startActivity(intent);
			}

		}

	}
}
