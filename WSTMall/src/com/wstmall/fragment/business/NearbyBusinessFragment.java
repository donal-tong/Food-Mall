package com.wstmall.fragment.business;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.adapter.BusinessAdapter;
import com.wstmall.api.nearbybusiness.GetShops;
import com.wstmall.application.Const;
import com.wstmall.application.SortFiled;
import com.wstmall.bean.BusinessListbean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.GoodsSortPopWindows;
import com.wstmall.widget.MyPullRefreshListView;
import com.wstmall.widget.ShopsSortPopWindows;
import com.wstmall.widget.ShopsSortPopWindows.AttrsSelectInf;

/**
 * 商家列表
 * 
 * @author pzl
 * 
 */
@FragmentView(id = R.layout.business_list)
public class NearbyBusinessFragment extends BaseFragment implements
		View.OnClickListener {

	@InjectView(id = R.id.pull_business_refresh_list)
	private MyPullRefreshListView mPullRefreshListView;
	private BusinessAdapter businessadapter;
	private GetShops getshops = new GetShops();
	private List<BusinessListbean> businesslist;

	// pop sort
	private ShopsSortPopWindows shopsSortPopWindows;
	private View emptyView;
	private int move_distance;
	private int endY;
	private int startY;
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

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getshops.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					if (jsonArray.length() != 0) {
						for (int i = 0; i < jsonArray.length(); i++) {
							BusinessListbean Bean = new Gson().fromJson(
									jsonArray.getJSONObject(i).toString(),
									BusinessListbean.class);
							businesslist.add(Bean);
						}
						mPullRefreshListView.onRefreshComplete();
						tv_result_count.setText("当前共" + businesslist.size()
								+ "个商家");
						businessadapter.notifyDataSetChanged();
					} else {
						mPullRefreshListView.onRefreshComplete();
					}
				}
			} catch (JSONException e) {
			}
		}
	}

	@Override
	protected void flagFailed(String url) {
		if (url.contains(getshops.getA())) {
			mPullRefreshListView.onRefreshComplete();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_sort:
			// goodsSortPopWindows.showAtLocation(ll_sign,
			// Gravity.RIGHT|Gravity.BOTTOM, 0, 0);
			shopsSortPopWindows.showAsDropDown(ll_sign, (int) (SortFiled.width / 6), 0);
			setBackgroundAlpha(0.5f);
			break;

		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.changeMode(tWidget.Left_Search_Zbar_Mode);
		shopsSortPopWindows=new ShopsSortPopWindows(getActivity());
		mPullRefreshListView.setMode(Mode.PULL_FROM_START);
		businesslist = new ArrayList<BusinessListbean>();
		businessadapter = new BusinessAdapter(getActivity(), businesslist);
		initEmptyHead();
		mPullRefreshListView.setAdapter(businessadapter);
		getshops.areaId2 = Const.cache.city.getCityid();
		getshops.latitude = Const.cache.point.getGeoLat();
		getshops.longitude = Const.cache.point.getGeoLng();
		getshops.desc = 0;
		getshops.descType = 0;
		getshops.p = 1;
		request(getshops);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		tv_sort.setOnClickListener(this);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				replaceFragment(
						new BusinessHomeFragment(
								businesslist.get(position - 2).shopId,
								businesslist.get(position - 2).shopName), true);
			}
		});
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						businesslist.clear();
						tv_result_count.setText("刷新中...");
						businessadapter.notifyDataSetChanged();
						getshops.p = 1;
						requestNoDialog(getshops);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
					}
				});
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				// TODO Auto-generated method stub
				getshops.p++;
				requestNoDialog(getshops);
			}
		});
		shopsSortPopWindows.setAttrsSelectListener(new AttrsSelectInf() {

			@Override
			public void AttrsSelect(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					getshops.desc = 0;
					getshops.descType = 0;
					break;
				case 1:
					getshops.desc = 0;
					getshops.descType = 1;
					break;
				case 2:
					getshops.desc = 1;
					getshops.descType = 0;
					break;
				case 3:
					getshops.desc = 1;
					getshops.descType = 1;
					break;
				case 4:
					getshops.desc = 2;
					getshops.descType = 0;
					break;
				case 5:
					getshops.desc = 2;
					getshops.descType = 1;
					break;
				}
				setBackgroundAlpha(1f);
				reFleshView();
				shopsSortPopWindows.dismiss();
			}
		});
		mPullRefreshListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				View fView = mPullRefreshListView.getRefreshableView()
						.getChildAt(0);
				Log.e("1111111111111", firstVisibleItem + "");
				Log.e("sort_bar", fView.getTop() + "");
				// sort_bar.setTranslationY(fView.getTop());
				if (firstVisibleItem > 1) {
					if (!isDown&&isSortBarShow) {
						sort_bar.startAnimation(SortFiled.getFadeOut());
						sort_bar.setVisibility(View.GONE);
						isSortBarShow=false;
					}
				}
				if ((isDown || firstVisibleItem <= 1)&&!isSortBarShow) {
					sort_bar.startAnimation(SortFiled.getFadeIn());
					sort_bar.setVisibility(View.VISIBLE);
					isSortBarShow=true;
				}
			}
		});
		mPullRefreshListView.getRefreshableView().setOnTouchListener(
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
									isDown = false;
								} else {
									isDown = true;
								}
							}
							break;
						}
						return false;
					}
				});
	}

	private void reFleshView() {
		businesslist.clear();
		businessadapter.notifyDataSetChanged();
		getshops.p = 1;
		request(getshops);
	}

	private void setBackgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getActivity().getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		getActivity().getWindow().setAttributes(lp);
	}

	public void initEmptyHead() {
		emptyView = LayoutInflater.from(getActivity()).inflate(
				R.layout.list_40dp_empty_head, null);
		mPullRefreshListView.getRefreshableView().addHeaderView(emptyView,null,false);
	}

}
