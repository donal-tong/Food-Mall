
package com.wstmall.activity.shoppingCart;

import android.os.Bundle;
import android.view.KeyEvent;

import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.fragment.mainPage.MainPageFragment;
import com.wstmall.fragment.shoppingCart.ShoppingCartFragment;

public class ShoppingCartActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new ShoppingCartFragment(), false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	if(whatFragmentTopNow().equals("com.wstmall.fragment.shoppingCart.ShoppingCartFragment")){
	    		MainActivity.mHost.setCurrentTab(0);
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
