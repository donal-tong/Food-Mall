
package com.wstmall.activity.search;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.search.SearchFragment;

public class SearchActivity extends BaseActivity{
	
	public static final int sign= 52;
	public static final String SearchTarget_String = "SEARCHTARGET_STRING";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		replaceFragment(new SearchFragment(), false);
	}
	
}
