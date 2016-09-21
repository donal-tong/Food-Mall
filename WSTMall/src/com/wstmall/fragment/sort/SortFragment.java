package com.wstmall.fragment.sort;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.zhy_9.food_test.R;
import com.amap.api.mapcore.util.i;
import com.wstmall.adapter.MyBaseAdapter;
import com.wstmall.api.goods.GetGoodsCats;
import com.wstmall.application.GoodsKinds;
import com.wstmall.application.GoodsKinds.GoodsKindsId;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

@FragmentView(id = R.layout.fragment_sort_page)
public class SortFragment extends BaseFragment {

	@InjectView(id = R.id.left_listView)
	private ListView left_listView;

	private View lastLeftSelectView;
	private int lastLeftSelectPosition;
	private List<SortRightFragment> sortRightList = new ArrayList<SortRightFragment>();
	private GetGoodsCats getGoodsCats = new GetGoodsCats();
	private int scrollPosition;

	@Override
	public void bindDataForUIElement() {
		request(getGoodsCats);
	}

	SortLeftAdapter adapter;
	
	@SuppressLint("NewApi")
	private void initSortList() {
		adapter = new SortLeftAdapter(getActivity(),
				GoodsKinds.goodsKindsList);
		left_listView.setAdapter(adapter);

		for (int i = 0; i < GoodsKinds.goodsKindsList.size(); i++) {
			sortRightList.add(new SortRightFragment(GoodsKinds.goodsKindsList
					.get(i).getChildList()));
		}

		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.right_list, sortRightList.get(0));
		fragmentTransaction.commit();

		left_listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				lastLeftSelectView.setBackgroundResource(R.color.white);
				TextView lasttv = (TextView) lastLeftSelectView
						.findViewById(R.id.title_side);
//				lasttv.setTextColor(Color.parseColor("#000000"));
				lastLeftSelectPosition = position;
				adapter.notifyDataSetChanged();
//				view.setBackgroundResource(R.color.sort_grey);
				TextView tv = (TextView) view.findViewById(R.id.title_side);
//				tv.setTextColor(Color.parseColor("#FF6666"));
//				tv.setBackgroundColor(Color.parseColor("white"));
//				tv.setBackgroundColor(Color.WHITE);
				lastLeftSelectView = view;
//				lastLeftSelectView.setBackgroundColor(Color.WHITE);
				FragmentTransaction fragmentTransaction = getChildFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.right_list,
						sortRightList.get(position));
				fragmentTransaction.commit();
				scrollPosition = position;
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						left_listView.smoothScrollToPositionFromTop(
								scrollPosition, 0);
					}
				}, 200);
			}
		});
		left_listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
	}

	@Override
	protected void bindEvent() {

	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getGoodsCats.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				new GoodsKinds(jsonobj.getJSONArray("data"));
				initSortList();
			} catch (JSONException e) {
			}
		}
	}

	private class SortLeftAdapter extends MyBaseAdapter<GoodsKindsId> {

		public SortLeftAdapter(Context context, List<GoodsKindsId> list) {
			super(context, list);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.item_sort_left, null);
				holder = new ViewHolder();
				holder.title_side = (TextView) convertView
						.findViewById(R.id.title_side);
				holder.flagView = (ImageView) convertView.findViewById(R.id.list_flag);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			GoodsKindsId goodsKindsId = getItem(position);
			holder.title_side
					.setText(goodsKindsId.getName().replace("、", "\n"));

			// 复用的时候，确定颜色
//			if (lastLeftSelectPosition == position) {
//				 convertView.setBackgroundResource(R.color.sort_grey);
//				holder.title_side.setTextColor(Color.parseColor("#FF6666"));
//			} else {
//				 convertView.setBackgroundResource(R.color.white);
//				holder.title_side.setTextColor(Color.parseColor("#000000"));
//			}

			// 初始化lastLeftSelectView
			if (lastLeftSelectView == null
					&& position == lastLeftSelectPosition) {
				lastLeftSelectView = convertView;
			}
			if (lastLeftSelectPosition == position) {
				holder.flagView.setVisibility(View.VISIBLE);
			}else {
				holder.flagView.setVisibility(View.GONE);
			}

			return convertView;
		}

		private class ViewHolder {
			TextView title_side;
			ImageView flagView;
		}

	}

}
