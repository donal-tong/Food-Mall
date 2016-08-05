package com.wstmall.fragment.order_goods;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy_9.food_test.R;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsForOrder;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.time.select.NumericWheelAdapter;
import com.wstmall.widget.time.select.OnWheelScrollListener;
import com.wstmall.widget.time.select.WheelView;

@FragmentView(id = R.layout.add_order)
public class AddOrderFragment extends BaseFragment implements
		View.OnClickListener {
	
	@InjectView(id = R.id.re_seletc_pay_type)
	private RelativeLayout re_seletc_pay_type;
	
	@InjectView(id = R.id.tv_pay_type)
	private TextView tv_pay_type;

	@InjectView(id = R.id.tv_self_get)
	private TextView tv_self_get;
	@InjectView(id = R.id.tv_no_self_get)
	private TextView tv_no_self_get;

	@InjectView(id = R.id.tv_isInvoice)
	private TextView tv_isInvoice;
	@InjectView(id = R.id.tv_notInvoice)
	private TextView tv_notInvoice;

	@InjectView(id = R.id.et_invoiceClient)
	private EditText et_invoiceClient;
	@InjectView(id = R.id.et_remarks)
	private EditText et_remarks;

	@InjectView(id = R.id.ll_canInvoice)
	private LinearLayout ll_canInvoice;
	@InjectView(id = R.id.ll_notCanInvoice)
	private LinearLayout ll_notCanInvoice;

	@InjectView(id = R.id.ll_invoice_item)
	private LinearLayout ll_invoice_item;

	@InjectView(id = R.id.ll_not_invoice_item)
	private LinearLayout ll_not_invoice_item;
	@InjectView(id = R.id.ll_invioce_info)
	private LinearLayout ll_invioce_info;
	//时间选择器
	
	@InjectView(id = R.id.tv_data)
	private TextView tv_data;
	@InjectView(id = R.id.tv_time)
	private TextView tv_time;
	
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView hour;
	private WheelView mins;
	PopupWindow menuWindow;
	
	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("支付，配送，发票");
		tWidget.setRightBtnText("保存");
		inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		initIsInvoice();
		initSelectInfo();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		re_seletc_pay_type.setOnClickListener(this);
		tv_self_get.setOnClickListener(this);
		tv_no_self_get.setOnClickListener(this);
		tv_notInvoice.setOnClickListener(this);
		tv_isInvoice.setOnClickListener(this);
		tv_data.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopwindow(getDataPick());//弹出日期选择器
			}
		});
		tv_time.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopwindow(getTimePick());//弹出时间选择器
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.re_seletc_pay_type:
				replaceFragment(new SelectPayTypeFragment(), true);
			break;
		case R.id.tv_self_get:
			resetTextBackground(2);
			tv_self_get.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_self_get.setTextColor(Color.parseColor("#F6772A"));

			OrderForGoodsFragment.addorderIsInvoice.isSelf = "1";
			break;
		case R.id.tv_no_self_get:
			resetTextBackground(2);
			tv_no_self_get.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_no_self_get.setTextColor(Color.parseColor("#F6772A"));

			OrderForGoodsFragment.addorderIsInvoice.isSelf = "0";
			break;
		case R.id.tv_isInvoice:
			resetTextBackground(3);
			tv_isInvoice.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_isInvoice.setTextColor(Color.parseColor("#F6772A"));

			OrderForGoodsFragment.addorderIsInvoice.isInvoice = "1";
			ll_invioce_info.setVisibility(View.VISIBLE);
			break;
		case R.id.tv_notInvoice:
			resetTextBackground(3);
			tv_notInvoice.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_notInvoice.setTextColor(Color.parseColor("#F6772A"));

			OrderForGoodsFragment.addorderIsInvoice.isInvoice = "0";
			ll_invioce_info.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * 初始化选择界面
	 */
	public void initSelectInfo() {
		if(OrderForGoodsFragment.payType.size()!=0){
			tv_pay_type.setText(OrderForGoodsFragment.payType.get(OrderForGoodsFragment.PAY_POSITION).payName);
		}

		if (OrderForGoodsFragment.addorderIsInvoice.isSelf.equals("1")) {
			tv_self_get.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_self_get.setTextColor(Color.parseColor("#F6772A"));

		} else {
			tv_no_self_get.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_no_self_get.setTextColor(Color.parseColor("#F6772A"));

		}
		if (OrderForGoodsFragment.addorderIsInvoice.isInvoice.equals("1")) {
			tv_isInvoice.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_isInvoice.setTextColor(Color.parseColor("#F6772A"));

		} else {
			tv_notInvoice.setBackgroundResource(R.drawable.pay_delivery_back_yellow);
			tv_notInvoice.setTextColor(Color.parseColor("#F6772A"));
		}
		et_invoiceClient.setText(OrderForGoodsFragment.addorderIsInvoice.invoiceClient);
		et_remarks.setText(OrderForGoodsFragment.addorderIsInvoice.remarks);
		tv_data.setText(OrderForGoodsFragment.requireData);
		tv_time.setText(OrderForGoodsFragment.requireTime);
	}

	public void rightClick() {
		OrderForGoodsFragment.addorderIsInvoice.invoiceClient = et_invoiceClient
				.getText().toString();
		OrderForGoodsFragment.addorderIsInvoice.remarks = et_remarks.getText()
				.toString();
		OrderForGoodsFragment.addorderIsInvoice.requireTime=OrderForGoodsFragment.requireData+" "+OrderForGoodsFragment.requireTime;
		getActivity().getFragmentManager().popBackStack();
	}

	public void resetTextBackground(int whice) {
		switch (whice) {
		case 2:
			tv_self_get
					.setBackgroundResource(R.drawable.pay_delivery_boder_yellow);
			tv_self_get.setTextColor(Color.parseColor("#252525"));
			tv_no_self_get
					.setBackgroundResource(R.drawable.pay_delivery_boder_yellow);
			tv_no_self_get.setTextColor(Color.parseColor("#252525"));
			break;
		case 3:
			tv_isInvoice
					.setBackgroundResource(R.drawable.pay_delivery_boder_yellow);
			tv_isInvoice.setTextColor(Color.parseColor("#252525"));
			tv_notInvoice
					.setBackgroundResource(R.drawable.pay_delivery_boder_yellow);
			tv_notInvoice.setTextColor(Color.parseColor("#252525"));
			break;
		}
	}

	/**
	 * 列出开发票和不开发票的商品
	 */
	public void initIsInvoice() {
		for (int i = 0; i < OrderForGoodsFragment.goodsfororder.size(); i++) {
			if (OrderForGoodsFragment.goodsfororder.get(i).isInvoice == 0) {
				for (int j = 0; j < OrderForGoodsFragment.goodsfororder.get(i).goodslist
						.size(); j++) {
					ll_not_invoice_item.setVisibility(View.VISIBLE);
					ImageView im = new ImageView(getActivity());
					loadOnImage(
							Const.BASE_URL
									+ OrderForGoodsFragment.goodsfororder
											.get(i).goodslist.get(j).goodsThums,
							im);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							new ViewGroup.LayoutParams(80, 80));
					layoutParams.leftMargin = 8;
					layoutParams.rightMargin = 8;
					ll_notCanInvoice.addView(im, layoutParams);
				}
			} else {
				for (int j = 0; j < OrderForGoodsFragment.goodsfororder.get(i).goodslist
						.size(); j++) {
					ll_invoice_item.setVisibility(View.VISIBLE);
					ImageView im = new ImageView(getActivity());
					loadOnImage(
							Const.BASE_URL
									+ OrderForGoodsFragment.goodsfororder
											.get(i).goodslist.get(j).goodsThums,
							im);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
							new ViewGroup.LayoutParams(80, 80));
					layoutParams.leftMargin = 8;
					layoutParams.rightMargin = 8;
					ll_canInvoice.addView(im, layoutParams);
				}
			}
		}
	}
	
	/**
	 * 时间选择器
	 */

	/**
	 * 初始化popupWindow
	 * @param view
	 */
	private void showPopwindow(View view) {
		menuWindow = new PopupWindow(view,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		menuWindow.setFocusable(true);
	//	menuWindow.setBackgroundDrawable(new BitmapDrawable());
		menuWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		menuWindow.setAnimationStyle(R.style.time_popwindow_anim_style);
		menuWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				menuWindow=null;
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	private View getTimePick() {
		View view = inflater.inflate(R.layout.timepick, null);
		hour = (WheelView) view.findViewById(R.id.hour);
		hour.setAdapter(new NumericWheelAdapter(0, 23));
		hour.setLabel("时");
		hour.setCyclic(true);
		mins = (WheelView) view.findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59));
		mins.setLabel("分");
		mins.setCyclic(true);
		
		hour.setCurrentItem(8);
		mins.setCurrentItem(30);
		
		Button bt = (Button) view.findViewById(R.id.set);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = hour.getCurrentItem() + ":"+ mins.getCurrentItem();
			//	Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
				//time
				OrderForGoodsFragment.requireTime=str;
				tv_time.setText(str);
				menuWindow.dismiss();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
			}
		});
		
		return view;
	}

	/**
	 * 
	 * @return
	 */
	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		final View view = inflater.inflate(R.layout.datapick, null);
		
		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(2008, curYear));
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);
		
		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 12));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);
		
		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		year.setCurrentItem(curYear - 2008);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);
		
		Button bt = (Button) view.findViewById(R.id.set);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = (year.getCurrentItem()+2008) + "-"+ (month.getCurrentItem()+1)+"-"+(day.getCurrentItem()+1);
			//	Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
				//data
				OrderForGoodsFragment.requireData=str;
				tv_data.setText(str);
				menuWindow.dismiss();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
			}
		});
		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			// TODO Auto-generated method stub
			int n_year = year.getCurrentItem() + 1950;// 楠烇拷
			int n_month = month.getCurrentItem() + 1;// 閺堬拷
			initDay(n_year,n_month);
		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}
}
