package com.wstmall.fragment.business;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.zhy_9.food_test.R;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.shoppingCart.ShoppingCartWithoutMainpageActivity;
import com.wstmall.adapter.BusinessGoodsAdapter;
import com.wstmall.api.business.home.GetShopInfo;
import com.wstmall.api.getshop.good.GetShopGoods;
import com.wstmall.application.Const;
import com.wstmall.bean.BusinessHomeBean;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.BusinessHomeListView;
import com.wstmall.widget.BusinessHomeListView.CallBacks;
import com.wstmall.widget.KaterinaRefreshListview;
import com.wstmall.widget.KaterinaRefreshListview.KateOnRefreshListener;
import com.wstmall.widget.ListViewIndecator;
import com.wstmall.widget.MyPullRefreshListView;
import com.wstmall.widget.ObservableScrollView;

/**
 * 商家主页
 * 
 * @ClassName BusinessHomeFragment
 * @author pzl
 * 
 */
@FragmentView(id = R.layout.fragment_business_home_page)
public class BusinessHomeFragment extends BaseFragment implements
		View.OnClickListener {

	public static final String FromSelfSupermarket = "FROMSELFSUPERMARKET";

	@InjectView(id = R.id.profile_layout)
	private View profile_layout;

	@InjectView(id = R.id.business_home_image)
	private ImageView business_home_image;

	@InjectView(id = R.id.business_home_describe)
	private TextView business_home_describe;

	@InjectView(id = R.id.business_home_phone)
	private TextView business_home_phone;

	@InjectView(id = R.id.goods_describe)
	private TextView goods_describe;

	@InjectView(id = R.id.goods_gettime)
	private TextView goods_gettime;

	@InjectView(id = R.id.goods_serve)
	private TextView goods_serve;
	
	@InjectView(id = R.id.iv_sell_arrow)
	private ImageView iv_sell_arrow;
	@InjectView(id = R.id.iv_price_arrow)
	private ImageView iv_price_arrow;
	@InjectView(id = R.id.iv_describe_arrow)
	private ImageView iv_describe_arrow;

	@InjectView(id = R.id.price_start)
	private EditText price_start;
	@InjectView(id = R.id.price_end)
	private EditText price_end;
	@InjectView(id = R.id.price_search)
	private Button price_search;
	@InjectView(id = R.id.sort_sell)
	private View sort_sell;
	@InjectView(id = R.id.sort_evalution)
	private View sort_evalution;
	@InjectView(id = R.id.sort_price)
	private View sort_price;
	//ticky
	@InjectView(id = R.id.lv_ktr)
	private KaterinaRefreshListview lv_ref;
	@InjectView(id = R.id.ll_sort)
    private int lastGoodsCount;
    private int stopHeight=-1;
    private ListView lv_ktr;
    //透明度控制
	@InjectView(id = R.id.ll_stop)
	private LinearLayout ll_stop;
	@InjectView(id = R.id.ll_move)
	private LinearLayout ll_move;
	@InjectView(id = R.id.shopping_cart)
	private ImageView shopping_cart;
	@InjectView(id = R.id.shopping_cart_num)
	private TextView shopping_cart_num;

	@InjectView(id = R.id.tv_mycontainer)
	private ListViewIndecator tv_mycontainer;

	private int[] end_location = new int[2];// 用来存储动画结束位置的X、Y坐标
	private ViewGroup anim_mask_layout;// 动画层
	private ImageView buyImg;// 这是在界面上跑的小图片
	private Boolean have_initEnd = false;

	private GetShopInfo getshopinfo;
	private GetShopGoods getshopgood;
	private BusinessHomeBean businessbean;
	private List<GoodsListBean> businesshomegood;
	private BusinessGoodsAdapter businessgoodadapter;
	private DecimalFormat df0 = new DecimalFormat("0.0");

	private Boolean fromSelfSupermark = false;
	private LinearLayout footerView;
	private int totalItem;
	public BusinessHomeFragment(String shopId, String shopName) {
		this.businessbean = new BusinessHomeBean();
		this.businessbean.shopId = shopId;
		this.businessbean.shopName = shopName;
	}

	public BusinessHomeFragment(String mode) {
		if (mode.equals(FromSelfSupermarket)) {
			fromSelfSupermark = true;
		}
	}

	public void initProfileLayout() {
		business_home_describe.setText(businessbean.shopAddress);
		business_home_phone.setText(businessbean.shopTel);
		goods_describe.setText(businessbean.goodsScore);
		goods_gettime.setText(businessbean.timeScore);
		goods_serve.setText(businessbean.serviceScore);
		loadOnRoundImage(Const.BASE_URL + businessbean.shopImg,
				business_home_image);
	}

	protected void requestSuccess(String url, String data) {
		if (url.contains(getshopinfo.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONObject homedata = jsonobj.getJSONObject("data");
					businessbean = gson.fromJson(homedata.toString(),
							BusinessHomeBean.class);
					tWidget.setCenterViewText(businessbean.shopName);
					initProfileLayout();
					getshopgood = new GetShopGoods();
					getshopgood.shopId = businessbean.shopId;
					getshopgood.desc = 0;
					getshopgood.descType = 0;
					getshopgood.p = 1;
					requestNoDialog(getshopgood);
				}
			} catch (JSONException e) {

			}

		} else if (url.contains(getshopgood.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						GoodsListBean Bean = gson.fromJson(jsonArray
								.getJSONObject(i).toString(),
								GoodsListBean.class);
						businesshomegood.add(Bean);
					}
					if (lastGoodsCount == businesshomegood.size()||businesshomegood.size()<6) {
						lv_ktr.removeFooterView(footerView);
					}
					lastGoodsCount = businesshomegood.size();
					businessgoodadapter.notifyDataSetChanged();
					lv_ref.OnRefreshFinsh();
					//tv_mycontainer.setText((lv_ktr.getLastVisiblePosition()-1) + "/" +businesshomegood.size());
					tv_mycontainer.setFz((lv_ktr.getLastVisiblePosition()-1)+"");
					tv_mycontainer.setFm(businesshomegood.size()+"");
			//		setListViewHeightBasedOnChildren(lv_ktr);
				}
			} catch (JSONException e) {

			}
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		businesshomegood = new ArrayList<GoodsListBean>();
		businessgoodadapter = new BusinessGoodsAdapter(getActivity(),
				businesshomegood, this);
		lv_ktr=lv_ref.getListView();
		initEmptyHeadView();
		initFooter();
		lv_ktr.setAdapter(businessgoodadapter);
	//	setListViewHeightBasedOnChildren(lv_ktr);
		getshopinfo = new GetShopInfo();
		getshopinfo.areaId2 = Const.cache.city.getCityid();

		if (fromSelfSupermark) {
			tWidget.setCenterViewText("自营超市");
			getshopinfo.isSelfShop = 1;
		} else {
			tWidget.setCenterViewText(businessbean.shopName);
			getshopinfo.shopId = businessbean.shopId;
		}
		request(getshopinfo);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (Const.cache.getShoppingCartSum() == 0) {
			shopping_cart_num.setText("0");
			shopping_cart_num.setVisibility(View.INVISIBLE);
		} else {
			shopping_cart_num.setText(Const.cache.getShoppingCartSum() + "");
			shopping_cart_num.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		price_search.setOnClickListener(this);
		sort_sell.setOnClickListener(this);
		sort_evalution.setOnClickListener(this);
		sort_price.setOnClickListener(this);
		shopping_cart.setOnClickListener(this);
		lv_ktr.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				intent.putExtra(GoodsActivity.goodsID,
						businesshomegood.get(position-1).goodsId);
				startActivity(intent);
			}
		});
		lv_ktr.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(lv_ktr.getLastVisiblePosition()==totalItem-2){
					getshopgood.p = getshopgood.p + 1;
					requestNoDialog(getshopgood);
				}

			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				//tv_mycontainer.setText((lv_ktr.getLastVisiblePosition()-1) + "/" +businesshomegood.size());
				tv_mycontainer.setFz((lv_ktr.getLastVisiblePosition()-1)+"");
				Log.e("nidayea======", (lv_ktr.getLastVisiblePosition()-1)+"");
				tv_mycontainer.setFm(businesshomegood.size()+"");
				View fView=lv_ktr.getChildAt(0);
				if(fView==null){
					return;
				}
				int top=fView.getTop();
				if(lv_ktr.getFirstVisiblePosition()==0){
					ll_move.setTranslationY(top);
				}else{
					ll_move.setTranslationY(-stopHeight);
				}
				totalItem=totalItemCount;
			}
		});
        ll_stop.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                    	stopHeight=ll_stop.getHeight();
                    }
                });
        lv_ref.setOnRefresh(new KateOnRefreshListener() {
			
			@Override
			public void reRresh() {
				// TODO Auto-generated method stub
				  businesshomegood.clear();
				  businessgoodadapter.notifyDataSetChanged(); 
				  getshopgood.p = 1;
				  requestNoDialog(getshopgood); 

			}
		});
		/*
		 * lv_business_goods .setOnRefreshListener(new
		 * OnRefreshListener2<ListView>() {
		 * 
		 * @Override public void onPullDownToRefresh(
		 * PullToRefreshBase<ListView> refreshView) { // TODO Auto-generated
		 * method stub 
		 * businesshomegood.clear();
		 * businessgoodadapter.notifyDataSetChanged(); 
		 * getshopgood.p = 1;
		 * requestNoDialog(getshopgood); }
		 * 
		 * @Override public void onPullUpToRefresh( PullToRefreshBase<ListView>
		 * refreshView) { // TODO Auto-generated method stub getshopgood.p++;
		 * requestNoDialog(getshopgood); } });
		 */

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.price_search:
			double tempMin,
			tempMax;
			if (TextUtils.isEmpty(price_start.getText().toString())
					|| Double.parseDouble(price_start.getText().toString()) == 0) {
				tempMin = 0;
				price_start.setText("");
			} else {
				tempMin = Double.parseDouble(price_start.getText().toString());
				price_start.setText(df0.format(tempMin));
			}
			getshopgood.startPrice = tempMin;
			if (TextUtils.isEmpty(price_end.getText().toString())
					|| Double.parseDouble(price_end.getText().toString()) == 0) {
				price_end.setText("");
				tempMax = 0;
			} else {
				tempMax = Double.parseDouble(price_end.getText().toString());
				price_end.setText(df0.format(tempMax));
			}
			getshopgood.endPrice = tempMax;

			initLv(); 
			getshopgood.p = 1;

			if (tempMax == 0 || tempMax == tempMin) {
				request(getshopgood);
				break;
			}

			if (tempMax < tempMin) {
				getshopgood.startPrice = tempMax;
				getshopgood.endPrice = tempMin;
				String tempString = price_end.getText().toString();
				price_end.setText(price_start.getText().toString());
				price_start.setText(tempString);
			}

			request(getshopgood);
			break;
		case R.id.sort_sell:
			initLv();
			if (getshopgood.desc == 0) {
				if (getshopgood.descType == 0) {
					iv_sell_arrow
							.setImageResource(R.drawable.goods_list_arrows_up_yellow);
					getshopgood.descType = 1;
				} else {
					iv_sell_arrow
							.setImageResource(R.drawable.goods_list_arrows_yellow);
					getshopgood.descType = 0;
				}
			} else {
				resetboder();
				sort_sell.setBackgroundResource(R.drawable.boder_except_bottom);
				iv_sell_arrow
						.setImageResource(R.drawable.goods_list_arrows_yellow);
				getshopgood.desc = 0;
				getshopgood.descType = 0;
			}
			getshopgood.p = 1;
			request(getshopgood);
			break;
		case R.id.sort_evalution:
			initLv();
			if (getshopgood.desc == 2) {
				if (getshopgood.descType == 0) {
					iv_describe_arrow
							.setImageResource(R.drawable.goods_list_arrows_up_yellow);
					getshopgood.descType = 1;
				} else {
					iv_describe_arrow
							.setImageResource(R.drawable.goods_list_arrows_yellow);
					getshopgood.descType = 0;
				}
			} else {
				resetboder();
				sort_evalution
						.setBackgroundResource(R.drawable.boder_except_bottom);
				iv_describe_arrow
						.setImageResource(R.drawable.goods_list_arrows_yellow);
				getshopgood.desc = 2;
				getshopgood.descType = 0;
			}
			getshopgood.p = 1;
			request(getshopgood);
			break;
		case R.id.sort_price:
			initLv();
			if (getshopgood.desc == 1) {
				if (getshopgood.descType == 0) {
					iv_price_arrow
							.setImageResource(R.drawable.goods_list_arrows_up_yellow);
					getshopgood.descType = 1;
				} else {
					iv_price_arrow
							.setImageResource(R.drawable.goods_list_arrows_yellow);
					getshopgood.descType = 0;
				}
			} else {
				resetboder();
				sort_price
						.setBackgroundResource(R.drawable.boder_except_bottom);
				iv_price_arrow
						.setImageResource(R.drawable.goods_list_arrows_yellow);
				getshopgood.desc = 1;
				getshopgood.descType = 0;
			}
			getshopgood.p = 1;
			request(getshopgood);
			break;
		case R.id.shopping_cart:
			// 点击购物车
			Intent intent = new Intent(getActivity(),
					ShoppingCartWithoutMainpageActivity.class);
			startActivity(intent);
			break;
		}
	}
	private void initLv(){
		lastGoodsCount=0;
		businesshomegood.clear();
		lv_ktr.removeFooterView(footerView);
		lv_ktr.addFooterView(footerView, null, false);
	}
	private void initEmptyHeadView(){
		View headview = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_business_home_header, null);
		View headviews = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.fragment_business_home_header_second, null);
		lv_ktr.addHeaderView(headview,null,false);
		lv_ktr.addHeaderView(headviews,null,false);
		
	}
	private void initFooter(){
		footerView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.listview_footer, null);
		ImageView footerAmi = (ImageView) footerView.findViewById(R.id.loading);
		AnimationDrawable animationDrawable = (AnimationDrawable) footerAmi
				.getBackground();
		animationDrawable.start();
		lv_ktr.addFooterView(footerView, null, false);
	}
	public void resetboder() {
		sort_sell.setBackgroundResource(R.drawable.boder_top_bottom);
		iv_sell_arrow.setImageResource(R.drawable.goods_list_arrow_gray);

		sort_evalution.setBackgroundResource(R.drawable.boder_top_bottom);
		iv_describe_arrow.setImageResource(R.drawable.goods_list_arrow_gray);

		sort_price.setBackgroundResource(R.drawable.boder_top_bottom);
		iv_price_arrow.setImageResource(R.drawable.goods_list_arrow_gray);

	}

	public void addShopCart(View v) {
		if (!have_initEnd) {
			shopping_cart.getLocationInWindow(end_location);
			end_location[0] += shopping_cart.getWidth() / 2;
			have_initEnd = true;
		}
		int[] start_location = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
		v.getLocationInWindow(start_location);// 这是获取购买按钮的启始的屏幕的X、Y坐标
		start_location[1] += v.getHeight() / 2;

		buyImg = new ImageView(getActivity());// buyImg是动画的图片
		buyImg.setImageResource(R.drawable.point_add_shopping);// 设置buyImg的图片
		setAnim(buyImg, start_location);// 开始执行动画
	}

	private void setAnim(final View v, int[] start_location) {
		anim_mask_layout = null;
		anim_mask_layout = createAnimLayout();
		anim_mask_layout.addView(v);// 把动画小球添加到动画层
		final View view = addViewToAnimLayout(anim_mask_layout, v,
				start_location);

		// 计算位移
		int endX = end_location[0] - start_location[0];// 动画位移的X坐标
		int endY = end_location[1] - start_location[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
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
				// TODO Auto-generated method stub
			}

			// 动画的结束
			@Override
			public void onAnimationEnd(Animation animation) {
				v.setVisibility(View.GONE);

				if (shopping_cart_num.getText().toString().equals("0")) {
					shopping_cart_num.setText("1");
					shopping_cart_num.setVisibility(View.VISIBLE);
				} else {
					shopping_cart_num.setText(((Integer) (Integer
							.parseInt(shopping_cart_num.getText().toString()) + 1))
							.toString());
				}
			}
		});

	}

	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) getActivity().getWindow()
				.getDecorView();
		LinearLayout animLayout = new LinearLayout(getActivity());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup vg, final View view,
			int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	private static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

/*	@Override
	public void doOnBottom() {
		// TODO Auto-generated method stub
		getshopgood.p = getshopgood.p + 1;
		requestNoDialog(getshopgood);
	}*/
}
