package com.wstmall.fragment.order;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.api.goods.AddAppraises;
import com.wstmall.application.Const;
import com.wstmall.bean.OrderBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.EditTextBar;

@FragmentView(id = R.layout.fragment_order_evaluation)
public class EvaluationFragment extends BaseFragment {
	private int shopPosition;
	@InjectView(id = R.id.lv_order_eva)
	private ListView order_eva;

	private OrderBean orderBean;

	public EvaluationFragment(int shopPosition) {
		this.shopPosition = shopPosition;
	}

	private List<AddAppraises> addAppraises;

	private int tipsTime = 0;

	protected void requestSuccess(String url, String data) {
		if (tipsTime == 0) {
			if (url.contains(addAppraises.get(0).getA())) {
				tipsTime = 1;
				try {
					JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合
					if (jsonreobj.getString("status").equals("1")) {
						Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_LONG)
								.show();
						getActivity().finish();
					} else {
						Toast.makeText(getActivity(), "提交失败，请稍后重试",
								Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("商品评价");
		tWidget.setRightBtnText("提交");
		addAppraises = new ArrayList<AddAppraises>();
		orderBean = OrderManagement.orderbeanlist.get(shopPosition);
		order_eva.setAdapter(new AppriseAdapter());
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub

		/*
		 * bt_submit_apprise.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub if (addAppraises.content.length() < 10) {
		 * Toast.makeText(getActivity(), "商品评价不能小于10个字",
		 * Toast.LENGTH_LONG).show(); } else if (addAppraises.goodsScore == 0 ||
		 * addAppraises.timeScore == 0 || addAppraises.serviceScore == 0) {
		 * Toast.makeText(getActivity(), "请选择完整的评分", Toast.LENGTH_LONG) .show();
		 * } else { request(addAppraises); } } });
		 */
	}

	@Override
	public void rightClick() {
		for (int i = 0; i < addAppraises.size(); i++) {
			request(addAppraises.get(i));
		}
	}

	public void initAddAppraises() {

	}

	public class AppriseAdapter extends BaseAdapter {
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
					R.layout.order_evaluation_item, null);
			AddAppraises aa = new AddAppraises();
			addAppraises.add(aa);
			ImageView im_order_good_img = (ImageView) convertView
					.findViewById(R.id.im_order_good_img);
			TextView tv_order_good_name = (TextView) convertView
					.findViewById(R.id.tv_order_good_name);
			TextView tv_order_good_price = (TextView) convertView
					.findViewById(R.id.tv_order_good_price);
			TextView tv_order_good_count = (TextView) convertView
					.findViewById(R.id.tv_order_good_count);
			RatingBar rb_goodsScore = (RatingBar) convertView
					.findViewById(R.id.rb_goodsScore);
			RatingBar rb_timeScore = (RatingBar) convertView
					.findViewById(R.id.rb_timeScore);
			RatingBar rb_serviceScore = (RatingBar) convertView
					.findViewById(R.id.rb_serviceScore);
			EditText et_content = (EditText) convertView
					.findViewById(R.id.et_content);
			addAppraises.get(position).tokenId = Const.cache.getTokenId();
			addAppraises.get(position).orderId = orderBean.orderId;
			addAppraises.get(position).goodsId = orderBean.goodlist
					.get(position).goodsId;

			((BaseActivity) getActivity()).loadOnImage(Const.BASE_URL
					+ orderBean.goodlist.get(position).goodsThums,
					im_order_good_img);
			tv_order_good_name
					.setText(orderBean.goodlist.get(position).goodsName);
			tv_order_good_price.setText("￥"
					+ orderBean.goodlist.get(position).goodsPrice + "");
			tv_order_good_count.setText("x"
					+ orderBean.goodlist.get(position).goodsNum + "");

			rb_goodsScore.setOnRatingBarChangeListener(new GSore(position));
			rb_timeScore.setOnRatingBarChangeListener(new TSore(position));
			rb_serviceScore.setOnRatingBarChangeListener(new SSore(position));

			et_content.addTextChangedListener(new CTextWatcher(position));

			return convertView;
		}

		private class CTextWatcher implements TextWatcher {
			private int position;

			public CTextWatcher(int position) {
				this.position = position;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				addAppraises.get(position).content = s.toString();
			}
		}

		private class GSore implements OnRatingBarChangeListener {
			private int position;

			public GSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).goodsScore = (int) rating;
			}
		}

		private class TSore implements OnRatingBarChangeListener {
			private int position;

			public TSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).timeScore = (int) rating;
			}
		}

		private class SSore implements OnRatingBarChangeListener {
			private int position;

			public SSore(int position) {
				this.position = position;
			}

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				addAppraises.get(position).serviceScore = (int) rating;
			}
		}
	}

}
