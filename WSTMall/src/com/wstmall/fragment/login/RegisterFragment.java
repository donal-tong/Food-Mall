
package com.wstmall.fragment.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.user.LoginActivity;
import com.wstmall.activity.user.MineActivity;
import com.wstmall.api.login.Login;
import com.wstmall.api.login.Register;
import com.wstmall.application.Const;
import com.wstmall.bean.User;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.mainPage.MainPageFragment;
import com.wstmall.fragment.user.MineFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.ClearEditText;


@FragmentView(id = R.layout.register_new_fragment)
public class RegisterFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.fragment_register_register)
	View registButton;
	@InjectView(id = R.id.fragment_register_name)
	EditText nameEditText;
	@InjectView(id = R.id.fragment_register_psw)
	EditText pswEditText;
	@InjectView(id = R.id.fragment_register_psw_again)
	ClearEditText pswAgainEditText;
	@InjectView(id = R.id.back_to_home_register)
	TextView backTo;
	@InjectView(id = R.id.fragment_login_forget)
	TextView toLogin;

	private Register register = new Register();

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		registButton.setOnClickListener(this);
		tWidget.setVisibility(View.GONE);
		backTo.setOnClickListener(this);
		toLogin.setOnClickListener(this);
	}

	@Override
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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_register_register:
			String nameStr = nameEditText.getText().toString();
			String pswStr = pswEditText.getText().toString();
			String pswaStr = pswAgainEditText.getText().toString();
			if (TextUtils.isEmpty(nameStr)) {
				Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(pswStr)) {
				Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(pswaStr)) {
				Toast.makeText(getActivity(), "请在输入一次密码", Toast.LENGTH_SHORT).show();
			} else if (!pswaStr.equals(pswStr)) {
				Toast.makeText(getActivity(), "两次输入密码必须一样", Toast.LENGTH_SHORT).show();
			} else {
				String registerKeyString = Base64.encodeToString(
						(Base64.encodeToString(nameStr.getBytes(), Base64.URL_SAFE) + "_" + Base64.encodeToString(
								pswStr.getBytes(), Base64.URL_SAFE)).getBytes(), Base64.NO_WRAP);
				register.registerKey = registerKeyString;
				request(register);
			}
			break;
		case R.id.back_to_home_register:
			MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
            MainActivity.mHost.setCurrentTab(0);
			Intent backIntent = new Intent(getActivity(), MainActivity.class);
			Log.e("回到首页", "register_back_to_home");
			startActivity(backIntent);
			break;
		case R.id.fragment_login_forget:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			Log.e("立即登陆", "register_back_to_home");
			startActivity(intent);
			break;
		}
	}

}
