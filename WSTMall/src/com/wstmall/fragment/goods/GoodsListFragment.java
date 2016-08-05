package com.wstmall.fragment.goods;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.shoppingCart.ShoppingCartWithoutMainpageActivity;
import com.wstmall.adapter.GoodsListAdapter;
import com.wstmall.api.goods.GetGoodsByCatId;
import com.wstmall.application.Const;
import com.wstmall.application.SortFiled;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.GoodsSortPopWindows;
import com.wstmall.widget.GoodsSortPopWindows.AttrsSelectInf;
import com.wstmall.widget.KaterinaRefreshListview;
import com.wstmall.widget.KaterinaRefreshListview.KateOnRefreshListener;

/**
 * 
 * @author pzl
 * 
 */
@FragmentView(id = R.layout.goods_list)
public class GoodsListFragment extends BaseFragment implements
		View.OnClickListener {

	protected static final String TAG = "GoodsListFragment";

	public static final String SearchTarget = "SearchTarget";
	public static final String GoodsCatId = "GoodsCatId";
	public static final String Mode_FromBrands = "FROMBRANDS";
	public static final String Mode_GoodsCatIdOne = "GOODSCATIDONE";
	public static final String Mode_GoodsCatIdSecond = "GOODSCATIDSECOND";

	@InjectView(id = R.id.pull_refresh_list)
	private KaterinaRefreshListview mPullRefreshListView;

	@InjectView(id = R.id.shopping_cart)
	private ImageView shopping_cart;
	@InjectView(id = R.id.shopping_cart_num)
	private TextView shopping_cart_num;

	private int[] end_location = new int[2];// 用来存储动画结束位置的X、Y坐标
	private ViewGroup anim_mask_layout;// 动画层
	private ImageView buyImg;// 这是在界面上跑的小图片
	private Boolean have_initEnd = false;

	private GetGoodsByCatId getGoodsByCatId = new GetGoodsByCatId();
	private GoodsListAdapter goodsadapter;
	private List<GoodsListBean> goodsinfolist;
	private String brandName;
	// pop sort
	private GoodsSortPopWindows goodsSortPopWindows;
	private View emptyView;
	private int move_distance;
	private int endY;
	private int startY;
	private int sortBarHeight;
	private boolean isDown;// 判断手势
	private boolean isSortBarShow=true;
	@InjectView(id = R.id.sort_bar)
	private RelativeLayout sort_bar;

	@InjectView(id = R.id.ll_sign)
	private LinearLayout ll_sign;
	@InjectView(id = R.id.tv_sort)
	private TextView tv_sort;
	@InjectView(id = R.id.tv_result_count)
	private TextView tv_result_count;
//move
	@InjectView(id = R.id.iv_move)
	private ImageView iv_move;
	private float itemTop;
	private LinearLayout footerView;
	private boolean isAddFooter;
	private int totalItem;
	private boolean isHaveData=true;
	public GoodsListFragment(String searchTarget) {
		getGoodsByCatId.key = searchTarget;
	}

	public GoodsListFragment(int goodsCatId) {
		getGoodsByCatId.goodsCatId = goodsCatId;
	}

	public GoodsListFragment(int goodsCatId, String mode) {
		if (mode.equals(Mode_GoodsCatIdOne)) {
			getGoodsByCatId.goodsCatId1 = goodsCatId;
		} else if (mode.equals(Mode_GoodsCatIdSecond)) {
			getGoodsByCatId.goodsCatId2 = goodsCatId;
		}
	}

	public GoodsListFragment(String brandId, String brandName, String mode) {
		if (mode.equals(Mode_FromBrands)) {
			getGoodsByCatId.brandId = brandId;
			this.brandName = brandName;
		}
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getGoodsByCatId.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					if (jsonArray.length() != 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							GoodsListBean Bean = new Gson().fromJson(jsonArray
									.getJSONObject(i).toString(),
									GoodsListBean.class);
							goodsinfolist.add(Bean);
						}
						mPullRefreshListView.OnRefreshFinsh();
						
						tv_result_count.setText("当前共" + goodsinfolist.size()
								+ "个结果");
						sort_bar.setVisibility(View.VISIBLE);
						isSortBarShow=true;
						if(goodsinfolist.size()>5){
							addFooter();
						}
						goodsadapter.notifyDataSetChanged();
					} else {
						mPullRefreshListView.OnRefreshFinsh();
						isHaveData=false;
						removeFooter();
					}
				}
			} catch (JSONException e) {

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { // 排序
		case R.id.tv_sort:
			// goodsSortPopWindows.showAtLocation(ll_sign,
			// Gravity.RIGHT|Gravity.BOTTOM, 0, 0);
			goodsSortPopWindows.showAsDropDown(ll_sign, (int) (SortFiled.width/6), 0);
			setBackgroundAlpha(0.5f);
			break;
		case R.id.shopping_cart:
			// 点击购物车
			Intent intent = new Intent(getActivity(),
					ShoppingCartWithoutMainpageActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		if (brandName != null) {
			tWidget.setCenterViewText(brandName);
		} else {
			tWidget.changeMode(tWidget.Left_Search_Zbar_Mode);
		}
		getSortBarPix();
		goodsSortPopWindows = new GoodsSortPopWindows(getActivity());
		goodsinfolist = new ArrayList<GoodsListBean>();
		goodsadapter = new GoodsListAdapter(getActivity(), goodsinfolist, this);
		initEmptyHead(); 
		initFooter();
		mPullRefreshListView.setAdapter(goodsadapter);
		getGoodsByCatId.latitude = Const.cache.point.getGeoLat();
		getGoodsByCatId.longitude = Const.cache.point.getGeoLng();
		getGoodsByCatId.areaId2 = Const.cache.city.getCityid();
		getGoodsByCatId.desc = 0;
		getGoodsByCatId.descType = 0;
		getGoodsByCatId.p = 1;
		request(getGoodsByCatId);
	}

	@Override
	protected void flagFailed(String url) {
		if (url.contains(getGoodsByCatId.getA())) {
			mPullRefreshListView.OnRefreshFinsh();
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		shopping_cart.setOnClickListener(this);
		tv_sort.setOnClickListener(this);
		mPullRefreshListView.setOnRefresh(new KateOnRefreshListener() {
			
			@Override
			public void reRresh() {
				// TODO Auto-generated method stub
				goodsinfolist.clear();
				tv_result_count.setText("刷新中...");
				goodsadapter.notifyDataSetChanged();
				getGoodsByCatId.p = 1;
				requestNoDialog(getGoodsByCatId);
				sort_bar.setVisibility(View.GONE);
				isSortBarShow=false;
				isHaveData=true;
				removeFooter();
			}
		});
		mPullRefreshListView.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), GoodsActivity.class);
				intent.putExtra(GoodsActivity.goodsID,
						goodsinfolist.get(position - 1).goodsId);
				startActivity(intent);
/*				ImageView iv=(ImageView) view.findViewById(R.id.im_goods);
				loadOnRectangleImage(Const.BASE_URL +goodsinfolist.get(position-2).goodsThums,iv_move);
				iv_move.setVisibility(View.VISIBLE);
				iv_move.setTranslationY(view.getY()+SortFiled.dip2px(getActivity(), 60));
				iv_move.setTranslationX(SortFiled.dip2px(getActivity(), 5));
				iv_move.invalidate();
				Log.e("timo", view.getY()+"");
				itemTop=view.getY();
				iv_move.startAnimation(SortFiled.getatSetImLa(SortFiled.width/(float)(SortFiled.dip2px(getActivity(), 68)),3f,(float)(SortFiled.dip2px(getActivity(), 7)),itemTop+SortFiled.dip2px(getActivity(), 11)));
*/			}
		});

		goodsSortPopWindows.setAttrsSelectListener(new AttrsSelectInf() {
			@Override
			public void AttrsSelect(int position) {
				// TODO Auto-generated method stub
				goodsSortPopWindows.dismiss();
				switch (position) {
				case 0:
					getGoodsByCatId.desc = 0;
					getGoodsByCatId.descType = 0;

					break;
				case 1:
					getGoodsByCatId.desc = 0;
					getGoodsByCatId.descType = 1;

					break;
				case 2:
					getGoodsByCatId.desc = 1;
					getGoodsByCatId.descType = 0;
					break;
				case 3:
					getGoodsByCatId.desc = 1;
					getGoodsByCatId.descType = 1;
					break;
				case 4:
					getGoodsByCatId.desc = 2;
					getGoodsByCatId.descType = 0;
					break;
				case 5:
					getGoodsByCatId.desc = 2;
					getGoodsByCatId.descType = 1;
					break;
				}
				setBackgroundAlpha(1f);
				isHaveData=true;
				refleshList();
			}
		});
		mPullRefreshListView.getListView().setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if(mPullRefreshListView.getListView().getLastVisiblePosition()==totalItem-1&&isHaveData){
					getGoodsByCatId.p++;
					requestNoDialog(getGoodsByCatId);
					addFooter();
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(firstVisibleItem>1){
					if(!isDown&&isSortBarShow){
						sort_bar.startAnimation(SortFiled.getFadeOut());
						sort_bar.setVisibility(View.GONE);
						isSortBarShow=false;
					}
				}
				if((isDown||firstVisibleItem<=1)&&!isSortBarShow){
					sort_bar.startAnimation(SortFiled.getFadeIn());
					sort_bar.setVisibility(View.VISIBLE);
					isSortBarShow=true;
				}
				Log.e("firstVisibleItem", firstVisibleItem+"");
				totalItem=totalItemCount;
			}
		});
		mPullRefreshListView.getListView().setOnTouchListener(
				new OnTouchListener() {

					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							startY = (int) event.getRawY();
							// Log.e("开始", startY+"");
							break;
						case MotionEvent.ACTION_MOVE:
							endY = (int) event.getRawY();
							// Log.e("结速", endY+"");
							move_distance = endY - startY;
							// Log.e("移动", move_distance+"");
							if (Math.abs(move_distance) > 10) {
								if (move_distance < 0) {
									isDown=false;
								} else {
									isDown=true;
								}
							}
							break;
						}
						return false;
					}
				});
	}
	private void addFooter(){
		if(!isAddFooter){
		mPullRefreshListView.getListView().addFooterView(footerView, null, false);
			isAddFooter=true;
		}
	}
	private void removeFooter(){
		mPullRefreshListView.getListView().removeFooterView(footerView);
		isAddFooter=false;
	}
	private void initFooter(){
		footerView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
				R.layout.listview_footer, null);
		ImageView footerAmi = (ImageView) footerView.findViewById(R.id.loading);
		AnimationDrawable animationDrawable = (AnimationDrawable) footerAmi
				.getBackground();
		animationDrawable.start();
		
	}
	private void refleshList() {
		getGoodsByCatId.p = 1;
		goodsinfolist.clear();
		goodsadapter.notifyDataSetChanged();
		request(getGoodsByCatId);
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

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param pzl
	 */
	private void setBackgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	private void initEmptyHead() {
		emptyView = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_40dp_empty_head, null);
		mPullRefreshListView.getListView().addHeaderView(emptyView,null,false);
	}

	public void getSortBarPix() {
		sort_bar.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				sortBarHeight = sort_bar.getHeight();
			}
		});
	}
}
