package com.wstmall.adapter;

import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.fragment.business.BusinessHomeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessGoodsAdapter extends MyBaseAdapter<GoodsListBean>{
	
	private BusinessHomeFragment businessHomeFragment;

	public BusinessGoodsAdapter(Context context, List<GoodsListBean> list,BusinessHomeFragment businessHomeFragment) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.businessHomeFragment=businessHomeFragment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_business_home_goods, null);
			holder = new ViewHolder();
			holder.im = (ImageView) convertView.findViewById(R.id.business_home_goods_img);
			holder.describe = (TextView) convertView.findViewById(R.id.business_home_goodsdescribe);
			holder.price = (TextView) convertView.findViewById(R.id.business_home_goodsprice);
			holder.iv_add_spc=(ImageView) convertView.findViewById(R.id.iv_add_spc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		((BaseActivity) mContext).loadOnRectangleImage(Const.BASE_URL + getItem(position).goodsThums, holder.im);
		holder.describe.setText(getItem(position).goodsName);
		holder.price.setText(df0.format(Double.parseDouble(getItem(position).shopPrice)));
		holder.iv_add_spc.setOnClickListener(new spconclick(holder.iv_add_spc, position));
		
		return convertView;
	}

	private class ViewHolder {
		ImageView im,iv_add_spc;
		TextView describe,price;
	}
	
	private class spconclick implements OnClickListener {
		private ImageView iv_add_spc;
		private int position;

		public spconclick(ImageView iv_add_spc, int position) {
			this.iv_add_spc = iv_add_spc;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			businessHomeFragment.addShopCart(iv_add_spc);
			Const.cache.addShoppingCartList(getItem(position));
		}
	}
}
