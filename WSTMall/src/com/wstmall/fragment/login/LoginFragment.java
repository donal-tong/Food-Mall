
package com.wstmall.fragment.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.mainPage.MainPageActivity;
import com.wstmall.activity.user.MineActivity;
import com.wstmall.activity.user.RegisterActivity;
import com.wstmall.api.login.Login;
import com.wstmall.application.Const;
import com.wstmall.bean.User;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.mainPage.MainPageFragment;
import com.wstmall.fragment.user.EditUserInfoFragment;
import com.wstmall.fragment.user.MineFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.zbar.lib.decode.FinishListener;


@FragmentView(id = R.layout.login_new_fragment)
public class LoginFragment extends BaseFragment implements View.OnClickListener {

	public final static String fromMainActivity = "FromMainActivity";

	@InjectView(id = R.id.fragment_login_regist)
	View registButton;
	@InjectView(id = R.id.fragment_login_name)
	EditText nameEditText;
	@InjectView(id = R.id.fragment_login_psw)
	EditText pswEditText;
	@InjectView(id = R.id.fragment_login_login)
	Button loginButton;
	@InjectView(id = R.id.fragment_login_forget)
	View forgetButton;
	@InjectView(id = R.id.tv_phone_register)
	private TextView tv_phone_register;
	@InjectView(id = R.id.back_to_home)
	private TextView backTo;
	
	private boolean isFromMainActivity = false;

	private Login login = new Login();

	public LoginFragment(String mode) {
		if (mode.equals(fromMainActivity)) {
			isFromMainActivity = true;
		}
	}

	public LoginFragment() {

	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!isFromMainActivity&&Const.isLogin){
			leftClick();
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
//		if (isFromMainActivity) {
//			tWidget.getLeftView().setVisibility(View.INVISIBLE);
//		}
		tWidget.setVisibility(View.GONE);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		registButton.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		forgetButton.setOnClickListener(this);
		backTo.setOnClickListener(this);
		//tv_phone_register.setOnClickListener(this);
//		tv_phone_register.setVisibility(View.GONE);
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(login.getA())) {
			try {
				Toast.makeText(getActivity(), "登录成功!", Toast.LENGTH_SHORT);
				JSONObject jsonobj = new JSONObject(data);
				Const.user = gson.fromJson(jsonobj.get("data").toString(), User.class);
				jsonobj = new JSONObject(jsonobj.get("data").toString());
				Const.cache.setTokenId(jsonobj.getString("tokenId"));
				Const.isLogin = true;
				MineActivity.autoToMine=true;
				if(isFromMainActivity){
				((BaseActivity) getActivity()).replaceFragment(new MineFragment(), false);
				}else{
					leftClick();
				}
			} catch (JSONException e) {

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_login_regist:
			Intent intent = new Intent(getActivity(), RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.fragment_login_login:
			String nameStr = nameEditText.getText().toString();
			String pswStr = pswEditText.getText().toString();
			if (TextUtils.isEmpty(nameStr)) {
				Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			} else if (TextUtils.isEmpty(pswStr)) {
				Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
			} else {
				login.loginKey = Base64.encodeToString((Base64.encodeToString(nameStr.getBytes(), Base64.URL_SAFE)
						+ "_" + Base64.encodeToString(pswStr.getBytes(), Base64.URL_SAFE)).getBytes(), Base64.NO_WRAP);
				request(login);
			}
			break;
		case R.id.fragment_login_forget:
			/*
			 * Intent intent = new Intent(getActivity(), MainActivity.class);
			 * getActivity().startActivity(intent);
			 */
			break;
			
		case R.id.back_to_home:
			MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
            MainActivity.mHost.setCurrentTab(0);
			break;
	/*	case R.id.tv_phone_register:
			replaceFragment(new PhoneRegisterFragment(),true);
		break;*/
		}
	}

}
