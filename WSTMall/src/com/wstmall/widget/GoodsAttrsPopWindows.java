package com.wstmall.widget;

import com.zhy_9.food_test.R;
import com.wstmall.bean.GoodsListBean;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class GoodsAttrsPopWindows extends PopupWindow {
	private View myView;
	private ListView lv_attrs;
	private AttrsSelectInf attrsSelectInf;

	public GoodsAttrsPopWindows(Context context,GoodsListBean goodlist) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		myView = inflater.inflate(R.layout.goodattrs_select_pop, null);
		lv_attrs = (ListView) myView.findViewById(R.id.lv_attrs);
		AttrsAdapter attrsAdapter=new AttrsAdapter(context,goodlist);
		lv_attrs.setAdapter(attrsAdapter);
		setOnClickitem();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int height = wm.getDefaultDisplay().getHeight();
		this.setContentView(myView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(getLvHeight(lv_attrs));
		this.setAnimationStyle(R.style.time_popwindow_anim_style);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
	}
	private void setOnClickitem(){
		lv_attrs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
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
	private class AttrsAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private Context context;
		private GoodsListBean goodlist;
		public AttrsAdapter(Context context,GoodsListBean goodlist){
			this.context=context;
			this.goodlist=goodlist;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return goodlist.priceAttrs.size();
			
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
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.attrs_type_item, null);
			TextView tv_attrs = (TextView) convertView
					.findViewById(R.id.tv_pay_type);
			tv_attrs.setText(""+goodlist.priceAttrs.get(position).attrVal);

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
