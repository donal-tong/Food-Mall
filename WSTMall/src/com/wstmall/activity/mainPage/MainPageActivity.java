
package com.wstmall.activity.mainPage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.WSTMallApplication;
import com.wstmall.fragment.mainPage.MainPageFragment;

public class MainPageActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new MainPageFragment(), false);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
	      //do something here
	    	confirQuit();
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	private void confirQuit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("确定退出"+getResources().getString(R.string.app_name)+"吗？").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				((WSTMallApplication) getApplication()).exit();
				System.exit(0);
			}
		}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		// Create the AlertDialog object and return it
		builder.create().show();
	}
	
}
