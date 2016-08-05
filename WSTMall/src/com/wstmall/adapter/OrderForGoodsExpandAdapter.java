package com.wstmall.adapter;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.Const;
import com.wstmall.fragment.order_goods.OrderForGoodsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderForGoodsExpandAdapter extends BaseExpandableListAdapter {
	private static final String TAG = "订单设配器";
	private Context context;
	private LayoutInflater minflater;

	public OrderForGoodsExpandAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return OrderForGoodsFragment.goodsfororder.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				.size();
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

		convertView = minflater.inflate(R.layout.goods_for_order_shop_item,
				null);
		TextView tv_shopName = (TextView) convertView
				.findViewById(R.id.tv_shopName);

		TextView tv_isDelivery = (TextView) convertView
				.findViewById(R.id.tv_isDelivery);

		TextView tv_isMinPrice = (TextView) convertView
				.findViewById(R.id.tv_isMinPrice);
		if (OrderForGoodsFragment.goodsfororder.get(groupPosition).communityId != null
				&& OrderForGoodsFragment.adressBean != null) {
			if (!OrderForGoodsFragment.goodsfororder.get(groupPosition).isdelivery) {
				tv_isDelivery.setVisibility(View.VISIBLE);
				tv_isDelivery.setText("抱歉,收货地址不在配送区域");
			}
		}
		if (!OrderForGoodsFragment.goodsfororder.get(groupPosition).isMinDeliveryPrice) {
			tv_isMinPrice.setVisibility(View.VISIBLE);
			tv_isMinPrice.setText("商品不满足店铺最低下单价格要求");
		}
		tv_shopName.getPaint().setFakeBoldText(true);
		tv_shopName.setText(OrderForGoodsFragment.goodsfororder
				.get(groupPosition).shopName);
		convertView.setClickable(true);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		minflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Cholder cholder = null;
		CholderBottom cholderbot = null;
		if (convertView == null) {
			//
			if ((childPosition + 1) != OrderForGoodsFragment.goodsfororder
					.get(groupPosition).goodslist.size()) {
				convertView = minflater.inflate(
						R.layout.goods_for_order_goods_item, null);
				cholder = new Cholder();
				cholder.im_goods = (ImageView) convertView
						.findViewById(R.id.im_goods);
				cholder.tv_goods_goods_name = (TextView) convertView
						.findViewById(R.id.tv_goods_goods_name);
				cholder.tv_goods_price = (TextView) convertView
						.findViewById(R.id.tv_goods_price);
				cholder.tv_goods_stock = (TextView) convertView
						.findViewById(R.id.tv_goods_stock);
				cholder.tv_goods_book = (TextView) convertView
						.findViewById(R.id.tv_goods_book);
				cholder.tv_goods_nums = (TextView) convertView
						.findViewById(R.id.tv_goods_nums);
				convertView.setTag(cholder);
				((BaseActivity) context).loadOnImage(
						Const.BASE_URL
								+ OrderForGoodsFragment.goodsfororder
										.get(groupPosition).goodslist
										.get(childPosition).goodsThums,
						cholder.im_goods);
				cholder.tv_goods_goods_name
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsName);
				cholder.tv_goods_price
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).shopPrice
								+ "元");
				cholder.tv_goods_stock.setText("库存:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsStock);
				cholder.tv_goods_nums.setText("规格:x"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsNum);
				/*
				 * if
				 * (OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				 * .get(childPosition).goodsStock > 0) {
				 * cholder.tv_goods_book.setText("可预订"); } else {
				 * cholder.tv_goods_book.setText("不可预订"); }
				 */
			} else {
				//
				convertView = minflater.inflate(
						R.layout.fragment_order_good_item_bottom, null);

				cholderbot = new CholderBottom();
				cholderbot.tv_goods_nums = (TextView) convertView
						.findViewById(R.id.tv_goods_nums);
				cholderbot.im_goods = (ImageView) convertView
						.findViewById(R.id.im_goods);
				cholderbot.tv_goods_goods_name = (TextView) convertView
						.findViewById(R.id.tv_goods_goods_name);
				cholderbot.tv_goods_price = (TextView) convertView
						.findViewById(R.id.tv_goods_price);
				cholderbot.tv_goods_stock = (TextView) convertView
						.findViewById(R.id.tv_goods_stock);
				cholderbot.tv_goods_book = (TextView) convertView
						.findViewById(R.id.tv_goods_book);

				cholderbot.tv_deliveryType = (TextView) convertView
						.findViewById(R.id.tv_deliveryType);
				cholderbot.tv_shopAtive = (TextView) convertView
						.findViewById(R.id.tv_shopAtive);
				cholderbot.tv_deliveryTime = (TextView) convertView
						.findViewById(R.id.tv_deliveryTime);

				cholderbot.tv_deliveryStartMoney = (TextView) convertView
						.findViewById(R.id.tv_deliveryStartMoney);
				cholderbot.tv_deliveryFreeMoney = (TextView) convertView
						.findViewById(R.id.tv_deliveryFreeMoney);
				cholderbot.tv_deliveryMoney = (TextView) convertView
						.findViewById(R.id.tv_deliveryMoney);
				cholderbot.tv_serviceTime = (TextView) convertView
						.findViewById(R.id.tv_serviceTime);
				convertView.setTag(cholderbot);

				((BaseActivity) context).loadOnImage(
						Const.BASE_URL
								+ OrderForGoodsFragment.goodsfororder
										.get(groupPosition).goodslist
										.get(childPosition).goodsThums,
						cholderbot.im_goods);
				cholderbot.tv_goods_goods_name
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsName);
				cholderbot.tv_goods_price
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).shopPrice
								+ "元");
				cholderbot.tv_goods_stock.setText("库存:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsStock);
				cholderbot.tv_goods_nums.setText("规格:x"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsNum);
				/*
				 * if
				 * (OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				 * .get(childPosition).goodsStock > 0) {
				 * cholderbot.tv_goods_book.setText("可预订"); } else {
				 * cholderbot.tv_goods_book.setText("不可预订"); }
				 */
				if (OrderForGoodsFragment.goodsfororder.get(groupPosition).deliveryType
						.equals("0")) {
					cholderbot.tv_deliveryType.setText("商城配送");
				} else {
					cholderbot.tv_deliveryType.setText("门店配送");
				}
				if (OrderForGoodsFragment.goodsfororder.get(groupPosition).shopAtive
						.equals("0")) {
					cholderbot.tv_shopAtive.setText("休息中");
				} else {
					cholderbot.tv_shopAtive.setText("营业中");
				}
/*				cholderbot.tv_deliveryTime.setText("配送服务时间:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryTime);
*/				
				cholderbot.tv_deliveryTime.setVisibility(View.GONE);
				cholderbot.tv_deliveryStartMoney.setText("起送价格:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryStartMoney + "元");
				cholderbot.tv_deliveryFreeMoney.setText("包邮起步价:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryFreeMoney + "元");
				cholderbot.tv_deliveryMoney.setText("配送费:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryMoney + "元");
				cholderbot.tv_serviceTime.setText("店铺服务时间:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).serviceStartTime
						+ ""
						+ "-"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).serviceEndTime+"点");
			}
		} else {
			if (childPosition + 1 != OrderForGoodsFragment.goodsfororder
					.get(groupPosition).goodslist.size()) {
				cholder = (Cholder) convertView.getTag();
				((BaseActivity) context).loadOnImage(
						Const.BASE_URL
								+ OrderForGoodsFragment.goodsfororder
										.get(groupPosition).goodslist
										.get(childPosition).goodsThums,
						cholder.im_goods);
				cholder.tv_goods_goods_name
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsName);
				cholder.tv_goods_price
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).shopPrice
								+ "元");
				cholder.tv_goods_stock.setText("库存:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsStock);
				cholder.tv_goods_nums.setText("规格:x"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsNum);
				/*
				 * if
				 * (OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				 * .get(childPosition).goodsStock > 0) {
				 * cholder.tv_goods_book.setText("可预订"); } else {
				 * cholder.tv_goods_book.setText("不可预订"); }
				 */
			} else {
				cholderbot = (CholderBottom) convertView.getTag();
				((BaseActivity) context).loadOnImage(
						Const.BASE_URL
								+ OrderForGoodsFragment.goodsfororder
										.get(groupPosition).goodslist
										.get(childPosition).goodsThums,
						cholderbot.im_goods);
				cholderbot.tv_goods_goods_name
						.setText(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsName);
				cholderbot.tv_goods_price.setText(+Double
						.parseDouble(OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).shopPrice)
						+ "元");
				cholderbot.tv_goods_stock.setText("库存:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsStock);
				cholderbot.tv_goods_nums.setText("规格:x"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).goodslist
								.get(childPosition).goodsNum);
				/*
				 * if
				 * (OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				 * .get(childPosition).goodsStock > 0) {
				 * cholderbot.tv_goods_book.setText("可预订"); } else {
				 * cholderbot.tv_goods_book.setText("不可预订"); }
				 */

				if (OrderForGoodsFragment.goodsfororder.get(groupPosition).deliveryType
						.equals("0")) {
					cholderbot.tv_deliveryType.setText("商城配送");
				} else {
					cholderbot.tv_deliveryType.setText("门店配送");
				}
				if (OrderForGoodsFragment.goodsfororder.get(groupPosition).shopAtive
						.equals("0")) {
					cholderbot.tv_shopAtive.setText("休息中");
				} else {
					cholderbot.tv_shopAtive.setText("营业中");
				}
