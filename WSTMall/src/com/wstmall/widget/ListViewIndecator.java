package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewIndecator extends RelativeLayout {
	private TextView tv_fz;
	private TextView tv_fm;
	private RelativeLayout myview;

	public ListViewIndecator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myview = (RelativeLayout) inflater.inflate(R.layout.listview_indicator,
				this);
		tv_fz = (TextView) myview.findViewById(R.id.tv_fz);
		tv_fm = (TextView) myview.findViewById(R.id.tv_fm);
	}

	public void setFz(String fz) {
		tv_fz.setText(fz);
	}

	public void setFm(String fm) {
		tv_fm.setText(fm);
	}
}
