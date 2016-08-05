package com.wstmall.fragment.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.adapter.AdressAdapter;
import com.wstmall.api.GetCitys;
import com.wstmall.api.useradress.GetUserAddress;
import com.wstmall.api.useradress.districts.GetDistricts;
import com.wstmall.application.Const;
import com.wstmall.bean.AdressDistrictsbean;
import com.wstmall.domain.ShippingAdressInfo;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

@FragmentView(id = R.layout.shipping_adress_list)
public class UserAdressListDistrictFragment extends BaseFragment implements
		View.OnClickListener {
	private static final String TAG = "UserAdressListDistrictFragment";
	@InjectView(id = R.id.lv_adress)
	private ListView lv_adress;
	private AdressAdapter adressadapter;
	public  int flag;
	public static int index;
	private int length;
	public static HashMap<String, List<String>> citytodistrict;
	public static String cityname;
    public static String allarea;
    private List<AdressDistrictsbean> districtslist;   
    
    private GetDistricts getdistricts;
    public static int backstackflag;
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getdistricts.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						AdressDistrictsbean Bean = new Gson()
								.fromJson(jsonArray.getJSONObject(i).toString(), AdressDistrictsbean.class);
						districtslist.add(Bean);
					}
					adressadapter = new AdressAdapter(districtslist,getActivity(),districtslist.size(),1,1);
					lv_adress.setAdapter(adressadapter);
				}
			} catch (JSONException e) {
				
			}
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("我的区县");
		tWidget.setLeftVisibility("visible");
		getdistricts=new GetDistricts();
		districtslist=new ArrayList<AdressDistrictsbean>();
		getdistricts.areaId2=Const.cache.city.getCityid();
		request(getdistricts);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		lv_adress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub		
				
				((BaseActivity) getActivity()).replaceFragment(new UserAdressListCommunitysFragment(districtslist.get(position).getAreaId(),districtslist.get(position).getAreaName()), true);

			}
		});
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(backstackflag==1){
			backstackflag=0;
			FragmentManager fm = getFragmentManager();  
	    	 FragmentTransaction transaction = fm.beginTransaction(); 	    	
			 transaction.remove(this);	
			 transaction.commit();
			 getActivity().getFragmentManager().popBackStack();
			 
		}
	}

}
