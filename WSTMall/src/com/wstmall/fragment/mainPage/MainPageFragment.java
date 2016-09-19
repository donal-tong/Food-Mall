package com.wstmall.fragment.mainPage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.brands.BrandsActivity;
import com.wstmall.activity.goodlist.GoodListActivity;
import com.wstmall.activity.nearbybusiness.NearbyBusinessActivity;
import com.wstmall.activity.nearbybusiness.ToSelfSupermarketActivity;
import com.wstmall.activity.order.OrderActivity;
import com.wstmall.activity.user.LoginActivity;
import com.wstmall.adapter.AdAdapter;
import com.wstmall.api.advertisement.GetAds;
import com.wstmall.api.recommendgoods.GetGoodsCatAndGoods;
import com.wstmall.application.Const;
import com.wstmall.application.SortFiled;
import com.wstmall.application.WSTMallApplication;
import com.wstmall.bean.Advertisement;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.bean.RecommendGoodsBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.goods.GoodsListFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.HorizontalScrollViewAdapter;
import com.wstmall.widget.MyHorizontalScrollView;
import com.wstmall.widget.ObservableScrollView;
import com.wstmall.widget.ObservableScrollView.Callbacks;


@FragmentView(id = R.layout.fragment_mainpage)
public class MainPageFragment extends BaseFragment implements
		View.OnClickListener {
	private static final String TAG = "MainPageFragment";
	public static MainPageFragment mainPageFragment;
	private ImageView[] tips, mImageViews;
	private GetAds getads;
	private GetGoodsCatAndGoods getgoodcatandgoods = new GetGoodsCatAndGoods();
//	private List<Advertisement> advertisementlist;
//	private List<RecommendGoodsBean> recommendgoodlist;// 分类合集
	private AdAdapter adadapter;
	private boolean isEnterOrderLoading = false;

	// 广告
	private View adView;
	private ViewPager adViewpager;
	private ViewGroup tipGroup;

	// 导航菜单
	/**
	 * 首页8格功能
	 * 
	 */
	private View menuView;
	private ImageButton nearby_businesses;
	private ImageButton brand_hall;
	private ImageButton self_supermarket;
	private ImageButton my_order;

	//
	@InjectView(id = R.id.lv_main_pager)
	private LinearLayout lv_main_pager;

	@InjectView(id = R.id.pl_scrollVeiw)
	// private PullToRefreshScrollView k;
	private ObservableScrollView pl_scrollVeiw;
	@InjectView(id = R.id.swipeRefreshLayout)
	private SwipeRefreshLayout swipeRefreshLayout;
	private boolean isSwrfRefresh;
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getads.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					Const.cache.removeAdvList();
					for (int i = 0; i < jsonArray.length(); i++) {
						Advertisement adean = new Gson().fromJson(jsonArray
								.getJSONObject(i).toString(),
								Advertisement.class);
						Const.cache.addAdvList(adean);
					}
				}
			} catch (JSONException e) {

			} finally {
				lv_main_pager.removeAllViews();
				initAdvertisement(); 
				lv_main_pager.addView(menuView);
				getgoodcatandgoods.areaId2 = Const.cache.city.getCityid();
				Log.i(TAG, "城市ID" + Const.cache.city.getCityid());
				requestNoDialog(getgoodcatandgoods);
			}
		} else if (url.contains(getgoodcatandgoods.getA())) {
			try {
				JSONObject jsonreobj = new JSONObject(data);// 获取分类对象集合

				if (jsonreobj.has("data")) {
					JSONArray jsonreArray = jsonreobj.getJSONArray("data");// 包含集合-集合
					Const.cache.removeRgList();
					for (int n = 0; n < jsonreArray.length(); n++) {

						RecommendGoodsBean recommendgoodbean = new RecommendGoodsBean();
						recommendgoodbean.catId = jsonreArray.getJSONObject(n)
								.getString("catId");
						recommendgoodbean.catName = jsonreArray
								.getJSONObject(n).getString("catName");

						if (jsonreArray.getJSONObject(n).has("data")) {
							JSONArray jsonarraydata = jsonreArray
									.getJSONObject(n).getJSONArray("data");
							List<GoodsListBean> reconmmendlist = new ArrayList<GoodsListBean>();
							for (int i = 0; i < jsonarraydata.length(); i++) {
								GoodsListBean goodslistbean = gson.fromJson(
										jsonarraydata.getJSONObject(i)
												.toString(),
										GoodsListBean.class);
								reconmmendlist.add(goodslistbean);
							}
							recommendgoodbean.goodlistbean = reconmmendlist;
						}
						Const.cache.addRgList(recommendgoodbean);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				((WSTMallApplication) getActivity().getApplication()).saveCache();
				initReGood();
				swipeRefreshLayout.post(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
						isSwrfRefresh = false;
					}
				});
				tWidget.setChageAlpha();
			}
		}
	}

	public void GetAds() {
		getads = new GetAds();
		getads.areaId2 = Const.cache.city.getCityid();
		requestNoDialog(getads);
	}

	public void GetAdsNoDialog() {
		getads = new GetAds();
		getads.areaId2 = Const.cache.city.getCityid();
		requestNoDialog(getads);
	}

	@Override
	public void bindDataForUIElement() {
		mainPageFragment = this;
		tWidget.changeMode(tWidget.Location_Search_Zbar_Mode);
		initAdvertisement();
		initMeun();
		lv_main_pager.addView(menuView);
		initReGood();
		GetAds();
		swipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				swipeRefreshLayout.setRefreshing(true);
				setCancelRefresh();
				isSwrfRefresh = true;
			}
		});
		listener.onRefresh();
		initHight();
	}

	OnRefreshListener listener = new OnRefreshListener() {
		public void onRefresh() {
		}
	};

	/**
	 * 10秒如果没数据取消刷新状态
	 */
	private void setCancelRefresh() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (isSwrfRefresh) {
					swipeRefreshLayout.post(new Runnable() {

						@Override
						public void run() {
							swipeRefreshLayout.setRefreshing(false);
							isSwrfRefresh = false;
						}
					});
				}
			}
		}, 7000);
	}

	@Override
	protected void bindEvent() {
		nearby_businesses.setOnClickListener(this);
		brand_hall.setOnClickListener(this);
		self_supermarket.setOnClickListener(this);
		my_order.setOnClickListener(this);
		swipeRefreshLayout.setOnRefreshListener(listener);
		tWidget.setChageAlpha();
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				refreshOperation();
			}
		});
		pl_scrollVeiw.setCallbacks(new Callbacks() {
			
			@Override
			public void onUpOrCancelMotionEvent() {
				
			}
			
			@Override
			public void onScrollChanged(int scrollY) {
				int adHeight=adView.getHeight();
				float f=(float)scrollY/(float)adHeight;
				if(f<=1){
				tWidget.setTitleAlpha((int)(255*f));
				}else{
					tWidget.setTitleAlpha(255);
				}
			}
			
			@Override
			public void onDownMotionEvent() {
				
			}
			
			@Override
			public void doOnBottom() {
				
			}
		});

	}

	public void refreshOperation() {
		Const.cache.removeAdvList();
		Const.cache.removeRgList();
		lv_main_pager.removeAllViews();
		//bindDataForUIElement();
		GetAds();
		bindEvent();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.nearby_businesses:
			intent = new Intent(getActivity(), NearbyBusinessActivity.class);
			startActivity(intent);
			break;
		case R.id.brand_hall:
			intent = new Intent(getActivity(), BrandsActivity.class);
			startActivity(intent);
			break;
		case R.id.self_supermarket:
			intent = new Intent(getActivity(), ToSelfSupermarketActivity.class);
			startActivity(intent);
			break;
		case R.id.my_order:
			if (Const.isLogin) {
				intent = new Intent(getActivity(), OrderActivity.class);
				startActivity(intent);
			} else {
				isEnterOrderLoading = true;
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
			}
			break;
		}
	}
 
	@Override
	public void onResume() {
		super.onResume();
		refreshCity();
		if (isEnterOrderLoading) {
			if (Const.isLogin) {
				Intent intent = new Intent(getActivity(), OrderActivity.class);
				startActivity(intent);
			}
			isEnterOrderLoading = false;
		}
	}

	public void refreshCity() {
		tWidget.refreshCity();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				int currentitem = adViewpager.getCurrentItem();
				currentitem++;
				adViewpager.setCurrentItem(currentitem);
				this.sendEmptyMessageDelayed(1, 3000);
				break;
			}
		}
	};

	/**
	 * INIT AD
	 */
	private void initAdvertisement() {
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		if(Const.cache.getAdvertisementList()!=null&&Const.cache.getAdvertisementList().size()!=0){
		adView = View.inflate(getActivity(), R.layout.fragment_mainpager_ad,
				null);
		adViewpager = (ViewPager) adView.findViewById(R.id.adviewPager);
		tipGroup = (ViewGroup) adView.findViewById(R.id.viewGroup);
		settips(); 
		setimage();
		adadapter = new AdAdapter(mImageViews, this, Const.cache.getAdvertisementList());
		adViewpager.setAdapter(adadapter);
		if (Const.cache.getAdvertisementList().size() != 1) {
			handler.sendEmptyMessageAtTime(1, 3000);
			adViewpager.setCurrentItem((mImageViews.length) * 100);
		} else {
			adViewpager.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return true;
				}
			});
		}
		adViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrollStateChanged(int arg0) {
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageSelected(int arg0) {

						if (Const.cache.getAdvertisementList().size() != 1) {
							handler.removeMessages(1);
							handler.sendEmptyMessageDelayed(1, 3000);
						}
						int selectItems = arg0 % mImageViews.length;
						for (int i = 0; i < tips.length; i++) {
							if (i == selectItems) {
								tips[i].setBackgroundResource(R.drawable.onfocuse);
							} else {
								tips[i].setBackgroundResource(R.drawable.focuse);
							}
						}
					}

				});
		lv_main_pager.addView(adView);
		}
	}

	/**
	 * INIT MENU
	 */
	private void initMeun() {
		menuView = View.inflate(getActivity(),
				R.layout.fragment_mainpager_menu, null);
		nearby_businesses = (ImageButton) menuView
				.findViewById(R.id.nearby_businesses);
		brand_hall = (ImageButton) menuView.findViewById(R.id.brand_hall);
		self_supermarket = (ImageButton) menuView
				.findViewById(R.id.self_supermarket);
		my_order = (ImageButton) menuView.findViewById(R.id.my_order);

	}

	/**
	 * INIT LISTVIEW
	 */
	private void initReGood() {
		if(Const.cache.getRecommendGoodsList()!=null){
		for (int i = 0; i < Const.cache.getRecommendGoodsList().size(); i++) {
			View review = View.inflate(getActivity(),
					R.layout.mainpage_recommend_item, null);
			TextView tv_recommendation_name = (TextView) review
					.findViewById(R.id.tv_recommendation_name);
			TextView bt_main_more = (TextView) review
					.findViewById(R.id.bt_main_more);
			MyHorizontalScrollView mHorizontalScrollView = (MyHorizontalScrollView) review
					.findViewById(R.id.id_horizontalScrollView);
			bt_main_more.setOnClickListener(new MoreGoodsOnClickListener(i));
			tv_recommendation_name.setText(Const.cache.getRecommendGoodsList().get(i).catName);
			HorizontalScrollViewAdapter mAdapter = new HorizontalScrollViewAdapter(
					getActivity(), Const.cache.getRecommendGoodsList().get(i).goodlistbean);
			mHorizontalScrollView.initDatas(mAdapter);
			lv_main_pager.addView(review);
		}
	   }
	}

	class MoreGoodsOnClickListener implements OnClickListener {
		private int position;

		public MoreGoodsOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), GoodListActivity.class);
			intent.putExtra(GoodsListFragment.Mode_GoodsCatIdOne,
					Integer.parseInt(Const.cache.getRecommendGoodsList().get(position).catId));
			startActivity(intent);
		}

	}

	private void settips() {
		tips = new ImageView[Const.cache.getAdvertisementList().size()];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.onfocuse);
			} else {
				tips[i].setBackgroundResource(R.drawable.focuse);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(10, 10));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			tipGroup.addView(imageView, layoutParams);
		}
	}

	private void setimage() {
		if (Const.cache.getAdvertisementList().size() == 2 || Const.cache.getAdvertisementList().size() == 3) {
			mImageViews = new ImageView[Const.cache.getAdvertisementList().size() * 2];
		} else {
			mImageViews = new ImageView[Const.cache.getAdvertisementList().size()];
		}
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(getActivity());
			((BaseActivity) getActivity()).loadOnRectangleImage(
					Const.BASE_URL
							+ Const.cache.getAdvertisementList().get(
									i > (Const.cache.getAdvertisementList().size() - 1) ? i
											- Const.cache.getAdvertisementList().size() : i)
									.getAdFile(), imageView);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			mImageViews[i] = imageView;
		}

	}

	private void initHight() {
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		SortFiled.height = wm.getDefaultDisplay().getHeight();
		SortFiled.width = wm.getDefaultDisplay().getWidth();

	}
}
