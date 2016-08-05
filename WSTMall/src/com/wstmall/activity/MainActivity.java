package com.wstmall.activity;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.testin.agent.TestinAgent;
import com.zhy_9.food_test.R;
import com.wstmall.activity.mainPage.MainPageActivity;
import com.wstmall.activity.shoppingCart.ShoppingCartActivity;
import com.wstmall.activity.sort.SortActivity;
import com.wstmall.activity.user.MineActivity;
import com.wstmall.api.GetCitys;
import com.wstmall.api.login.GetUserInfo;
import com.wstmall.application.Const;
import com.wstmall.application.MyPref;
import com.wstmall.bean.AbstractParam;
import com.wstmall.bean.City;
import com.wstmall.bean.Point;
import com.wstmall.bean.User;
import com.wstmall.fragment.mainPage.MainPageFragment;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
import com.wstmall.widget.FindMe;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 

/**
 * 购物车特效调用语句 MainActivity.mainActivity.addShopCart(v);
 */

public class MainActivity extends TabActivity implements TabHost.OnTabChangeListener {

	public static TabHost mHost;
	public static MainActivity mainActivity;
	private View tabPrevious;
	protected Gson gson;

	private int[] end_location = new int[2];// 用来存储动画结束位置的X、Y坐标
	private ViewGroup anim_mask_layout;// 动画层
	private ImageView buyImg;// 这是在界面上跑的小图片
	
	private TextView buyNum;
	private GetCitys getCitys = new GetCitys();
	private GetUserInfo getUserInfo = new GetUserInfo();

