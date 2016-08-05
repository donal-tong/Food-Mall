
package com.wstmall.activity.sort;

import android.os.Bundle;
import android.view.KeyEvent;

import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.fragment.sort.SortFragment;

public class SortActivity  extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new SortFragment(), false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	if(whatFragmentTopNow().equals("com.wstmall.fragment.sort.SortFragment")){
	    		MainActivity.mHost.setCurrentTab(0);
	    		return true;
	    	}
	    	return super.onKeyDown(keyCode, event);
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
