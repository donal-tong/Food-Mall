
package com.wstmall.fragment.shoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.zhy_9.food_test.R;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.order.goods.OrderForGoodsActivity;
import com.wstmall.activity.user.LoginActivity;
import com.wstmall.adapter.ShoppingCartExpandAdapter;
import com.wstmall.adapter.ShoppingCartExpandAdapter.ShopTotalPriceChange;
import com.wstmall.application.Const;
import com.wstmall.application.WSTMallApplication;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.domain.ShoppingCart;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;


@FragmentView(id = R.layout.fragment_shoppingcar_page)
public class ShoppingCartFragment extends BaseFragment implements
		View.OnClickListener, ShopTotalPriceChange {
	protected static final String TAG = "ShoppingCartFragment";

	@InjectView(id = R.id.tv_shoppingcar_businessname)
	private TextView tv_shoppingcar_businessname;
	@InjectView(id = R.id.tv_free_price)
	private TextView tv_free_price;
	@InjectView(id = R.id.tv_free_price)
	private TextView tv_poor_price;
	@InjectView(id = R.id.shopping_balance)
	private Button shopping_balance;

	@InjectView(id = R.id.cb_chioc)
	private CheckBox cb_chioc;

	@InjectView(id = R.id.ll_balance)
	private RelativeLayout ll_balance;

	@InjectView(id = R.id.ll_null_spc)
	private LinearLayout ll_null_spc;

	@InjectView(id = R.id.shopping_delete)
	private Button shopping_delete;

	@InjectView(id = R.id.tv_total_price)
	private TextView tv_total_price;
	@InjectView(id = R.id.button_space)
	private View button_space;

	@InjectView(id = R.id.el_shoppingcar)
	private ExpandableListView el_shoppingcar;

	private ShoppingCartExpandAdapter scadapter;
	public boolean flag;
	public static List<ShoppingCart> shoppinglist;
	private boolean isBalance = false;

	private ShopTotalPriceChange shop = this;
	// 购物车
	private HashMap<String, String> shoppingcar;

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("购物车");
		tWidget.setRightBtnText("编辑");
		tWidget.right.setVisibility(View.VISIBLE);
		if (getActivity()
				.getClass()
				.getName()
				.equals("com.wstmall.activity.shoppingCart.ShoppingCartWithoutMainpageActivity")) {
			tWidget.left.setVisibility(View.VISIBLE);
			button_space.setVisibility(View.GONE);
		}
		flag = false;
		shoppingcar = new HashMap<String, String>();
		shoppinglist = new ArrayList<ShoppingCart>();
		setshoppingcar();
		isSelectAll(true);
		tv_total_price.setText("合计：" +CountTotalPrice() + "元");
		scadapter = new ShoppingCartExpandAdapter(getActivity());
		scadapter.setTotalPriceChangeListener(this);
		initShopTotalMoney();
		el_shoppingcar.setAdapter(scadapter);

		for (int i = 0; i < scadapter.getGroupCount(); i++) {
			el_shoppingcar.expandGroup(i);
		}
		//
		setTotalPriceCheckAble();
	}

	@Override
	public void rightClick() {
		if (flag) {
			shopping_delete.setVisibility(View.GONE);
			shopping_balance.setVisibility(View.VISIBLE);
			ll_balance.setBackgroundResource(R.color.light_black);
			flag = false;
			scadapter.notifyDataSetChanged();
			setshoppingcar();
			tWidget.setRightBtnText("编辑");
		} else {// 编辑中
			shopping_delete.setVisibility(View.VISIBLE);
			shopping_balance.setVisibility(View.GONE);
			ll_balance.setBackgroundResource(R.color.gray_deep_other);
			flag = true;
			isSelectAll(false);
			setTotalPriceCheckAble();
			tv_total_price.setText("合计：" + CountTotalPrice() + "元");
			scadapter.notifyDataSetChanged();
			tWidget.setRightBtnText("完成");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.shopping_delete:
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确认删除？");
			builder.setTitle("提示");
			builder.setPositiveButton("确认", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					deleteShoppingCarSelect();
					setTotalPriceCheckAble();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
					Log.i(TAG, "购物车数量" + Const.cache.getShoppingCartListSize());
					scadapter.setTotalPriceChangeListener(shop);
					scadapter = new ShoppingCartExpandAdapter(getActivity());
					el_shoppingcar.setAdapter(scadapter);
					for (int i = 0; i < scadapter.getGroupCount(); i++) {
						el_shoppingcar.expandGroup(i);
					}
					shopping_delete.setVisibility(View.GONE);
					shopping_balance.setVisibility(View.VISIBLE);
					ll_balance.setBackgroundResource(R.color.light_black);
					flag = false;
					setshoppingcar();
					tWidget.setRightBtnText("编辑");
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			break;
		case R.id.shopping_balance:
			if (Const.isLogin) {
				balanceLoading();
			} else {
				isBalance = true;
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		}
	}

	private void balanceLoading() {
		if (!isShoppingListSelectNull()) {
			Intent intent = new Intent(getActivity(),
					OrderForGoodsActivity.class);
			intent.putExtra("totalprice", CountTotalPrice());
			startActivity(intent);
		} else {
			Toast.makeText(getActivity(), "请选择商品", Toast.LENGTH_LONG).show();
		}
	}

	// 判断结算时有没选择商品
	public boolean isShoppingListSelectNull() {
		int goodNum = 0;
		for (int i = 0; i < ShoppingCartFragment.shoppinglist.size(); i++) {
			for (int j = 0; j < ShoppingCartFragment.shoppinglist.get(i).child
					.size(); j++) {
				GoodsListBean bean = ShoppingCartFragment.shoppinglist.get(i).child
						.get(j);
				if (ShoppingCartFragment.shoppinglist.get(i).child.get(j).cbchild) {
					goodNum = goodNum + 1;
				}
			}
		}
		if (goodNum == 0) {
			return true;
		} else {
			return false;
		}
	}

	// 删除选中的商品
	public void deleteShoppingCarSelect() {

		int n = ShoppingCartFragment.shoppinglist.size();
		int k = 0;
		for (int i = 0; i < n; i++) {
			if (ShoppingCartFragment.shoppinglist.get(k).cbgroup) {
				// 整组删除购物车数据
				for (int a = 0; a < ShoppingCartFragment.shoppinglist.get(k).child
						.size(); a++) {
					Const.cache
							.deleteGoodsFromShoppingCartWithGoodsId(ShoppingCartFragment.shoppinglist
									.get(k).child.get(a).goodsId);
				}
				ShoppingCartFragment.shoppinglist.remove(k);
			} else {
				int m = ShoppingCartFragment.shoppinglist.get(k).child.size();
				int t = 0;
				for (int j = 0; j < m; j++) {
					if (ShoppingCartFragment.shoppinglist.get(k).child.get(t).cbchild) {
						// 删除缓存单个数据
						Const.cache
								.deleteGoodsFromShoppingCartWithGoodsId(ShoppingCartFragment.shoppinglist
										.get(k).child.get(t).goodsId);
						ShoppingCartFragment.shoppinglist.get(k).child
								.remove(t);
					} else {
						t++;
					}
				}
				k++;
			}
		}
		MainActivity.mainActivity.refreshBuyNum();
		((WSTMallApplication) getActivity().getApplication()).saveCache();
	}

	@Override
	public void onResume() {
		super.onResume();
		setshoppingcar();
		isSelectAll(true);
		setTotalPriceCheckAble();
		initShopTotalMoney();
		scadapter = new ShoppingCartExpandAdapter(getActivity());
		scadapter.setTotalPriceChangeListener(this);
		el_shoppingcar.setAdapter(scadapter);
		for (int i = 0; i < scadapter.getGroupCount(); i++) {
			el_shoppingcar.expandGroup(i);
		}
		tv_total_price.setText("合计：" +CountTotalPrice() + "元");
		if (isBalance) {
			if (Const.isLogin) {
				balanceLoading();
			}
			isBalance = false;
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		shopping_balance.setOnClickListener(this);
		shopping_delete.setOnClickListener(this);
		shopping_balance.setOnClickListener(this);
/*		el_shoppingcar.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				intent.putExtra(GoodsActivity.goodsID, shoppinglist
						.get(groupPosition).child.get(childPosition).goodsId);
				startActivity(intent);
				return false;
			}
		});*/

		/*
		 * cb_chioc.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		 * 
		 * @Override public void onCheckedChanged(CompoundButton buttonView,
		 * boolean isChecked) { // TODO Auto-generated method stub if
		 * (isChecked) { isSelectAll(true); scadapter.notifyDataSetChanged();
		 * tv_total_price.setText("合计：" + CountTotalPrice() + "元"); } else {
		 * isSelectAll(false); scadapter.notifyDataSetChanged();
		 * tv_total_price.setText("合计：" + CountTotalPrice() + "元"); } } });
		 */
		cb_chioc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (cb_chioc.isChecked()) {
					isSelectAll(true);
					scadapter.notifyDataSetChanged();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
				} else {
					isSelectAll(false);
					scadapter.notifyDataSetChanged();
					tv_total_price.setText("合计：" + CountTotalPrice() + "元");
				}
			}
		});
	}

	// 判断总计是否勾选
	public void setTotalPriceCheckAble() {
		if (TotalPrice() == CountTotalPrice() && TotalPrice() != 0) {
			cb_chioc.setChecked(true);
		} else {
			cb_chioc.setChecked(false);
		}
	}

	// 商品数据整合,同店的商品归类。
	public void setshoppingcar() { 
		if (Const.cache.getShoppingCartListSize() != 0) {
			ll_balance.setVisibility(View.VISIBLE);
			ll_null_spc.setVisibility(View.GONE);
			el_shoppingcar.setVisibility(View.VISIBLE);
			shoppinglist.clear();
			shoppingcar.clear();
			for (int i = 0; i < Const.cache.getShoppingCartListSize(); i++) {
				if (shoppingcar.containsKey(Const.cache.getGoodsBeanFromShoppingCartList(i).shopId)) { // 同一家店铺
					for (int n = 0; n < shoppinglist.size(); n++) {
						if (shoppinglist.get(n).shopID.equals(Const.cache
								.getGoodsBeanFromShoppingCartList(i).shopId)) {
							List<GoodsListBean> child;
							child = shoppinglist.get(n).child;
							Const.cache.getGoodsBeanFromShoppingCartList(i)
									.Setchild(false);
							child.add(Const.cache
									.getGoodsBeanFromShoppingCartList(i));
							shoppinglist.get(n).child = child;
						}
					}
				} else { // 不同店铺
					shoppingcar
							.put(Const.cache
									.getGoodsBeanFromShoppingCartList(i).shopId,
									"");
					ShoppingCart newshoppingcart = new ShoppingCart(false);
					Const.cache.getGoodsBeanFromShoppingCartList(i).Setchild(
							false);
					newshoppingcart.child.add(Const.cache
							.getGoodsBeanFromShoppingCartList(i));
					newshoppingcart.shopname = Const.cache
							.getGoodsBeanFromShoppingCartList(i).shopName;
					newshoppingcart.shopID = Const.cache
							.getGoodsBeanFromShoppingCartList(i).shopId;
					newshoppingcart.deliveryFreeMoney = Const.cache
							.getGoodsBeanFromShoppingCartList(i).deliveryFreeMoney;
					shoppinglist.add(newshoppingcart);
				}

			}
		} else {
			ll_balance.setVisibility(View.GONE);
			ll_null_spc.setVisibility(View.VISIBLE);
			el_shoppingcar.setVisibility(View.GONE);
		}
	}

	// 计算勾选的购物车总价格
	public double CountTotalPrice() {
		double totalprice = 0;
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).child.size(); j++) {
					if (shoppinglist.get(i).child.get(j).cbchild) {
						totalprice = totalprice
								+ Double.parseDouble(shoppinglist.get(i).child
										.get(j).shopPrice)
								* shoppinglist.get(i).child.get(j).goodscount;
					}
				}
			}
		}
		return totalprice;
	}
	//计算单个店铺商品总价格
	public void initShopTotalMoney(){
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).child.size(); j++) {
					shoppinglist.get(i).totalPrice = Double
							.parseDouble(shoppinglist.get(i).child.get(j).shopPrice)
							* shoppinglist.get(i).child.get(j).goodscount
							+ shoppinglist.get(i).totalPrice; 
				}
			}
		}
	}
	
	// 计算总价格，不管是否勾选
	public double TotalPrice() {
		double totalprice = 0;
		if (shoppinglist != null) {
			for (int i = 0; i < shoppinglist.size(); i++) {
				for (int j = 0; j < shoppinglist.get(i).child.size(); j++) {
					totalprice = totalprice
							+ Double.parseDouble(shoppinglist.get(i).child
									.get(j).shopPrice)
							* shoppinglist.get(i).child.get(j).goodscount;
				}
			}
		}
		return totalprice;
	}

	// 设置购物车全部是否勾选
	public void isSelectAll(boolean ifAll) {
		for (int i = 0; i < shoppinglist.size(); i++) {
			if (ifAll) {
				shoppinglist.get(i).cbgroup = true;
				for (int j = 0; j < shoppinglist.get(i).child.size(); j++) {
					shoppinglist.get(i).child.get(j).cbchild = true;
				}
			} else {
				shoppinglist.get(i).cbgroup = false;
				for (int j = 0; j < shoppinglist.get(i).child.size(); j++) {
					shoppinglist.get(i).child.get(j).cbchild = false;
				}
			}
		}
	}

	// 选取商品计算总额接口
	@Override
	public void totalPriceChange() {
		// TODO Auto-generated method stub
		tv_total_price.setText("合计：" + CountTotalPrice() + "元");
		if (TotalPrice() == CountTotalPrice()) {
			cb_chioc.setChecked(true);
		} else {
			cb_chioc.setChecked(false);
		}
	}
}
