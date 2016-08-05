package com.wstmall.fragment.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.adapter.UserAdressAdapter;
import com.wstmall.api.useradress.GetUserAddress;
import com.wstmall.api.useradress.editadress.EditAddress;
import com.wstmall.application.Const;
import com.wstmall.bean.UserAdressBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.order_goods.OrderForGoodsFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

@FragmentView(id = R.layout.mine_adress)
public class ShippingAdressFragment extends BaseFragment {
	@InjectView(id = R.id.lv_mine_adress)
	private ListView lv_mine_adress;
	private GetUserAddress getuseraddress;
	public static List<UserAdressBean> useradressbeanlist;
	private UserAdressAdapter useradressadapter;

	private final static int CHOICE_ADRESS = 0;
	private final static int CHANGE_ADRESS = 1;
	private int howAdress;

	public ShippingAdressFragment(int howAdress) {
		this.howAdress = howAdress;
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(getuseraddress.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(data);
				if (jsonobj.has("data")) {
					JSONArray jsonArray = jsonobj.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						UserAdressBean Bean = new Gson().fromJson(jsonArray
								.getJSONObject(i).toString(),
								UserAdressBean.class);
						useradressbeanlist.add(Bean);
					}
					useradressadapter = new UserAdressAdapter(
							useradressbeanlist, getActivity());
					lv_mine_adress.setAdapter(useradressadapter);
				}
			} catch (JSONException e) {

			}
			finally{
				if(useradressbeanlist.size()!=0){
					 EditAddress editadress = new EditAddress();
					 editadress.tokenId=Const.cache.getTokenId();
					 editadress.id=useradressbeanlist.get(0).addressId;
					 editadress.userName=useradressbeanlist.get(0).userName;
					 editadress.areaId1=useradressbeanlist.get(0).getAreaId1();
					 editadress.areaId2=useradressbeanlist.get(0).getAreaId2();
					 editadress.areaId3=useradressbeanlist.get(0).getAreaId3();
					 editadress.isDefault="1";
					 editadress.communityId=useradressbeanlist.get(0).getCommunityId();
					 editadress.userPhone=useradressbeanlist.get(0).getUserPhone();
					 editadress.userTel=useradressbeanlist.get(0).userTel;
					 editadress.address=useradressbeanlist.get(0).address;
					 requestNoDialog(editadress);
				}
			}
		}
	}

	@Override
	public void rightClick() {
		replaceFragment(new ModifyAddAdressFragment("add"), true);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("我的地址");
		tWidget.right.setVisibility(View.VISIBLE);
		useradressbeanlist = new ArrayList<UserAdressBean>();
		getuseraddress = new GetUserAddress();
		getuseraddress.tokenId = Const.cache.getTokenId();
		request(getuseraddress);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub

		lv_mine_adress.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (howAdress == CHANGE_ADRESS) {
					((BaseActivity) getActivity()).replaceFragment(
							new ModifyAddAdressFragment(useradressbeanlist
									.get(position), useradressbeanlist.get(
									position).getAddressId(), "change"), true);
				} else {
					OrderForGoodsFragment.addressPosition=position;
					OrderForGoodsFragment.isChangeadr=true;
					getActivity().getFragmentManager().popBackStack();
				}
			}
		});
	}

}
