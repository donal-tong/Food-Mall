package com.wstmall.activity.brands;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.brands.BrandsFragment;
import com.wstmall.fragment.business.NearbyBusinessFragment;


public class BrandsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new BrandsFragment(), false);
	}
}
