package com.wstmall.adapter;

import java.util.List;

import com.zhy_9.food_test.R;
import com.wstmall.bean.UserAdressBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserAdressAdapter extends BaseAdapter {
	private List<UserAdressBean> useradressbeanlist;
	private Context context;
	
	public UserAdressAdapter(List<UserAdressBean> useradressbeanlist,
			Context context) {
		super();
		this.useradressbeanlist = useradressbeanlist;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return useradressbeanlist.size();
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
		View view=View.inflate(context, R.layout.mine_adress_item, null);
		
		TextView tv_person_name= (TextView) view.findViewById(R.id.tv_person_name);
		tv_person_name.setText(useradressbeanlist.get(position).getUserName());
		
		TextView tv_cellphone= (TextView) view.findViewById(R.id.tv_cellphone);
		tv_cellphone.setText(useradressbeanlist.get(position).getUserPhone());
		
		TextView tv_telephone= (TextView) view.findViewById(R.id.tv_telephone);
		tv_telephone.setText(useradressbeanlist.get(position).getUserTel());
		
		TextView tv_area= (TextView) view.findViewById(R.id.tv_area);
//		tv_area.setText(useradressbeanlist.get(position).getAreaName());
		
		TextView tv_adress= (TextView) view.findViewById(R.id.tv_adress);
		tv_adress.setText(useradressbeanlist.get(position).getAddress());
		if(useradressbeanlist.get(position).getIsDefault().equals("1")){
		TextView tv_default= (TextView) view.findViewById(R.id.tv_default);
		tv_default.setText("[默认]");
		}
		return view;
	}

}