	private City tempCity;
	private City tempCity2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bottom_tabs);
		mHost = (TabHost) findViewById(android.R.id.tabhost);
		MyPref.getInstance(this);
		mainActivity = this;
		gson = new Gson();
		initTestin();
		setupIntent();
		initLocation();
		GetUserInfo();
		initEnd_location();
	}
	
	private void initTestin(){
		TestinAgent.init(this, "8a3af5eed5642ed78b8317e5d9a23763",getResources().getString(R.string.app_name));
	}

	private void GetUserInfo() {
		if (Const.cache.getTokenId() != null) {
			getUserInfo.tokenId = Const.cache.getTokenId();
			requestNoDialog(getUserInfo);
		}
	}

	private void initLocation() {
		new FindMe(this, new FindMe.FindMeCallback() {

			@Override
			public void afterFindMe(Point point, City city,City city2) {
				Const.cache.point = point;
				// 城市有变化
				if (!city2.getCityname().equals(Const.cache.city2.getCityname())) {
					tempCity = city;
					getCitys.key = city.getCityname();
					getCitys.key2=city2.getCityname();
					requestNoDialog(getCitys);
				}
			}
		});
	}

	// tab由左数起 0 1 2 3 4
	private void setupIntent() {

		mHost.addTab(createTab("0", R.drawable.tab_home_page, R.drawable.tab_home_page_touch, new Intent(this,
				MainPageActivity.class)));
		mHost.addTab(createTab("1", R.drawable.tab_sort, R.drawable.tab_sort_touch,
				new Intent(this, SortActivity.class)));
		mHost.addTab(createTab("2", R.drawable.tab_shopping_cart, R.drawable.tab_shopping_cart_touch, new Intent(this,
				ShoppingCartActivity.class)));
		mHost.addTab(createTab("3", R.drawable.tab_mine, R.drawable.tab_mine_touch,
				new Intent(this, MineActivity.class)));

		tabPrevious = mHost.getTabWidget().getChildTabViewAt(0);
		mHost.setCurrentTab(3);
		mHost.setOnTabChangedListener(this);
		mHost.setCurrentTab(0);
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        if (Const.cache.getShoppingCartSum()!= 0) {
    		buyNum.setText(Const.cache.getShoppingCartSum() + "");
    		buyNum.setVisibility(View.VISIBLE);
    	}
    }

	private TabHost.TabSpec createTab(String tag, Integer resId, Integer resIdOnclick, Intent content) {
		View view = View.inflate(this, R.layout.bottom_tabs_item, null);
		ImageView tabImg = (ImageView) view.findViewById(R.id.bottom_tabs_item_img);
		tabImg.setImageResource(resId);
		ImageView tabImgClick = (ImageView) view.findViewById(R.id.bottom_tabs_item_img_onclick);
		tabImgClick.setImageResource(resIdOnclick);
		if (tag.equals("2")) {
			buyNum = (TextView) view.findViewById(R.id.bottom_tabs_item_text);
		}
		return mHost.newTabSpec(tag).setIndicator(view).setContent(content);
	}

	@Override
	public void onTabChanged(String tabId) {

		View view = tabPrevious;
		view.findViewById(R.id.bottom_tabs_item_img_onclick).setVisibility(View.INVISIBLE);
		view.findViewById(R.id.bottom_tabs_item_img).setVisibility(View.VISIBLE);

		view = mHost.getCurrentTabView();
		view.findViewById(R.id.bottom_tabs_item_img).setVisibility(View.INVISIBLE);
		view.findViewById(R.id.bottom_tabs_item_img_onclick).setVisibility(View.VISIBLE);

		tabPrevious = view;
	}

	private void initEnd_location() {

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		end_location[0] = dm.widthPixels / 5 * 3;
		end_location[1] = dm.heightPixels - getResources().getDimensionPixelSize(R.dimen.button_height);
	}

	public void addShopCart(View v) {
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		v.getLocationInWindow(start_location);// 这是获取购买按钮的启始的屏幕的X、Y坐标
		start_location[0] += v.getWidth() / 2;
		start_location[1] += v.getHeight() / 2;

		buyImg = new ImageView(this);// buyImg是动画的图片
		buyImg.setImageResource(R.drawable.point_add_shopping);// 设置buyImg的图片
		setAnim(buyImg, start_location);// 开始执行动画
	}

	public void refreshBuyNum() {
		buyNum.setText(Const.cache.getShoppingCartSum()+"");
		if(Const.cache.getShoppingCartSum()==0){
			buyNum.setVisibility(View.INVISIBLE);
		}else{
			buyNum.setVisibility(View.VISIBLE);
		}
	}

	private void setAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);// 把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);

		// 计算位移
		int endX = end_location[0] - start_location[0];// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(400);// 动画的执行时间
		view.startAnimation(set);
		// 动画监听事件
		set.setAnimationListener(new AnimationListener() {
			// 动画的开始
			@Override
			public void onAnimationStart(Animation animation) {
				v.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);

				if (buyNum.getText().toString().equals("0")) {
					buyNum.setText("1");
					buyNum.setVisibility(View.VISIBLE);
				} else {
					buyNum.setText(((Integer) (Integer.parseInt(buyNum.getText().toString()) + 1)).toString());
				}
			}
		});

	}

	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private AbstractParam param;

	public void requestNoDialog(final AbstractParam ap) {
		Class clazz = ap.getClass();
		param = ap;
		RequestType type = (RequestType) clazz.getAnnotation(RequestType.class);
		if (type != null) {
			final String url = Const.API_BASE_URL + param.getA();
			RequestParams p = ap.getRequestParam();

			AsyncHttpClient c = new AsyncHttpClient();
			if (type.type().equals(HttpMethod.GET)) {
				c.get(url + ap.getString(), textHttpResponseHandler);
				Log.i("get信息", url + ap.getString());
			} else {
				c.post(url, p, textHttpResponseHandler);
				Log.i("post信息", url + ap.getString());
			}
		}
	}

	TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseBody) {
			System.out.println("responseBody : " + responseBody);
			try {
				if (responseBody.startsWith("\ufeff")) {
					responseBody = responseBody.substring(1);
				}
				if (responseBody.indexOf("{") > -1) {
					responseBody = responseBody.substring(responseBody.indexOf("{"));
					JSONObject response = new JSONObject(responseBody);
					if (response.getInt("status") == -1000) {
						Toast.makeText(MainActivity.this, "用户令牌已过期，请重新登录", Toast.LENGTH_SHORT).show();
						BaseActivity.reLogin();
						return;
					}else if (response.getInt("status") == 1) {
						requestSuccess(param.getA(), response.toString());
					} else {
						Toast.makeText(MainActivity.this, response.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "请求出错，请重试！", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
		}
	};

	protected void requestSuccess(String url, String data) {
		if (url.contains(getCitys.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				jsonobj = new JSONObject(jsonobj.getJSONObject("data").toString());
				tempCity.setCityid(jsonobj.getString("areaId"));
				tempCity2.setCityid(jsonobj.getString("areaId2"));
				new ChangeCityDialog(this).show();
			} catch (JSONException e) {
			}
		} else if (url.contains(getUserInfo.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
				MineActivity.autoToMine = true;
				Const.isLogin = true;
			} catch (JSONException e) {
			}
		}
	}

	class ChangeCityDialog extends Dialog implements View.OnClickListener {

		public ChangeCityDialog(Context context) {
			super(context, R.style.Translucent_NoTitle);
			setContentView(R.layout.dialog_change_city);

			((TextView) findViewById(R.id.change_city_title)).setText("您当前选择的位置为" + Const.cache.city.getCityname()+Const.cache.city2.getCityname()
					+ "，是否切换至" + tempCity.getCityname()+tempCity2.getCityname() + "？");
			findViewById(R.id.button_no).setOnClickListener(this);
			findViewById(R.id.button_yes).setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_no:
				dismiss();
				break;
			case R.id.button_yes:
				Const.cache.city = tempCity;
				Const.cache.city2 = tempCity2;
				// 如果当前显示的是首页，刷新首页
				if (MainPageFragment.mainPageFragment.isVisible()) {
					//MainPageFragment.mainPageFragment.GetAds();
					MainPageFragment.mainPageFragment.refreshOperation();
				}
				dismiss();
				break;
			}
		}

	}

}
