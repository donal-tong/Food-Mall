
package com.wstmall.activity.user;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.login.LoginFragment;

public class LoginActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new LoginFragment(), false);
	}
	
}
