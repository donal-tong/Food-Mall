
package com.wstmall.fragment.login;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.EditTextBar;

@FragmentView(id = R.layout.fragment_phone_register)
public class PhoneRegisterFragment extends BaseFragment implements View.OnClickListener {
	@InjectView(id = R.id.bt_submit_num)
	private Button bt_submit_num;
	
	@InjectView(id = R.id.et_phone_num)
	private EditTextBar et_phone_num;

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("手机注册");
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		bt_submit_num.setOnClickListener(this);
	}

/*	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(register.getA())) {
			try {
				Toast.makeText(getActivity(), "注册成功!", Toast.LENGTH_SHORT);
				JSONObject jsonobj = new JSONObject(data);
				Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
				jsonobj=new JSONObject(jsonobj.get("data").toString());
				Const.cache.setTokenId(jsonobj.getString("tokenId"));
				Const.isLogin=true;
				MineActivity.autoToMine=true;
				leftClick();
			} catch (JSONException e) {

			}
		}
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_submit_num:
			if(et_phone_num.getText().toString().length()!=11){
				Toast.makeText(getActivity(), "请确定手机号码为11位", Toast.LENGTH_LONG).show();
			}else{
				replaceFragment(new PhoneIdFragment(), true);
			}
			break;
		}
		}

}
