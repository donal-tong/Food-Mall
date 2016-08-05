package com.wstmall.adapter;

import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.goodlist.GoodListActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.application.Const;
import com.wstmall.bean.RecommendGoodsBean;
import com.wstmall.fragment.goods.GoodsListFragment;
import com.wstmall.widget.HorizontalScrollViewAdapter;
import com.wstmall.widget.MyHorizontalScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ReGoodsAdapter extends BaseAdapter {
	private List<RecommendGoodsBean> recommendgoodlist;
	private Context context;
	private LayoutInflater minflater;
	private int windowsWidth;

	public ReGoodsAdapter(Context context,
			List<RecommendGoodsBean> recommendgoodlist) {
		this.context = context;
		this.recommendgoodlist = recommendgoodlist;
		WindowManager wm1 = ((Activity) context).getWindowManager();
		this.windowsWidth = wm1.getDefaultDisplay().getWidth();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recommendgoodlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ReHolder reHolder=null;
		if(convertView==null){
			reHolder=new ReHolder();
			convertView = minflater.inflate(R.layout.mainpage_recommend_item,
					null);
				reHolder.tv_recommendation_name = (TextView) convertView
						.findViewById(R.id.tv_recommendation_name);
				reHolder.bt_main_more = (ImageView) convertView
						.findViewById(R.id.bt_main_more);
				reHolder.mHorizontalScrollView = (MyHorizontalScrollView) convertView.findViewById(R.id.id_horizontalScrollView);
			
				convertView.setTag(reHolder);	
		}else{
			reHolder=(ReHolder) convertView.getTag();
		}
		
			/*reHolder.ll_recommend_goods = (LinearLayout) convertView
						.findViewById(R.id.ll_recommend_goods);
				for (int i = 1; i < 4; i++) {
					View reView = View.inflate(context,
							R.layout.mainpage_recommend_goods_tiem, null);
				ImageView im2 = (ImageView) reView
							.findViewById(R.id.recommend_first);
					TextView tvname = (TextView) reView
							.findViewById(R.id.tv_main_shop_name_first);
					TextView tvgoods = (TextView) reView
							.findViewById(R.id.tv_main_goods_name_first);
					TextView tvprice = (TextView) reView
							.findViewById(R.id.tv_main_price_first);
					ImageView imaddshopcar = (ImageView) reView
							.findViewById(R.id.im_shoppingcar_first);	
					reView.setLayoutParams(new LinearLayout.LayoutParams(
							windowsWidth / 3,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					reHolder.ll_recommend_goods.addView(reView);
				}
			convertView.setTag(reHolder);*/
		reHolder.bt_main_more.setOnClickListener(new MoreGoodsOnClickListener(
				position));
		reHolder.tv_recommendation_name
				.setText(recommendgoodlist.get(position).catName);
		HorizontalScrollViewAdapter mAdapter = new HorizontalScrollViewAdapter(context, recommendgoodlist.get(position).goodlistbean);
		reHolder.mHorizontalScrollView.initDatas(mAdapter);
		return convertView;
	}

	static class ReHolder {
		TextView tv_recommendation_name;
		ImageView bt_main_more;
		MyHorizontalScrollView mHorizontalScrollView;
	}

	class MoreGoodsOnClickListener implements OnClickListener {
		private int position;

		public MoreGoodsOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, GoodListActivity.class);
			intent.putExtra(GoodsListFragment.Mode_GoodsCatIdOne,
					Integer.parseInt(recommendgoodlist.get(position).catId));
			context.startActivity(intent);
		}

	}

	
}
