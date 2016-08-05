package com.wstmall.adapter;

import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.order.AddAppraisesActivity;
import com.wstmall.activity.widget.HtmlViewActivity;
import com.wstmall.api.pay.ToPay;
import com.wstmall.application.Const;
import com.wstmall.bean.OrderBean;
import com.wstmall.fragment.order.EvaluationFragment;
import com.wstmall.fragment.shoppingCart.ShoppingCartFragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderExpandAdapter extends BaseExpandableListAdapter {
	private final static int GOOD_ITEM = 0;
	private final static int BOTTOM_ITEM = 1;

	private LayoutInflater minflater;
	private Context context;
	private List<OrderBean> orderbeanlist;

	public OrderExpandAdapter(Context context, List<OrderBean> orderbeanlist) {
		this.context = context;
		this.orderbeanlist = orderbeanlist;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return orderbeanlist.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return orderbeanlist.get(groupPosition).goodlist.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if (orderbeanlist.get(groupPosition).goodlist.size() != childPosition + 1) {
			return GOOD_ITEM;
		} else {
			return BOTTOM_ITEM;
		}
	}

	@Override
	public int getChildTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		OrderShopViewHolder sholder = null;
		if (convertView == null) {
			sholder = new OrderShopViewHolder();
			convertView = minflater.inflate(R.layout.fragment_order_shop_item,
					null);
			sholder.tv_order_shop_name = (TextView) convertView
					.findViewById(R.id.tv_order_shop_name);
			sholder.tv_order_good_state = (TextView) convertView
					.findViewById(R.id.tv_order_good_state);
			convertView.setTag(sholder);
		} else {
			sholder = (OrderShopViewHolder) convertView.getTag();
		}
		// 5:门店同意取消 -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 -1：用户取消 0:未受理 1:已受理 2:打包中 3:配送中
		// 4:已到货 5:门店确认已收货
		sholder.tv_order_shop_name
				.setText(orderbeanlist.get(groupPosition).shopName);
		switch (orderbeanlist.get(groupPosition).orderStatus) {
		// -7\-6\-1:用户取消 -5:门店不同意拒收 -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 0:未受理 1:已受理
		// 2:打包中 3:配送中 4:用户确认收货
		case -7:
			sholder.tv_order_good_state.setText("用户取消");
			break;
		case -6:
			sholder.tv_order_good_state.setText("用户取消");
			break;
		case -1:
			sholder.tv_order_good_state.setText("用户取消");
			break;
		case -5:
			sholder.tv_order_good_state.setText("不同意拒收");
			break;
		case -4:
			sholder.tv_order_good_state.setText("同意拒收");
			break;
		case -3:
			sholder.tv_order_good_state.setText("用户拒收");
			break;
		case -2:
			sholder.tv_order_good_state.setText("未付款");
			break;
		case 0:
			sholder.tv_order_good_state.setText("未受理 ");
			break;
		case 1:
			sholder.tv_order_good_state.setText("已受理");
			break;
		case 2:
			sholder.tv_order_good_state.setText("打包中");
			break;
		case 3:
			sholder.tv_order_good_state.setText("配送中");
			break;
		case 4:
			sholder.tv_order_good_state.setText("已确认收货 ");
			break;

		}

		// convertView.setClickable(true);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		OrderBotViewHolder gholder = null;
		OrderGoodViewHolder ngholder = null;
		if (convertView == null) {
			if (orderbeanlist.get(groupPosition).goodlist.size() != childPosition + 1) {
				convertView = minflater.inflate(
						R.layout.fragment_order_good_item, null);
				ngholder = new OrderGoodViewHolder();
				ngholder.im_order_good_img = (ImageView) convertView
						.findViewById(R.id.im_order_good_img);
				ngholder.tv_order_good_name = (TextView) convertView
						.findViewById(R.id.tv_order_good_name);
				ngholder.tv_order_good_price = (TextView) convertView
						.findViewById(R.id.tv_order_good_price);
				ngholder.tv_order_good_count = (TextView) convertView
						.findViewById(R.id.tv_order_good_count);
				convertView.setTag(ngholder);
			} else {
				convertView = minflater.inflate(
						R.layout.fragment_order_bottom_item, null);
				gholder = new OrderBotViewHolder();
				gholder.im_order_good_img = (ImageView) convertView
						.findViewById(R.id.im_order_good_img);
				gholder.tv_order_good_name = (TextView) convertView
						.findViewById(R.id.tv_order_good_name);

				gholder.tv_order_good_price = (TextView) convertView
						.findViewById(R.id.tv_order_good_price);
				gholder.tv_order_good_count = (TextView) convertView
						.findViewById(R.id.tv_order_good_count);

				gholder.tv_order_good_total_price = (TextView) convertView
						.findViewById(R.id.tv_order_good_total_price);
				gholder.tv_order_good_bot_count = (TextView) convertView
						.findViewById(R.id.tv_order_good_bot_count);
				gholder.tv_pay = (TextView) convertView
						.findViewById(R.id.tv_pay);
				gholder.tv_cod = (TextView) convertView
						.findViewById(R.id.tv_cod);
				gholder.tv_evalution = (TextView) convertView
						.findViewById(R.id.tv_evalution);

				convertView.setTag(gholder);
			}

		} else {
			if (orderbeanlist.get(groupPosition).goodlist.size() != childPosition + 1) {
				ngholder = (OrderGoodViewHolder) convertView.getTag();

			} else {
				gholder = (OrderBotViewHolder) convertView.getTag();
			}
		}
		if (orderbeanlist.get(groupPosition).goodlist.size() != childPosition + 1) {
			((BaseActivity) context).loadOnImage(
					Const.BASE_URL
							+ orderbeanlist.get(groupPosition).goodlist
									.get(childPosition).goodsThums,
					ngholder.im_order_good_img);
			ngholder.tv_order_good_name.setText(orderbeanlist
					.get(groupPosition).goodlist.get(childPosition).goodsName);
			ngholder.tv_order_good_price.setText("￥"
					+ orderbeanlist.get(groupPosition).goodlist
							.get(childPosition).goodsPrice + "");
			ngholder.tv_order_good_count.setText("x"
					+ orderbeanlist.get(groupPosition).goodlist
							.get(childPosition).goodsNum + "");
		} else {// 最后一个
			((BaseActivity) context).loadOnImage(
					Const.BASE_URL
							+ orderbeanlist.get(groupPosition).goodlist
									.get(childPosition).goodsThums,
					gholder.im_order_good_img);
			gholder.tv_order_good_name
					.setText(orderbeanlist.get(groupPosition).goodlist
							.get(childPosition).goodsName);
			gholder.tv_order_good_price.setText("￥"
					+ orderbeanlist.get(groupPosition).goodlist
							.get(childPosition).goodsPrice + "");
			gholder.tv_order_good_count.setText("x"
					+ orderbeanlist.get(groupPosition).goodlist
							.get(childPosition).goodsNum + "");
			// count 数量和总价格
			gholder.tv_order_good_total_price.setText("实付：￥"
					+ orderbeanlist.get(groupPosition).totalMoney);
			gholder.tv_order_good_bot_count.setText("共"
					+ orderbeanlist.get(groupPosition).shopgoodcount + "件商品");
			
			if ((orderbeanlist.get(groupPosition).orderStatus == -2
					|| orderbeanlist.get(groupPosition).orderStatus == 0
					|| orderbeanlist.get(groupPosition).orderStatus == 1)&&orderbeanlist.get(groupPosition).payType.equals("1")) {
				gholder.tv_pay.setOnClickListener(new ToPayClickListener(
						orderbeanlist.get(groupPosition)));
				gholder.tv_pay.setVisibility(View.VISIBLE);
				gholder.tv_cod.setVisibility(View.GONE);
				gholder.tv_evalution.setVisibility(View.GONE);
			} else if (orderbeanlist.get(groupPosition).payType.equals("0")&&orderbeanlist.get(groupPosition).orderStatus != 4) {
				gholder.tv_cod.setVisibility(View.VISIBLE);
				gholder.tv_pay.setVisibility(View.GONE);
				gholder.tv_evalution.setVisibility(View.GONE);
			} else if (orderbeanlist.get(groupPosition).orderStatus == 4&&orderbeanlist.get(groupPosition).isAppraises==0) {
				gholder.tv_evalution.setVisibility(View.VISIBLE);
				gholder.tv_evalution.setText("去评价");
				gholder.tv_evalution.setOnClickListener(new ToAddAppraises(groupPosition));
				gholder.tv_pay.setVisibility(View.GONE);
				gholder.tv_cod.setVisibility(View.GONE);
			}else if(orderbeanlist.get(groupPosition).orderStatus == 4&&orderbeanlist.get(groupPosition).isAppraises==1){
				gholder.tv_evalution.setVisibility(View.VISIBLE);
				gholder.tv_evalution.setText("已评价");
				gholder.tv_pay.setVisibility(View.GONE);
				gholder.tv_cod.setVisibility(View.GONE);
			} else if(orderbeanlist.get(groupPosition).orderStatus == -7||orderbeanlist.get(groupPosition).orderStatus == -6||orderbeanlist.get(groupPosition).orderStatus == -1){
				gholder.tv_pay.setVisibility(View.GONE);
				gholder.tv_cod.setVisibility(View.GONE);
				gholder.tv_evalution.setVisibility(View.GONE);
			}

		}
		convertView.setOnClickListener(new OrderChildItemClick(groupPosition,
				childPosition));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	private class ToPayClickListener implements OnClickListener {
		private OrderBean orderbean;

		public ToPayClickListener(OrderBean orderbean) {
			this.orderbean = orderbean;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToPay toPay = new ToPay();
			toPay.orderId = orderbean.orderId;
			toPay.tokenId = Const.cache.getTokenId();

			Intent intent = new Intent(context, HtmlViewActivity.class);
			intent.putExtra("title", "在线支付");
			intent.putExtra("Url",
					Const.API_BASE_URL + toPay.getA() + toPay.getString());
			context.startActivity(intent);
		}

	}

	private class ToAddAppraises implements OnClickListener {
		private int position;

		public ToAddAppraises(int position) {
			this.position = position;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, AddAppraisesActivity.class);
			intent.putExtra("orderId",position);
			context.startActivity(intent);
		}

	}

	static class OrderShopViewHolder {
		TextView tv_order_shop_name;
		TextView tv_order_good_state;

	}

	static class OrderGoodViewHolder {

		ImageView im_order_good_img;
		TextView tv_order_good_name;
		TextView tv_order_good_price;
		TextView tv_order_good_count;
	}

	static class OrderBotViewHolder {

		ImageView im_order_good_img;
		TextView tv_order_good_name;
		TextView tv_order_good_price;
		TextView tv_order_good_count;
		TextView tv_order_good_total_price;
		TextView tv_order_good_bot_count;
		TextView tv_pay;
		TextView tv_cod;
		TextView tv_evalution;
	}

	public class OrderChildItemClick implements OnClickListener {
		private int groupPosition;
		private int childPosition;

		OrderChildItemClick(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, GoodsActivity.class);
			intent.putExtra(GoodsActivity.goodsID, orderbeanlist
					.get(groupPosition).goodlist.get(childPosition).goodsId);
			context.startActivity(intent);
		}

	}
}
