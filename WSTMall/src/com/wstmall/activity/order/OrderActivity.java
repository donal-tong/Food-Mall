package com.wstmall.activity.order;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.brands.BrandsFragment;
import com.wstmall.fragment.business.NearbyBusinessFragment;
import com.wstmall.fragment.order.OrderManagement;


public class OrderActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new OrderManagement(getIntent().getIntExtra("orderType", 0)), false);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		replaceFragment(new OrderManagement(), false);
	}
	
}
