
package com.wstmall.activity.shoppingCart;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.shoppingCart.ShoppingCartFragment;

public class ShoppingCartWithoutMainpageActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new ShoppingCartFragment(), false);
	}

}
