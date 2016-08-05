package com.wstmall.fragment.goods;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.zhy_9.food_test.R;
import com.wstmall.activity.user.LoginActivity;
import com.wstmall.api.goods.GetGoods;
import com.wstmall.api.goods.GetGoodsDesc;
import com.wstmall.application.Const;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.business.BusinessHomeFragment;
import com.wstmall.fragment.order_goods.OrderForGoodsFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.GoodsAttrsPopWindows;
import com.wstmall.widget.GoodsAttrsPopWindows.AttrsSelectInf;

@SuppressLint("ResourceAsColor")
@FragmentView(id = R.layout.fragment_goods)
public class GoodsFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.scrollview_first)
	private ScrollView scrollview_first;
	@InjectView(id = R.id.htmlView)
	private WebView htmlView;
	@InjectView(id = R.id.goods_picture)
	private ImageView goods_picture;
	@InjectView(id = R.id.goods_name)
	private TextView goods_name;
	@InjectView(id = R.id.goods_price)
	private TextView goods_price;
	@InjectView(id = R.id.goods_stock)
	private TextView goods_stock;
	@InjectView(id = R.id.goods_seller)
	private TextView goods_seller;
	@InjectView(id = R.id.goods_spec_layout)
	private View goods_spec_layout;
	@InjectView(id = R.id.goods_spec)
	private TextView goods_spec;
	@InjectView(id = R.id.goods_appraise_num)
	private TextView goods_appraise_num;
	@InjectView(id = R.id.appraise_touch)
	private View appraise_touch;
	@InjectView(id = R.id.buy_layout)
	private View buy_layout;
	@InjectView(id = R.id.minus_buy_button)
	private TextView minus_buy_button;
	@InjectView(id = R.id.add_buy_button)
	private TextView add_buy_button;
	@InjectView(id = R.id.buy_num_edittext)
	private EditText buy_num_edittext;
	@InjectView(id = R.id.buy_button)
	private TextView buy_button;
	@InjectView(id = R.id.add_cart_button)
	private TextView add_cart_button;
	//
	@InjectView(id = R.id.ll_shop_name)
	private RelativeLayout ll_shop_name;
	@InjectView(id = R.id.re_good_attr)
	private RelativeLayout re_good_attr;
	@InjectView(id = R.id.tv_good_attrs)
	private TextView tv_good_attrs;
	private static int IS_DEFAULT_ATTRS=-1;
	private int currentPosition=IS_DEFAULT_ATTRS;//当前选中的属性位置。
	private JSONObject jsonobj;

	private GetGoods getGoods = new GetGoods();
	private GoodsListBean goodsListBean;
	private GetGoodsDesc getGoodsDesc = new GetGoodsDesc();
	private int numStock;
	private int tempBuyNum = 1;
	private boolean alreadyHtmlLoading = false;
	private boolean lockTouch = false;
	private int upHeight;
	private int downHeight;

	private GoodsAttrsPopWindows goodsAttrsPopWindows;
	private boolean isBalance;

	public GoodsFragment(String goodsId) {
		this.goodsListBean = new GoodsListBean();
		this.goodsListBean.goodsId = goodsId;
	}

	@Override
	public void bindDataForUIElement() {
		// 商品图片变成正方形
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		LayoutParams params = goods_picture.getLayoutParams();
		params.height = dm.widthPixels;

		// 上拔反应有效高度和下拉反应有效高度
		upHeight = dm.heightPixels * 5 / 32;
		downHeight = dm.heightPixels * 25 / 128;

		goods_picture.setLayoutParams(params);
		GetGoods();
		initWebView();
	}

	private void initWebView() {
		WebSettings webSettings = htmlView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);// 支持缩放
		webSettings.setBuiltInZoomControls(true);// 允许缩放控制
		webSettings.setDisplayZoomControls(false); // 不显示缩放按钮
		webSettings.setLoadWithOverviewMode(true); // 当需要从一个网页跳转到另一个网页时,目标网页仍然在当前WebView中显示,而不是打开系统浏览器
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setSupportMultipleWindows(false);// 禁止网页多窗口
		webSettings.setUseWideViewPort(true);// 任意比例缩放
	}

	private void GetGoods() {
		getGoods.id = goodsListBean.goodsId;
		request(getGoods);
	}

	@Override
	protected void bindEvent() {
		appraise_touch.setOnClickListener(this);
		buy_button.setOnClickListener(this);
		add_cart_button.setOnClickListener(this);
		minus_buy_button.setOnClickListener(this);
		add_buy_button.setOnClickListener(this);
		goods_seller.setOnClickListener(this);
		re_good_attr.setOnClickListener(this);
		ll_shop_name.setOnClickListener(this);
		buy_num_edittext.addTextChangedListener(buyNumWatcher);

		scrollview_first.setOnTouchListener(upTouchListener);
		htmlView.setOnTouchListener(downTouchListener);
	}

	private OnTouchListener upTouchListener = new OnTouchListener() {

		private boolean isTouchWhenBottom = false;
		private float yTouchDown;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (lockTouch) {
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				View contentView = scrollview_first.getChildAt(0);

				// 判断scrollview到达底部
				if (contentView.getMeasuredHeight() <= scrollview_first
						.getScrollY() + scrollview_first.getHeight()) {
					isTouchWhenBottom = true;
					yTouchDown = event.getRawY();
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (isTouchWhenBottom
						&& yTouchDown - event.getRawY() >= upHeight) {
					lockTouch = true;
					isTouchWhenBottom = false;
					Animation outAnimation = AnimationUtils.loadAnimation(
							getActivity(), R.anim.slide_out_to_top);
					outAnimation
							.setAnimationListener(new Animation.AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {
									scrollview_first.setVisibility(View.GONE);
									//buy_layout.setVisibility(View.GONE);
									Animation inAnimation = AnimationUtils
											.loadAnimation(getActivity(),
													R.anim.slide_in_from_bottom);
									htmlView.startAnimation(inAnimation);
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									lockTouch = false;
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
								}
							});
					scrollview_first.startAnimation(outAnimation);
					if (!alreadyHtmlLoading) {
						getGoodsDesc.id = goodsListBean.goodsId;
						htmlView.loadUrl(Const.API_BASE_URL
								+ getGoodsDesc.getA()
								+ getGoodsDesc.getString()); 
						Log.e("niqusiba", Const.API_BASE_URL
								+ getGoodsDesc.getA()
								+ getGoodsDesc.getString());
						alreadyHtmlLoading = true;
					}
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				isTouchWhenBottom = false;
			}
			return false;
		}
	};

	private OnTouchListener downTouchListener = new OnTouchListener() {

		private boolean isTouchWhenTop = false;
		private float yTouchDown;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (lockTouch) {
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 判断htmlView到达底部
				if (htmlView.getScrollY() == 0) {
					isTouchWhenTop = true;
					yTouchDown = event.getRawY();
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				if (isTouchWhenTop
						&& event.getRawY() - yTouchDown >= downHeight) {
					lockTouch = true;
					isTouchWhenTop = false;
					Animation outAnimation = AnimationUtils.loadAnimation(
							getActivity(), R.anim.slide_out_to_bottom);
					outAnimation
							.setAnimationListener(new Animation.AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {
									Animation inAnimation = AnimationUtils
											.loadAnimation(getActivity(),
													R.anim.slide_in_from_top);
									scrollview_first
											.startAnimation(inAnimation);
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									scrollview_first
											.setVisibility(View.VISIBLE);
									//buy_layout.setVisibility(View.VISIBLE);
									lockTouch = false;
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {

								}
							});
					htmlView.startAnimation(outAnimation);
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				isTouchWhenTop = false;
			}
			return false;
		}
	};

	private TextWatcher buyNumWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			String tempString = buy_num_edittext.getText().toString();

			// 如果输入框为空
			if (tempString.equals("")) {
				buy_num_edittext.setText("1");
				tempBuyNum = 1;
				return;
			}

			// 如果输入长度或数值超过库存
			if (tempString.length() > String.valueOf(numStock).length()
					|| Integer.parseInt(tempString) > numStock) {
				Toast.makeText(getActivity(), "已超过库存", Toast.LENGTH_SHORT)
						.show();
				tempBuyNum = numStock;
				buy_num_edittext.setText("" + numStock);
				return;
			}

			tempBuyNum = Integer.parseInt(tempString);
		}
	};

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getGoods.getA())) {
			try {
				 jsonobj = new JSONObject(data); 
				goodsListBean = gson.fromJson(jsonobj.get("data").toString(),
						GoodsListBean.class);
				loadOnRectangleImage(Const.BASE_URL + goodsListBean.goodsThums,
						goods_picture);
				goods_name.setText(goodsListBean.goodsName);
				goods_price.setText(goodsListBean.shopPrice + "/"
						+ goodsListBean.goodsUnit);
				setStockText(goodsListBean.goodsStock + "");
				goods_seller.setText(goodsListBean.shopName);
				if (goodsListBean.goodsSpec != null
						&& !goodsListBean.goodsSpec.equals("")) {
					goods_spec.setText(goodsListBean.goodsSpec);
					goods_spec_layout.setVisibility(View.VISIBLE);
				}
				if (!goodsListBean.appraiseNum.equals("0")) {
					goods_appraise_num.setText(goodsListBean.appraiseNum);
					appraise_touch.setVisibility(View.VISIBLE);
				}
				if (goodsListBean.priceAttrs.size() != 0) {
					re_good_attr.setVisibility(View.VISIBLE);
					// 设置默认的品质
					tv_good_attrs.setText(goodsListBean.priceAttrName+":"+getAttrVal()); 
					goodsListBean.priceAttrName=goodsListBean.priceAttrs.get(0).attrName;
					goodsListBean.priceAttrVal=getAttrVal();

				} else {
					re_good_attr.setVisibility(View.GONE);
				}
			} catch (JSONException e) {
			}
		}
	}

	private String getAttrVal() {
		for(int i=0;i<goodsListBean.priceAttrs.size();i++){
			if(goodsListBean.priceAttrs.get(i).id.equals(goodsListBean.goodsAttrId)){
				return goodsListBean.priceAttrs.get(i).attrVal;
			}
		}
		return null;
	}

	private void setStockText(String stockString) {
		if (stockString.equals("0")) {
			goods_stock.setText("无货");
			goods_stock.setTextColor(R.color.red);
			numStock = 0;
		} else {
			goods_stock.setText("有货");
			goods_stock.setTextColor(R.color.gray_deep_other);
			numStock = Integer.parseInt(stockString);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.appraise_touch:
			replaceFragment(new GoodsAppraiseFragment(goodsListBean.goodsId),
					true);
			break;
		case R.id.buy_button:
			if (Const.isLogin) {
				if(currentPosition!=IS_DEFAULT_ATTRS){
				GoodsListBean glb=new GoodsListBean();
				try {
					glb = gson.fromJson(jsonobj.get("data").toString(),
							GoodsListBean.class);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				glb.goodsAttrId=goodsListBean.priceAttrs.get(currentPosition).id;
				glb.shopPrice=goodsListBean.priceAttrs.get(currentPosition).attrPrice;
				glb.priceAttrName=goodsListBean.priceAttrs.get(currentPosition).attrName;
				glb.priceAttrVal=goodsListBean.priceAttrs.get(currentPosition).attrVal;
				for (int i = 0; i < tempBuyNum; i++) {
					Const.cache.addShoppingCartList(glb);
				}

				replaceFragment(
						new OrderForGoodsFragment(1, glb.goodsId
								+ "_" + glb.goodsAttrId + "_"
								+ tempBuyNum, tempBuyNum,
								Double.parseDouble(glb.shopPrice)
										* tempBuyNum), true);
				}else{
					for (int i = 0; i < tempBuyNum; i++) {
						Const.cache.addShoppingCartList(goodsListBean);
					}

					replaceFragment(
							new OrderForGoodsFragment(1, goodsListBean.goodsId
									+ "_" + goodsListBean.goodsAttrId + "_"
									+ tempBuyNum, tempBuyNum,
									Double.parseDouble(goodsListBean.shopPrice)
											* tempBuyNum), true);
				}
			} else {
				isBalance = true;
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.add_cart_button:
			if(currentPosition!=IS_DEFAULT_ATTRS){
				GoodsListBean glb=new GoodsListBean();
				try {
					glb = gson.fromJson(jsonobj.get("data").toString(),
							GoodsListBean.class);
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				glb.goodsAttrId=goodsListBean.priceAttrs.get(currentPosition).id;
				glb.shopPrice=goodsListBean.priceAttrs.get(currentPosition).attrPrice;
				glb.priceAttrName=goodsListBean.priceAttrs.get(currentPosition).attrName;
				glb.priceAttrVal=goodsListBean.priceAttrs.get(currentPosition).attrVal;
				Const.cache.addShoppingCartListWithNum(glb, tempBuyNum);
			}else{
				Const.cache.addShoppingCartListWithNum(goodsListBean, tempBuyNum);
			}
			Toast.makeText(getActivity(), "商品已成功加入购物车", Toast.LENGTH_LONG)
					.show();
			break;
		case R.id.minus_buy_button:
			if (tempBuyNum > 1) {
				tempBuyNum--;
				buy_num_edittext.setText(tempBuyNum + "");
			}
			break;
		case R.id.add_buy_button:
			if (tempBuyNum < numStock) {
				tempBuyNum++;
				buy_num_edittext.setText(tempBuyNum + "");
			}
			break;
		case R.id.goods_seller:
			replaceFragment(new BusinessHomeFragment(goodsListBean.shopId,
					goodsListBean.shopName), true);
			break;
		case R.id.ll_shop_name:
			replaceFragment(new BusinessHomeFragment(goodsListBean.shopId,
					goodsListBean.shopName), true);
			break;
		case R.id.re_good_attr:
			goodsAttrsPopWindows = new GoodsAttrsPopWindows(getActivity(),
					goodsListBean);
			goodsAttrsPopWindows.setAttrsSelectListener(new AttrsSelectInf() {

				@Override
				public void AttrsSelect(int position) {
					refreshAttrs(position);
					goodsAttrsPopWindows.dismiss();
				}
			});
			goodsAttrsPopWindows.showAtLocation(re_good_attr, Gravity.BOTTOM,
					0, 0);
			break;
		}
	}

	private void refreshAttrs(int position) {
		tv_good_attrs.setText(goodsListBean.priceAttrName+":"+goodsListBean.priceAttrs.get(position).attrVal);
		goods_price.setText(goodsListBean.priceAttrs.get(position).attrPrice
				+ "/" + goodsListBean.goodsUnit);
		setStockText(goodsListBean.priceAttrs.get(position).attrStock + "");
		currentPosition=position;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Const.isLogin && isBalance) {
			for (int i = 0; i < tempBuyNum; i++) {
				Const.cache.addShoppingCartList(goodsListBean);
			}
			isBalance = false;
			replaceFragment(new OrderForGoodsFragment(1, goodsListBean.goodsId
					+ "_" + goodsListBean.goodsAttrId + "_" + tempBuyNum,
					tempBuyNum, Double.parseDouble(goodsListBean.shopPrice)
							* tempBuyNum), true);
		}
	}

}
