package com.wstmall.activity.widget;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.widget.HtmlViewFragment;

import android.os.Bundle;

public class HtmlViewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String title = getIntent().getStringExtra("title");
		String Url  = getIntent().getStringExtra("Url");
		HtmlViewFragment htmlViewFragment = new HtmlViewFragment(Url,title);
		replaceFragment(htmlViewFragment, false);
	}	
}