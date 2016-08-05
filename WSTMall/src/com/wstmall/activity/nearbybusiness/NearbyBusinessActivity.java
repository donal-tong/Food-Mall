package com.wstmall.activity.nearbybusiness;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.business.NearbyBusinessFragment;


public class NearbyBusinessActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new NearbyBusinessFragment(), false);
	}
}
