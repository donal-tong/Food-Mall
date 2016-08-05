package com.wstmall.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
/**
 * 推荐商品适配器
 * @author pzl
 *@param List<View> 放置每个包含三个推荐商品的view
 */
public class ReAdapter extends PagerAdapter{
		private List<View> listview;
		public ReAdapter(List<View> listview){
			this.listview=listview;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(listview.get(position));
			
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			
			try{
			container.addView(listview.get(position),0);
			}
			catch(Exception e){
				
			}
			return listview.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
	}
