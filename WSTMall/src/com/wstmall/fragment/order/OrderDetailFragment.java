package com.wstmall.fragment.order;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.widget.HtmlViewActivity;
import com.wstmall.api.order.CancelOrder;
import com.wstmall.api.order.ConfirmOrder;
import com.wstmall.api.order.GetOrderDetail;
import com.wstmall.api.pay.ToPay;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.bean.OrderBean;
import com.wstmall.bean.OrderDetailBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.RemarkDialog;
import com.wstmall.widget.RemarkDialog.SubmitRemark;

@FragmentView(id = R.layout.activity_order_detail)
public class OrderDetailFragment extends BaseFragment implements
		OnClickListener, SubmitRemark {
	@InjectView(id = R.id.lv_order_detail_goods)
	private ListView lv_order_detail_goods;

	private OrderDetailAdapter orderDetailAdapter;
	private OrderBean orderBean;

	private RemarkDialog remarkDialog;

	private OrderDetailFragment orderDetailFragment;
	//
	private CancelOrder canelOrder = new CancelOrder();
	private ConfirmOrder confirmOrder = new ConfirmOrder();
	//
	GetOrderDetail getOrderDetail = new GetOrderDetail();
	//
	private OrderDetailBean orderDetailBean;

	// bot
	@InjectView(id = R.id.tv_order_pay_handle)
	private TextView tv_order_pay_handle;

	@InjectView(id = R.id.tv_order_handle)
	private TextView tv_order_handle;

	@InjectView(id = R.id.tv_order_cancel)
	private TextView tv_order_cancel;

	@InjectView(id = R.id.re_bot)
	private RelativeLayout re_bot;

	protected void requestSuccess(String url, String data) {
		if (url.contains(canelOrder.getA())) {// 取消订单接口

			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合

				String result = jsonreobj.getString("status");
				if (result.equals("1")) {
					Toast.makeText(getActivity(), "取消订单成功", Toast.LENGTH_LONG)
							.show();
					getActivity().finish();
				} else {
					Toast.makeText(getActivity(), "取消订单失败,请稍后重试",
							Toast.LENGTH_LONG).show();
				}
			}

			catch (JSONException e) {
				e.printStackTrace();
			} finally {

			}
		} else if (url.contains(confirmOrder.getA())) { // 拒收接收
			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合

				String result = jsonreobj.getString("status");
				if (result.equals("1")) {
					Toast.makeText(getActivity(), "操作成功", Toast.LENGTH_LONG)
							.show();
					getActivity().finish();
				} else {
					Toast.makeText(getActivity(), "操作失败,请稍后重试",
							Toast.LENGTH_LONG).show();
				}

			}

			catch (JSONException e) {
				e.printStackTrace();
			} finally {

			}
		} else if (url.contains(getOrderDetail.getA())) { // 拒收接收
			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合
				orderDetailBean = new Gson().fromJson(
						jsonreobj.getJSONObject("data").toString(),
						OrderDetailBean.class);

				for (int i = 0; i < jsonreobj.getJSONObject("data")
						.getJSONArray("goods").length(); i++) {
					GoodsListBean goodsListBean = new Gson().fromJson(jsonreobj
							.getJSONObject("data").getJSONArray("goods")
							.getJSONObject(i).toString(), GoodsListBean.class);
					orderDetailBean.orderGoodList.add(goodsListBean);
				}
			}

			catch (JSONException e) {
				e.printStackTrace();
			} finally {
				initView();

			}
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("订单详情");
		orderBean = OrderManagement.orderbeanlist
				.get(OrderManagement.orderDetailPosition);
		initBot();
		getOrderDetail.tokenId = Const.cache.getTokenId();
		getOrderDetail.orderId = orderBean.orderId;
		request(getOrderDetail);
		this.orderDetailFragment = this;
	}

	@Override
	protected void bindEvent() {
		tv_order_pay_handle.setOnClickListener(this);
		tv_order_handle.setOnClickListener(this);
		lv_order_detail_goods.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				intent.putExtra(GoodsActivity.goodsID,
						orderBean.goodlist.get(position - 1).goodsId);
				startActivity(intent);

			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_order_pay_handle:
			if (orderBean.orderStatus == -2) {
				ToPay toPay = new ToPay();
				toPay.orderId = orderBean.orderId;
				toPay.tokenId = Const.cache.getTokenId();
				Intent intent = new Intent(getActivity(),
						HtmlViewActivity.class);
				intent.putExtra("title", "在线支付");
				intent.putExtra("Url", Const.API_BASE_URL + toPay.getA()
						+ toPay.getString());
				startActivity(intent);
			} else if (orderBean.orderStatus == 3) {
				ConfirmOrder confirmOrder = new ConfirmOrder();
				confirmOrder.orderId = orderBean.orderId;
				confirmOrder.tokenId = Const.cache.getTokenId();
				confirmOrder.type = "1";
				request(confirmOrder);
			}
			break;
		case R.id.tv_order_handle:
			canelOrder.tokenId = Const.cache.getTokenId();
			canelOrder.orderId = orderBean.orderId;
			if (orderBean.orderStatus == -2 || orderBean.orderStatus == 0
					|| orderBean.orderStatus == 1 || orderBean.orderStatus == 2) {
				if (orderBean.orderStatus == 1 || orderBean.orderStatus == 2) {
					remarkDialog = new RemarkDialog(getActivity(),
							R.style.MyDialogStyle, "填写取消订单原因");
					remarkDialog.setSureToDoListener(orderDetailFragment);
					remarkDialog.show();
				} else {
					request(canelOrder);
				}
			} else if (orderBean.orderStatus == 3) {
				remarkDialog = new RemarkDialog(getActivity(),
						R.style.MyDialogStyle, "填写拒绝收货原因");
				remarkDialog.setSureToDoListener(orderDetailFragment);
				remarkDialog.show();
			} else if (orderBean.orderStatus == 4 && orderBean.isAppraises == 0) {
				replaceFragment(new EvaluationFragment(
						OrderManagement.orderDetailPosition), false);
			}
			break;
		}
	}

	private double getTotalGoodsMoney() {
		double totalGoodsMoney=0;
		for(int i=0;i<orderBean.goodlist.size();i++){
			totalGoodsMoney=totalGoodsMoney+orderBean.goodlist.get(i).goodsNum*orderBean.goodlist.get(i).goodsPrice;
		}
		return totalGoodsMoney;
	}

	private void initView() {
		// headview
		LinearLayout headView = (LinearLayout) LayoutInflater.from(
				getActivity()).inflate(R.layout.order_detail_head, null);

		TextView tv_order_num = (TextView) headView
				.findViewById(R.id.tv_order_num);
		TextView tv_order_shop_name = (TextView) headView
				.findViewById(R.id.tv_order_shop_name);
		TextView tv_order_good_state = (TextView) headView
				.findViewById(R.id.tv_order_good_state);

		tv_order_num.setText("订单号: " + orderDetailBean.orderNo);

		tv_order_shop_name.setText(orderBean.shopName);

		//
		TextView tv_order_total_price = (TextView) headView
				.findViewById(R.id.tv_order_total_price);
		TextView tv_order_good_total_price = (TextView) headView
				.findViewById(R.id.tv_order_good_total_price);
		TextView tv_order_good_delivery_money = (TextView) headView
				.findViewById(R.id.tv_order_good_delivery_money);

		TextView tv_order_good_bot_count = (TextView) headView
				.findViewById(R.id.tv_order_good_bot_count);

		tv_order_total_price.setText("总计:   " + "￥" + orderBean.totalMoney);
		tv_order_good_total_price.setText("商品: " + "+￥" + getTotalGoodsMoney());
		tv_order_good_delivery_money.setText("运费: " + "+￥" + (orderBean.totalMoney-getTotalGoodsMoney()));

		tv_order_good_bot_count
				.setText("共" + orderBean.goodlist.size() + "件商品");
		switch (orderDetailBean.orderStatus) {
		// -7\-6\-1:用户取消 -5:门店不同意拒收 -4:门店同意拒收 -3:用户拒收 -2:未付款的订单 0:未受理 1:已受理
		// 2:打包中 3:配送中 4:用户确认收货
		case -7:
			tv_order_good_state.setText("用户取消");
			break;
		case -6:
			tv_order_good_state.setText("用户取消");
			break;
		case -1:
			tv_order_good_state.setText("用户取消");
			break;
		case -5:
			tv_order_good_state.setText("不同意拒收");
			break;
		case -4:
			tv_order_good_state.setText("同意拒收");
			break;
		case -3:
			tv_order_good_state.setText("用户拒收");
			break;
		case -2:
			tv_order_good_state.setText("未付款");
			break;
		case 0:
			tv_order_good_state.setText("未受理 ");
			break;
		case 1:
			tv_order_good_state.setText("已受理");
			break;
		case 2:
			tv_order_good_state.setText("打包中");
			break;
		case 3:
			tv_order_good_state.setText("配送中");
			break;
		case 4:
			tv_order_good_state.setText("已确认收货 ");
			break;
		}

		// botView
		LinearLayout botView = (LinearLayout) LayoutInflater
				.from(getActivity()).inflate(R.layout.order_detarl_bot, null);

		TextView tv_isPay = (TextView) botView.findViewById(R.id.tv_isPay);
		TextView tv_payType = (TextView) botView.findViewById(R.id.tv_payType);
		TextView tv_deliverType = (TextView) botView
				.findViewById(R.id.tv_deliverType);
		TextView tv_createTime = (TextView) botView
				.findViewById(R.id.tv_createTime);
		TextView tv_orderRemarks = (TextView) botView
				.findViewById(R.id.tv_orderRemarks);
		TextView tv_isInvoice = (TextView) botView
				.findViewById(R.id.tv_isInvoice);
		TextView tv_invoiceClient = (TextView) botView
				.findViewById(R.id.tv_invoiceClient);
		TextView tv_userAddress = (TextView) botView
				.findViewById(R.id.tv_userAddress);
		TextView tv_requireTime = (TextView) botView
				.findViewById(R.id.tv_requireTime);
		TextView tv_userTel = (TextView) botView.findViewById(R.id.tv_userTel);
		TextView tv_userPhone = (TextView) botView
				.findViewById(R.id.tv_userPhone);
		if (orderDetailBean.payType == 0) {
			tv_payType.setText("货到付款");
		} else {
			tv_payType.setText("在线付款");
		}
		if (orderDetailBean.isPay == 1) {
			tv_isPay.setText("已付款");
		} else {
			tv_isPay.setText("未付款");
		}
		tv_deliverType.setText(orderDetailBean.deliverType + "");
		tv_createTime.setText(orderDetailBean.createTime + "");
		tv_orderRemarks.setText(orderDetailBean.orderRemarks + "");
		if (orderDetailBean.isInvoice == 1) {
			tv_isInvoice.setText("开发票");
		} else {
			tv_isInvoice.setText("不开发票");
		}
		tv_invoiceClient.setText(orderDetailBean.invoiceClient);
		tv_userAddress.setText(orderDetailBean.userAddress);
		tv_requireTime.setText(orderDetailBean.requireTime + "");
		tv_userTel.setText(orderDetailBean.userTel);
		tv_userPhone.setText(orderDetailBean.userPhone);

		// init listview
		lv_order_detail_goods.addHeaderView(headView, null, false);
		lv_order_detail_goods.addFooterView(botView, null, false);
		orderDetailAdapter = new OrderDetailAdapter();
		lv_order_detail_goods.setAdapter(orderDetailAdapter);
	}

	public void initBot() {
		re_bot.getBackground().setAlpha(100);
		if (orderBean.orderStatus == -2 || orderBean.orderStatus == 0
				|| orderBean.orderStatus == 1 || orderBean.orderStatus == 2) {
			tv_order_handle.setVisibility(View.VISIBLE);
			tv_order_handle.setText("取消订单");
			if (orderBean.orderStatus == -2) {
				tv_order_pay_handle.setVisibility(View.VISIBLE);
				tv_order_pay_handle.setText("去支付");
			}
		} else if (orderBean.orderStatus == 3) {
			tv_order_handle.setVisibility(View.VISIBLE);
			tv_order_handle.setText("拒绝收货");
			tv_order_pay_handle.setVisibility(View.VISIBLE);
			tv_order_pay_handle.setText("确定收货");
		} else if (orderBean.orderStatus == -7 || orderBean.orderStatus == -6
				|| orderBean.orderStatus == -1) {
			tv_order_cancel.setVisibility(View.VISIBLE);
			tv_order_cancel.setText("订单已取消");
		} else if (orderBean.orderStatus == 4 && orderBean.isAppraises == 0) {
			tv_order_handle.setVisibility(View.VISIBLE);
			tv_order_handle.setText("去评价");
		} else if (orderBean.orderStatus == 4 && orderBean.isAppraises == 1) {
			tv_order_handle.setVisibility(View.VISIBLE);
			tv_order_handle.setText("已评价");
		}

	}

	@Override
	public void sureTodo() {
		// TODO Auto-generated method stub
		if (remarkDialog.getRemarkContent().equals("")) {
			Toast.makeText(getActivity(), "请填写原因", Toast.LENGTH_LONG).show();
			return;
		}
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("亲,确认忍心提交？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (orderBean.orderStatus == 3) {
					confirmOrder.orderId = orderBean.orderId;
					confirmOrder.tokenId = Const.cache.getTokenId();
					confirmOrder.type = "0";
					confirmOrder.rejectionRemarks = remarkDialog
							.getRemarkContent();
					request(confirmOrder);
				} else if (orderBean.orderStatus == 1
						|| orderBean.orderStatus == 2) {
					canelOrder.rejectionRemarks = remarkDialog
							.getRemarkContent();
					request(canelOrder);
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private class OrderDetailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderBean.goodlist.size();
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
			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.fragment_order_good_item, null);
			ImageView im_order_good_img = (ImageView) convertView
					.findViewById(R.id.im_order_good_img);
			TextView tv_order_good_name = (TextView) convertView
					.findViewById(R.id.tv_order_good_name);
			TextView tv_order_good_price = (TextView) convertView
					.findViewById(R.id.tv_order_good_price);
			TextView tv_order_good_count = (TextView) convertView
					.findViewById(R.id.tv_order_good_count);
			((BaseActivity) getActivity()).loadOnImage(Const.BASE_URL
					+ orderBean.goodlist.get(position).goodsThums,
					im_order_good_img);
			tv_order_good_name
					.setText(orderBean.goodlist.get(position).goodsName);
			tv_order_good_price.setText("￥"
					+ orderBean.goodlist.get(position).goodsPrice + "");
			tv_order_good_count.setText("x"
					+ orderBean.goodlist.get(position).goodsNum + "");
			return convertView;
		}

	}

}
