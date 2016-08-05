
package com.wstmall.activity.goods;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.goods.GoodsFragment;

public class GoodsActivity extends BaseActivity{
	public static final String goodsID="GOODSID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new GoodsFragment(getIntent().getStringExtra(goodsID)), false);
	}
	
}
