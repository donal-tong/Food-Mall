package com.wstmall.adapter;

import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.bean.AdressCommunitysbean;
import com.wstmall.bean.AdressDistrictsbean;
import com.wstmall.domain.ShippingAdressInfo;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdressAdapter extends BaseAdapter {
	private List<AdressDistrictsbean> districtslist;
	private List<AdressCommunitysbean> adresscommunitylist;
	private Context context;
	private int count;
	private int flag;
	
	public AdressAdapter(List<AdressCommunitysbean> adresscommunitylist,Context context,int count,int flag) {		
		this.adresscommunitylist = adresscommunitylist;
		this.context=context;
		this.count=count;
		this.flag=flag;
	}

	public AdressAdapter(List<AdressDistrictsbean> districtslist, Context context,int count,int flag,int n) {		
		this.districtslist = districtslist;
		this.context = context;
		this.flag = flag;
		this.count=count;		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
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

		View view = View.inflate(context, R.layout.shipping_adress_item, null);
		TextView tv_adress = (TextView) view
				.findViewById(R.id.tv_shippingadress);
		switch (flag) {
		case 1:
			AdressDistrictsbean districts = districtslist.get(position);
			tv_adress.setText(districts.getAreaName());
			break;
		case 2:
			AdressCommunitysbean	communitys = adresscommunitylist.get(position);
			tv_adress.setText(communitys.getCommunityName());
			break;						
		}
		return view;
	}

}
