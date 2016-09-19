package com.wstmall.widget;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsListBean;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HorizontalScrollViewAdapter
{

	private Context context;
	private LayoutInflater mInflater;
	private List<GoodsListBean> goodlistbean;
	private int mScreenWitdh;
	public HorizontalScrollViewAdapter(Context context, List<GoodsListBean> goodlistbean)
	{
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.goodlistbean = goodlistbean;
		WindowManager wm = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics outMetrics = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(outMetrics);
			 mScreenWitdh = outMetrics.widthPixels;
	}

	public int getCount()
	{
		return  goodlistbean.size();
	}

	public Object getItem(int position)
	{
		return goodlistbean.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.mainpage_recommend_goods_tiem, parent, false);
			convertView.setLayoutParams(new LinearLayout.LayoutParams(
					mScreenWitdh / 3,
					LinearLayout.LayoutParams.WRAP_CONTENT));
			viewHolder.im1 = (ImageView) convertView
					.findViewById(R.id.recommend_first);
			viewHolder.tvname = (TextView) convertView
					.findViewById(R.id.tv_main_shop_name_first);
			viewHolder.tvgoods = (TextView) convertView
					.findViewById(R.id.tv_main_goods_name_first);
			viewHolder.tvprice = (TextView) convertView
					.findViewById(R.id.tv_main_price_first);
			viewHolder.imaddshopcar = (ImageView) convertView
					.findViewById(R.id.im_shoppingcar_first);

			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		((BaseActivity) context).loadOnRectangleImage(
				Const.BASE_URL
						+ goodlistbean
								.get(position).goodsThums, viewHolder.im1);
		viewHolder.im1.setOnClickListener(new goodsonclick(position));
		viewHolder.tvname
				.setText(goodlistbean
						.get(position).shopName);
		viewHolder.tvgoods
				.setText(goodlistbean
						.get(position).goodsName + "");
		viewHolder.tvprice
				.setText("￥"
						+ goodlistbean
								.get(position).shopPrice + "");
		viewHolder.imaddshopcar.setOnClickListener(new Addshoppingcar(
				goodlistbean, viewHolder.imaddshopcar,position));

		return convertView;
	}

	private class ViewHolder
	{
		ImageView im1 ;
		TextView tvname;
		TextView tvgoods;
		TextView tvprice ;
		ImageView imaddshopcar;
	}
	// 加入购物车监听
		class Addshoppingcar implements OnClickListener {
			private List<GoodsListBean> goodlistbean;
			private ImageView imaddshopcar;
			private int goodsnum;

			public Addshoppingcar(
					List<GoodsListBean> goodlistbean,
					ImageView imaddshopcar, int goodsnum) {
				this.goodlistbean = goodlistbean;
				this.imaddshopcar = imaddshopcar;
				this.goodsnum = goodsnum;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.mainActivity.addShopCart(imaddshopcar);
				Const.cache
						.addShoppingCartList(goodlistbean
								.get(goodsnum));
			}

		}

		class goodsonclick implements OnClickListener {
			private int goodnum;

			public goodsonclick( int goodnum) {
				this.goodnum = goodnum;
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, GoodsActivity.class);
				intent.putExtra(
						GoodsActivity.goodsID,
						goodlistbean.get(goodnum).goodsId);
				context.startActivity(intent);
			}

		}
}
