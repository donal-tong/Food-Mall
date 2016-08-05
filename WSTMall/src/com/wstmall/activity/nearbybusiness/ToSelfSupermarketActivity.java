
package com.wstmall.activity.nearbybusiness;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.business.BusinessHomeFragment;

public class ToSelfSupermarketActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new BusinessHomeFragment(BusinessHomeFragment.FromSelfSupermarket), false);
	}
}
