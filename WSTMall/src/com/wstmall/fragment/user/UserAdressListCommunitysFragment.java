package com.wstmall.fragment.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.adapter.AdressAdapter;
import com.wstmall.api.useradress.community.GetCommunitys;
import com.wstmall.bean.AdressCommunitysbean;
import com.wstmall.bean.GoodsListBean;
import com.wstmall.domain.ShippingAdressInfo;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.service.AdressService;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

@FragmentView(id = R.layout.shipping_adress_list)
public class UserAdressListCommunitysFragment extends BaseFragment implements
		View.OnClickListener {
	@InjectView(id = R.id.lv_adress)
	private ListView lv_adress;
	private AdressAdapter adressadapter;
	public  int flag;
	public static int index;
	public static String cityname;
    public static String allarea;
    public static String areaId3;
    public static String communityId;
    private GetCommunitys getcommunitys;
    private List<AdressCommunitysbean> adresscommunitylist;
    private String districtsname;
    public static String districtsandcommunity;
    
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getcommunitys.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						AdressCommunitysbean Bean = new Gson()
								.fromJson(jsonArray.getJSONObject(i).toString(), AdressCommunitysbean.class);
						adresscommunitylist.add(Bean);
					}
					
					adressadapter = new AdressAdapter(adresscommunitylist,getActivity(),adresscommunitylist.size(),2);
					lv_adress.setAdapter(adressadapter);
					adressadapter.notifyDataSetChanged();
				}
			} catch (JSONException e) {
				
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	public UserAdressListCommunitysFragment() {

	}
	public UserAdressListCommunitysFragment(String areaId3,String districtsname) {
		this.areaId3=areaId3;
		this.districtsname=districtsname;
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("我的社区");
		tWidget.setLeftVisibility("visible");
		getcommunitys=new GetCommunitys();
		adresscommunitylist=new ArrayList<AdressCommunitysbean>();
		getcommunitys.areaId3=areaId3;
		request(getcommunitys);
	
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		lv_adress.setOnItemClickListener(new itemclick(this) );
	}
	class itemclick implements OnItemClickListener{
		
		private Fragment fragment;
		public itemclick(Fragment fragment){
			this.fragment=fragment;
		}
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			communityId=adresscommunitylist.get(position).getCommunityId();
			UserAdressListDistrictFragment.backstackflag=1;
			districtsandcommunity=districtsname+adresscommunitylist.get(position).getCommunityName();
			FragmentManager fm = getFragmentManager();  
	    	 FragmentTransaction transaction = fm.beginTransaction(); 	    	
			 transaction.remove(fragment);	
			 transaction.commit();
			 getActivity().getFragmentManager().popBackStack();
		}
		
	}
}
