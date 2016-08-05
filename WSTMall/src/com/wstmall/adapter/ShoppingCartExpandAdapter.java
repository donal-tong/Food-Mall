package com.wstmall.adapter;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.application.Const;
import com.wstmall.domain.ShoppingCart;
import com.wstmall.fragment.shoppingCart.ShoppingCartFragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShoppingCartExpandAdapter extends BaseExpandableListAdapter {
	protected static final String TAG = "你麻痹";
	private Context context;
	private CheckBox cb_child;
	private CheckBox cb_group;
	private int child_check_count;
	private ShopTotalPriceChange totalpricechange;

	public ShoppingCartExpandAdapter(Context context) {
		this.context = context;

	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return ShoppingCartFragment.shoppinglist.size();
	}

	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ShoppingCartFragment.shoppinglist.get(groupPosition).child
				.size();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		// return shoppinglist.get(groupPosition);
		return null;
	}

	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		// return
		// shoppinglist.get(groupPosition).getGoodsimg().get(childPosition);
		return null;
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ShoppingCart spc = ShoppingCartFragment.shoppinglist.get(groupPosition);
		View viewbusiness = View.inflate(context,
				R.layout.shoppingcar_business_item, null);

		TextView name = (TextView) viewbusiness
				.findViewById(R.id.tv_shoppingcar_businessname);
		name.setText(spc.shopname);
		name.getPaint().setFakeBoldText(true);
		TextView free_price = (TextView) viewbusiness
				.findViewById(R.id.tv_free_price);
			if(spc.deliveryFreeMoney!=0){
			free_price.setText("亲，购买商品达到" + spc.deliveryFreeMoney + "元,可以免运费");
			}else{
				free_price.setText("店铺免运费");
			}
		
		// checkbox
		cb_group = (CheckBox) viewbusiness.findViewById(R.id.cb_group);

		cb_group.setVisibility(View.VISIBLE);
		cb_group.setChecked(ShoppingCartFragment.shoppinglist
				.get(groupPosition).cbgroup);
		cb_group.setOnCheckedChangeListener(new groupcheck(groupPosition));

		viewbusiness.setClickable(true);
		return viewbusiness;
	}

	class groupcheck implements OnCheckedChangeListener {
		private int groupPosition;

		groupcheck(int groupPosition) {
			this.groupPosition = groupPosition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = true;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size(); i++) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).child
							.get(i).cbchild = true;
					if (totalpricechange != null) {
						totalpricechange.totalPriceChange();
					}
					notifyDataSetChanged();
				}
			} else {
				ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = false;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size(); i++) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).child
							.get(i).cbchild = false;
					if (totalpricechange != null) {
						totalpricechange.totalPriceChange();
					}
					notifyDataSetChanged();
				}

			}
		}

	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View viewgoods = View.inflate(context, R.layout.shoppingcar_goods_item,
				null);
		ImageView im = (ImageView) viewgoods
				.findViewById(R.id.im_shppingcar_godsimg);
		((BaseActivity) context)
				.loadOnImage(
						Const.BASE_URL
								+ ShoppingCartFragment.shoppinglist
										.get(groupPosition).child
										.get(childPosition).goodsThums, im);
		TextView goods_describe = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_describe);
		goods_describe.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).goodsName);
		goods_describe.getPaint().setFakeBoldText(true);

		LinearLayout ll_spc_good_item = (LinearLayout) viewgoods
				.findViewById(R.id.ll_spc_good_item);

		ll_spc_good_item.setOnClickListener(new ChildItemClick(groupPosition, childPosition));

		TextView goods_price = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_price);
		goods_price.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).shopPrice + "");

		TextView goods_count = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_count);
		TextView tv_goods_attrs= (TextView) viewgoods
				.findViewById(R.id.tv_goods_attrs);
		goods_count.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).goodscount + "");
		TextView goods_count_right = (TextView) viewgoods
				.findViewById(R.id.tv_shoppingcar_goods_count_right);
		goods_count_right.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).goodscount + "");
		if(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).priceAttrName!=null){
		tv_goods_attrs.setText(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).priceAttrName+":"+ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).priceAttrVal);
		}
		// checkbox
		cb_child = (CheckBox) viewgoods.findViewById(R.id.cb_child);
		cb_child.setChecked(ShoppingCartFragment.shoppinglist
				.get(groupPosition).child.get(childPosition).cbchild);

		cb_child.setOnCheckedChangeListener(new childcheck(groupPosition,
				childPosition));
		return viewgoods;
	}

	class childcheck implements OnCheckedChangeListener {
		private int groupPosition;
		private int childPosition;

		childcheck(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			if (isChecked) {
				ShoppingCartFragment.shoppinglist.get(groupPosition).child
						.get(childPosition).cbchild = true;
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
				}
				// 判断是否全选
				child_check_count = 0;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size(); i++) {
					if (ShoppingCartFragment.shoppinglist.get(groupPosition).child
							.get(i).cbchild) {

						child_check_count = child_check_count + 1;
					}
				}
				if (child_check_count == ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size()) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = true;
				} else {
					ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = false;
				}
				notifyDataSetChanged();
			} else {
				ShoppingCartFragment.shoppinglist.get(groupPosition).child
						.get(childPosition).cbchild = false;
				child_check_count = 0;
				for (int i = 0; i < ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size(); i++) {
					if (ShoppingCartFragment.shoppinglist.get(groupPosition).child
							.get(i).cbchild) {

						child_check_count = child_check_count + 1;

					}
				}
				if (child_check_count == ShoppingCartFragment.shoppinglist
						.get(groupPosition).child.size()) {
					ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = true;
				} else {
					ShoppingCartFragment.shoppinglist.get(groupPosition).cbgroup = false;
				}
				if (totalpricechange != null) {
					totalpricechange.totalPriceChange();
				}
				notifyDataSetChanged();
			}

		}

	}

	public class ChildItemClick implements OnClickListener {
		private int groupPosition;
		private int childPosition;

		ChildItemClick(int groupPosition, int childPosition) {
			this.groupPosition = groupPosition;
			this.childPosition = childPosition;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, GoodsActivity.class);
			intent.putExtra(GoodsActivity.goodsID, ShoppingCartFragment.shoppinglist
					.get(groupPosition).child.get(childPosition).goodsId);
			context.startActivity(intent);
		}

	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	public interface ShopTotalPriceChange {
		void totalPriceChange();
	}

	public void setTotalPriceChangeListener(
			ShopTotalPriceChange totalpricechange) {
		this.totalpricechange = totalpricechange;
	}
}
