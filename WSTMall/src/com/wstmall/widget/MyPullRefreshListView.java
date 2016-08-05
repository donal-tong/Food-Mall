
package com.wstmall.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class MyPullRefreshListView extends PullToRefreshListView{

	public MyPullRefreshListView(Context context) {
		super(context);
		initCustomFeatures();
	}

	public MyPullRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initCustomFeatures();
	}

	public MyPullRefreshListView(Context context, Mode mode) {
		super(context, mode);
		initCustomFeatures();
	}

	public MyPullRefreshListView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
		initCustomFeatures();
	}
	
	public void initCustomFeatures(){
		getHeaderLayout().setPullLabel("下拉刷新");
		getHeaderLayout().setReleaseLabel("松开进行刷新");

		getFooterLayout().setPullLabel("上拔加载");
		getFooterLayout().setReleaseLabel("松开加载更多");
	}

}
