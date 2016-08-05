package com.wstmall.widget;

import com.zhy_9.food_test.R;
import com.wstmall.application.SortFiled;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ShopsSortPopWindows extends PopupWindow {
	private View myView;
	private ListView lv_attrs;
	private AttrsSelectInf attrsSelectInf;
	private TextView tv_done;

	public ShopsSortPopWindows(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myView = inflater.inflate(R.layout.goods_sort_select_pop, null);
		lv_attrs = (ListView) myView.findViewById(R.id.lv_goods_sort);
		tv_done=(TextView) myView.findViewById(R.id.tv_done);
		SortAdapter attrsAdapter=new SortAdapter(context);
		lv_attrs.setAdapter(attrsAdapter);
		setOnClickitem();
		this.setContentView(myView);
		this.setWidth((int) (SortFiled.width*5/6));
		this.setHeight(LayoutParams.MATCH_PARENT);
		//this.setHeight(getLvHeight(lv_attrs));
		this.setAnimationStyle(R.style.sort_popwindow_anim_style);
		this.setFocusable(true);
		//ColorDrawable dw = new ColorDrawable(R.color.white);
		//this.setBackgroundDrawable(dw);
		tv_done.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
	private void setOnClickitem(){
		lv_attrs.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				SortFiled.currentSortItem=position;
				attrsSelectInf.AttrsSelect(position);
			}
		});
	}
	public ListView getListview(){
		return lv_attrs;
	}
	public void setAttrsSelectListener(AttrsSelectInf attrsSelectInf){
		this.attrsSelectInf=attrsSelectInf;
	}
	public interface AttrsSelectInf{
		void AttrsSelect(int position);
	}
	private class SortAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private Context context;
		public SortAdapter(Context context){
			this.context=context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return SortFiled.getShopsSortFiled().size();
			
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.goods_sort_type_item, null);
			TextView tv_attrs = (TextView) convertView
					.findViewById(R.id.tv_pay_type);
			tv_attrs.setText(""+SortFiled.getShopsSortFiled().get(position));
			Log.e("xxxxxxxx", ""+SortFiled.getShopsSortFiled().get(position));
			CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.sort_select);
			if(SortFiled.currentSortItem==position){
				checkBox.setChecked(true);
				tv_attrs.setTextColor(Color.parseColor("#F6772A"));
			}
			return convertView;
		}

	}
	private  int getLvHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
      //  params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
      //  listView.setLayoutParams(params);
    }
}
