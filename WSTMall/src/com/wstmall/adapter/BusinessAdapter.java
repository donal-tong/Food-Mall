package com.wstmall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.BusinessListbean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessAdapter extends MyBaseAdapter<BusinessListbean> {

	public BusinessAdapter(Context context, List<BusinessListbean> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.business_list_item, null);
			holder = new ViewHolder();
			holder.businessimager = (ImageView) convertView.findViewById(R.id.im_business);
			holder.tvname = (TextView) convertView.findViewById(R.id.tv_business_name);
			holder.price = (TextView) convertView.findViewById(R.id.tv_business_price);
			holder.distance = (TextView) convertView.findViewById(R.id.tv_business_distance);
			holder.describe = (TextView) convertView.findViewById(R.id.tv_business_describe);
			holder.businessadress = (TextView) convertView.findViewById(R.id.tv_business_adress);
			holder.stars = new ArrayList<ImageView>();
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_star_first));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_star_second));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_star_third));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_star_fouth));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_star_fifth));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BusinessListbean business = getItem(position);

		holder.tvname.setText(business.shopName);
		holder.price.setText(df0.format(business.deliveryStartMoney) + "");
		holder.distance.setText(df00.format(business.userDistance) + "km");
		holder.describe.setText("主营:" + business.catName);
		holder.businessadress.setText("地址:" + business.shopAddress);
		
		if(business.shopImg==null||business.shopImg.equals("")){
			holder.businessimager.setImageResource(R.drawable.ic_launcher);
		}else{
		((BaseActivity) mContext).loadOnImage(Const.BASE_URL + business.shopImg, holder.businessimager);
		}
		
		if (business.totalScore == 0) {
			int i = 1;
			for (; i < holder.stars.size(); i++) {
				if (setStars(holder.stars.get(i), i, 3)) {
					break;
				}
			}
			i++;
			for (; i < holder.stars.size(); i++) {
				holder.stars.get(i).setImageResource(R.drawable.star_gray);
			}
		} else {
			int i = 1;
			for (; i < holder.stars.size(); i++) {
				if (setStars(holder.stars.get(i), i, business.totalScore)) {
					break;
				}
			}
			i++;
			for (; i < holder.stars.size(); i++) {
				holder.stars.get(i).setImageResource(R.drawable.star_gray);
			}
		}

		return convertView;
	}
	
	private Boolean setStars(ImageView stars, int num, double starsScore) {
		Double tempNum = (double) (num + 1);
		if (starsScore == tempNum) {
			stars.setImageResource(R.drawable.star_yellow);
			return true;
		} else if (starsScore < tempNum) {
			stars.setImageResource(R.drawable.star_half_color);
			return true;
		} else {
			stars.setImageResource(R.drawable.star_yellow);
			return false;
		}
	}

	private class ViewHolder {
		TextView tvname;
		TextView price;
		TextView distance;
		TextView describe;
		TextView businessadress;
		List<ImageView> stars;
		ImageView businessimager;
	}
}
