package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class KaterinaRefreshListview extends LinearLayout {
	private LinearLayout mainView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView lv_message;

	private KateOnRefreshListener kateOnRefreshListener;

	public KaterinaRefreshListview(Context context) {
		super(context);
		initView();
		setListener();
	}

	public KaterinaRefreshListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
		setListener();
	}

	public KaterinaRefreshListview(Context context, AttributeSet attrs,
			int desfaultStye) {
		super(context, attrs, desfaultStye);
		initView();
		setListener();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mainView = (LinearLayout) inflater.inflate(R.layout.sw_listview, this);
		lv_message = (ListView) mainView.findViewById(R.id.lv_message);
		swipeRefreshLayout = (SwipeRefreshLayout) mainView
				.findViewById(R.id.swipe_container);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_red_light, android.R.color.holo_red_light,
				android.R.color.holo_red_light, android.R.color.holo_red_light);
	}

	public ListView getListView() {
		return lv_message;
	}

	private void setListener() {
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				kateOnRefreshListener.reRresh();
			}
		});
		
	}

	public void setAdapter(ListAdapter baseAdapter) {
		lv_message.setAdapter(baseAdapter);
	}

	public void OnRefreshFinsh() {
		swipeRefreshLayout.setRefreshing(false);
	}

	public void setOnRefresh(KateOnRefreshListener kateOnRefreshListener) {
		this.kateOnRefreshListener = kateOnRefreshListener;
	}
	public interface KateOnRefreshListener {
		void reRresh();
	}
}
