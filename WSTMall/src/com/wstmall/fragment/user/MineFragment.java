
package com.wstmall.fragment.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.order.OrderActivity;
import com.wstmall.api.order.GetOrdersStatus;
import com.wstmall.application.Const;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.login.LoginFragment;
import com.wstmall.fragment.order.OrderManagement;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;


@FragmentView(id = R.layout.fragment_personal_center)
public class MineFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.ib_personimg)
	private ImageView ib_personimg;
	
	@InjectView(id = R.id.tv_person_points)
	private TextView tv_person_points;
	
	@InjectView(id = R.id.tv_person_name)
	private TextView tv_person_name;
	
	@InjectView(id = R.id.person_order)
	private RelativeLayout person_order;
	
	@InjectView(id = R.id.person_message)
	private RelativeLayout person_message;
	
	@InjectView(id = R.id.person_info)
	private RelativeLayout person_info;
	
	@InjectView(id = R.id.person_shippingaddress)
	private RelativeLayout person_shippingaddress;
	
	@InjectView(id = R.id.person_safty)
	private RelativeLayout person_safty;
	
	//订单显示
	@InjectView(id = R.id.ll_way_pay)
	private LinearLayout ll_way_pay;
	
	@InjectView(id = R.id.ll_way_accept)
	private LinearLayout ll_way_accept;
	
	@InjectView(id = R.id.ll_way_receive)
	private LinearLayout ll_way_receive;
	
	@InjectView(id = R.id.ll_way_eva)
	private LinearLayout ll_way_eva;
	
	@InjectView(id = R.id.tv_way_pay)
	private TextView tv_way_pay;
	
	@InjectView(id = R.id.tv_way_accept)
	private TextView tv_way_accept;
	
	@InjectView(id = R.id.tv_way_receive)
	private TextView tv_way_receive;
	
	@InjectView(id = R.id.tv_way_eva)
	private TextView tv_way_eva;
	
	private GetOrdersStatus getOrdersStatus=new GetOrdersStatus();
	
	protected void requestSuccess(String url, String data) {
		if (url.contains(getOrdersStatus.getA())) {
			try {
				JSONObject jsonreobj = new JSONObject(data);
				JSONObject js=jsonreobj.getJSONObject("data");
				tv_way_pay.setText(js.getString("-2"));
				tv_way_accept.setText(js.getString("0"));
				tv_way_receive.setText(js.getString("3"));
				tv_way_eva.setText(js.getString("4"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.person_order:
			Intent orderintent=new Intent(getActivity(), OrderActivity.class);
			startActivity(orderintent);
			break;
		case R.id.person_message:

			break;
		case R.id.person_info:
			MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
            replaceFragment(new EditUserInfoFragment(), true);
			break;
		case R.id.person_shippingaddress:
			MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
			replaceFragment(new ShippingAdressFragment(1), true);
			break;
		case R.id.person_safty:
			MainActivity.mHost.getTabWidget().setVisibility(View.GONE);
			replaceFragment(new AccountSecurityFragment(),true);
			break;
		case R.id.ll_way_pay:
			Intent payIntent=new Intent(getActivity(), OrderActivity.class);
			payIntent.putExtra("orderType",1);
			startActivity(payIntent);
			break;
		case R.id.ll_way_accept:
			Intent acIntent=new Intent(getActivity(), OrderActivity.class);
			acIntent.putExtra("orderType",2);
			startActivity(acIntent);
			break;
		case R.id.ll_way_receive:
			Intent reIntent=new Intent(getActivity(), OrderActivity.class);
			reIntent.putExtra("orderType", 3);
			startActivity(reIntent);
			break;
		case R.id.ll_way_eva:
			Intent evaIntent=new Intent(getActivity(), OrderActivity.class);
			evaIntent.putExtra("orderType", 4);
			startActivity(evaIntent);
			break;
		}
	}
	private void initOrderTips(){
		ll_way_pay.getBackground().setAlpha(100);
		ll_way_accept.getBackground().setAlpha(100);
		ll_way_receive.getBackground().setAlpha(100);
		ll_way_eva.getBackground().setAlpha(100);
		ll_way_pay.setOnClickListener(this);
		ll_way_accept.setOnClickListener(this);
		ll_way_receive.setOnClickListener(this);
		ll_way_eva.setOnClickListener(this);
	}
	private void confirLogout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("确定退出登录？").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((BaseActivity)getActivity()).reLogin();
				replaceFragment(new LoginFragment(LoginFragment.fromMainActivity), false);
			}
		}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		// Create the AlertDialog object and return it
		builder.create().show();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
		requestNoDialog(getOrdersStatus);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("会员中心");
		tWidget.setRightBtnText("注销");
		tWidget.setTitleAlpha(0);
		tWidget.right.setVisibility(View.VISIBLE);
		tv_person_name.setText(Const.user.getName());	
		tv_person_points.setText(Const.user.userScore);
		if(Const.user.userPhoto!=null&&!Const.user.userPhoto.equals("")){
			loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, ib_personimg);
		}
		getOrdersStatus.tokenId=Const.cache.getTokenId();
		requestNoDialog(getOrdersStatus);
		initOrderTips();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		person_order.setOnClickListener(this);
		person_message.setOnClickListener(this);
		person_info.setOnClickListener(this);
		person_shippingaddress.setOnClickListener(this);
		person_safty.setOnClickListener(this);
	}
	@Override
	public void rightClick() {
		confirLogout();
	}
}