/*				cholderbot.tv_deliveryTime.setText("配送服务时间:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryTime);*/
				cholderbot.tv_deliveryStartMoney.setText("起送价格:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryStartMoney + "元");
				cholderbot.tv_deliveryFreeMoney.setText("包邮起步价:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryFreeMoney + "元");
				cholderbot.tv_deliveryMoney.setText("配送费:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).deliveryMoney + "元");
				cholderbot.tv_serviceTime.setText("服务时间:"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).serviceStartTime
						+ ""
						+ "-"
						+ OrderForGoodsFragment.goodsfororder
								.get(groupPosition).serviceEndTime+"点");
			}
		}

		return convertView;
	}

	@Override
	public int getChildType(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		if (OrderForGoodsFragment.goodsfororder.get(groupPosition).goodslist
				.size() != (childPosition + 1)) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int getChildTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	static class Cholder {
		ImageView im_goods;
		TextView tv_goods_goods_name;
		TextView tv_goods_price;
		TextView tv_goods_stock;
		TextView tv_goods_book;
		TextView tv_goods_nums;
	}

	static class CholderBottom {
		ImageView im_goods;
		TextView tv_goods_goods_name;
		TextView tv_goods_price;
		TextView tv_goods_stock;
		TextView tv_goods_book;

		TextView tv_deliveryType;
		TextView tv_shopAtive;
		TextView tv_isInvoice;
		TextView tv_deliveryTime;
		TextView tv_deliveryStartMoney;
		TextView tv_deliveryFreeMoney;
		TextView tv_deliveryMoney;
		TextView tv_serviceTime;
		TextView tv_goods_nums;
	}
}
