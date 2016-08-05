package com.wstmall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.fragment.goods.GoodsListFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListAdapter extends MyBaseAdapter<GoodsListBean> {
	private GoodsListFragment goodslistfragment;

	public GoodsListAdapter(Context context, List<GoodsListBean> list, GoodsListFragment goodslistfragment) {
		super(context, list);
		this.goodslistfragment = goodslistfragment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.goods_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.tv_goods_business_name);
			holder.gooddescribe = (TextView) convertView.findViewById(R.id.tv_goods_describe);
			holder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
			holder.tv_goods_freigprice = (TextView) convertView.findViewById(R.id.tv_goods_freigprice);
			holder.tv_goods_distance = (TextView) convertView.findViewById(R.id.tv_goods_distance);
			holder.goodimg = (ImageView) convertView.findViewById(R.id.im_goods);
			holder.iv_add_spc = (ImageView) convertView.findViewById(R.id.iv_add_spc);

			holder.stars = new ArrayList<ImageView>();
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_goods_star_first));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_goods_star_second));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_goods_star_third));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_goods_star_fouth));
			holder.stars.add((ImageView) convertView.findViewById(R.id.im_goods_star_fifth));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		GoodsListBean goosinfo = getItem(position);
		holder.name.setText(goosinfo.shopName);
		holder.gooddescribe.setText(goosinfo.goodsName);
		holder.tv_goods_price.setText(df0.format(Double.parseDouble(goosinfo.shopPrice)) + "");
		holder.tv_goods_freigprice.setText(df0.format(goosinfo.deliveryStartMoney) + "");
		holder.tv_goods_distance.setText(df00.format(goosinfo.userDistance) + "km");
		
		if(goosinfo.goodsThums==null||goosinfo.goodsThums.equals("")){
			holder.goodimg.setImageResource(R.drawable.ic_launcher);
		}else{
		((BaseActivity) mContext).loadOnRectangleImage(Const.BASE_URL + goosinfo.goodsThums, holder.goodimg);
		}
		if (goosinfo.score == 0) {
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
				if (setStars(holder.stars.get(i), i, goosinfo.score)) {
					break;
				}
			}
			i++;
			for (; i < holder.stars.size(); i++) {
				holder.stars.get(i).setImageResource(R.drawable.star_gray);
			}
		}

		holder.iv_add_spc.setOnClickListener(new spconclick(holder.iv_add_spc, position));
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
		TextView name, gooddescribe, tv_goods_price, tv_goods_freigprice, tv_goods_distance;
		ImageView goodimg, iv_add_spc;
		List<ImageView> stars;
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
			goodslistfragment.addShopCart(iv_add_spc);
			Const.cache.addShoppingCartList(getItem(position));
		}
	};
}
