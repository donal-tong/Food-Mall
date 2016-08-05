package com.wstmall.activity.order;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.brands.BrandsFragment;
import com.wstmall.fragment.business.NearbyBusinessFragment;
import com.wstmall.fragment.order.EvaluationFragment;
import com.wstmall.fragment.order.OrderManagement;


public class AddAppraisesActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new EvaluationFragment(getIntent().getIntExtra("orderId", 0)), false);
	}

}
