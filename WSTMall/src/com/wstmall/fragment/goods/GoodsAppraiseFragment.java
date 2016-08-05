
package com.wstmall.fragment.goods;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy_9.food_test.R;
import com.wstmall.activity.nearbybusiness.NearbyBusinessActivity;
import com.wstmall.adapter.AppraisesListAdapter;
import com.wstmall.api.goods.GetAppraises;
import com.wstmall.application.Const;
import com.wstmall.bean.AppraiseListBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.MyPullRefreshListView;


@FragmentView(id = R.layout.fragment_appraise)
public class GoodsAppraiseFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.list_all_button)
	private View list_all_button;
	@InjectView(id = R.id.list_all_num)
	private TextView list_all_num;
	@InjectView(id = R.id.list_all_line)
	private View list_all_line;

	@InjectView(id = R.id.list_well_button)
	private View list_well_button;
	@InjectView(id = R.id.list_well_num)
	private TextView list_well_num;
	@InjectView(id = R.id.list_well_line)
	private View list_well_line;

	@InjectView(id = R.id.list_between_button)
	private View list_between_button;
	@InjectView(id = R.id.list_between_num)
	private TextView list_between_num;
	@InjectView(id = R.id.list_between_line)
	private View list_between_line;

	@InjectView(id = R.id.list_bad_button)
	private View list_bad_button;
	@InjectView(id = R.id.list_bad_num)
	private TextView list_bad_num;
	@InjectView(id = R.id.list_bad_line)
	private View list_bad_line;

	@InjectView(id = R.id.list_viewpager)
	private ViewPager viewPager;

	private int pageNumberNow = 0;

	private AppraisesListView[] list = new AppraisesListView[4];
	private GetAppraises getAppraises = new GetAppraises();

	public GoodsAppraiseFragment(String goodsId) {
		getAppraises.id = goodsId;
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getAppraises.getA())) {
			try {
				JSONObject jsonobj = new JSONObject(data);

				List<AppraiseListBean> tempList = new ArrayList<AppraiseListBean>();
				JSONArray jsonArray = jsonobj.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					tempList.add(gson.fromJson(jsonArray.get(i).toString(), AppraiseListBean.class));
				}

				if (list[pageNumberNow].initBoolean) {
					if (pageNumberNow == 0) {
						JSONObject typeBean = jsonobj.getJSONObject("type");
						try {
							list_all_num.setText("("+typeBean.getString("type0")+")");
						} catch (Exception e) {
							// TODO: handle exception
						}
						try {
							list_bad_num.setText("("+typeBean.getString("type3")+")");
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {
							list_between_num.setText("("+typeBean.getString("type2")+")");
						} catch (Exception e) {
							// TODO: handle exception
						}

						try {
							list_well_num.setText("("+typeBean.getString("type1")+")");
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					list[pageNumberNow].initBoolean = false;
					list[pageNumberNow].totalPage = jsonobj.getInt("totalPage");
					list[pageNumberNow].nowPage = 0;
					list[pageNumberNow].list = tempList;
					list[pageNumberNow].adapter = new AppraisesListAdapter(getActivity(), tempList);
					list[pageNumberNow].setAdapter(list[pageNumberNow].adapter);
				} else {
					list[pageNumberNow].list.addAll(tempList);
					list[pageNumberNow].adapter.notifyDataSetChanged();
				}
				list[pageNumberNow].nowPage++;
				list[pageNumberNow].onRefreshComplete();
			} catch (JSONException e) {
			}
		}
	}

	@Override
	protected void flagFailed(String url) {
		if (url.contains(getAppraises.getA())) {
			list[pageNumberNow].onRefreshComplete();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.list_all_button:
			viewPager.setCurrentItem(0);
			break;
		case R.id.list_well_button:
			viewPager.setCurrentItem(1);
			break;
		case R.id.list_between_button:
			viewPager.setCurrentItem(2);
			break;
		case R.id.list_bad_button:
			viewPager.setCurrentItem(3);
			break;
		}
	}

	private void clearTabLine() {
		list_all_line.setVisibility(View.INVISIBLE);
		list_well_line.setVisibility(View.INVISIBLE);
		list_between_line.setVisibility(View.INVISIBLE);
		list_bad_line.setVisibility(View.INVISIBLE);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		getAppraises.type = pageNumberNow;
		request(getAppraises);
		for (int i = 0; i < 4; i++) {
			list[i] = new AppraisesListView(getActivity());
			list[i].setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(onPageChangeListener);
	}

	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			clearTabLine();
			pageNumberNow=arg0;
			switch (arg0) {
			case 0:
				list_all_line.setVisibility(View.VISIBLE);
				break;
			case 1:
				list_well_line.setVisibility(View.VISIBLE);
				break;
			case 2:
				list_between_line.setVisibility(View.VISIBLE);
				break;
			case 3:
				list_bad_line.setVisibility(View.VISIBLE);
				break;
			}
			if (list[pageNumberNow].initBoolean) {
				
				getAppraises.type = pageNumberNow;
				getAppraises.p = 1;
				request(getAppraises);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
		}
	};

	private PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.length;
		}

		// 滑动切换的时候销毁当前的组件
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(list[position]);
		}

		// 每次滑动的时候生成的组件
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(list[position]);
			return list[position];
		}

	};

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		list_all_button.setOnClickListener(this);
		list_well_button.setOnClickListener(this);
		list_between_button.setOnClickListener(this);
		list_bad_button.setOnClickListener(this);
	}

	private class AppraisesListView extends MyPullRefreshListView {

		private int nowPage;
		private int totalPage;
		private Boolean initBoolean = true;
		private List<AppraiseListBean> list = new ArrayList<AppraiseListBean>();
		private AppraisesListAdapter adapter=new AppraisesListAdapter(getActivity(), list);

		public AppraisesListView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			setHorizontalScrollBarEnabled(false);
			setVerticalScrollBarEnabled(false);
			setMode(Mode.BOTH);
			setOnRefreshListener(onRefreshListener2);
			
		}

		private OnRefreshListener2<ListView> onRefreshListener2 = new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				initBoolean = true;
				getAppraises.p = getAppraises.p+1;
				getAppraises.type = pageNumberNow;
				requestNoDialog(getAppraises);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				getAppraises.p = nowPage + 1;
				getAppraises.type = pageNumberNow;
				requestNoDialog(getAppraises);
			}
		};

	}

}
