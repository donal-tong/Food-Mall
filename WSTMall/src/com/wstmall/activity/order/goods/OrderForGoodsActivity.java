package com.wstmall.activity.order.goods;
import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.brands.BrandsFragment;
import com.wstmall.fragment.business.NearbyBusinessFragment;
import com.wstmall.fragment.order_goods.OrderForGoodsFragment;


public class OrderForGoodsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		double totalprice=getIntent().getDoubleExtra("totalprice",0);
		replaceFragment(new OrderForGoodsFragment(), false);
	}
}
