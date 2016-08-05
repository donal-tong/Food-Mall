
package com.wstmall.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;


public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected List<T> modelList;
	protected Context mContext;
	DecimalFormat df0 = new DecimalFormat("0.0");
	DecimalFormat df00 = new DecimalFormat("0.00");

	public MyBaseAdapter(Context context, List<T> list) {
		this.mContext = context;
		this.modelList = list;
	}

	@Override
	public int getCount() {
		return modelList.size();
	}

	public List<T> getModelList() {
		return modelList;
	}

	@Override
	public T getItem(int position) {
		return modelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
