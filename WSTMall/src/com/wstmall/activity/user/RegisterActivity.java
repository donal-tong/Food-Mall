
package com.wstmall.activity.user;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.login.LoginFragment;
import com.wstmall.fragment.login.RegisterFragment;

public class RegisterActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new RegisterFragment(), false);
	}
	
}
