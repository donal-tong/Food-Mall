package com.wstmall.activity.order;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.order.OrderDetailFragment;


public class OrderDetailActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new OrderDetailFragment(), false);
	}
}
