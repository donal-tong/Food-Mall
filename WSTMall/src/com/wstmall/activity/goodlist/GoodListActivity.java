package com.wstmall.activity.goodlist;

import android.os.Bundle;

import com.wstmall.activity.BaseActivity;
import com.wstmall.fragment.goods.GoodsListFragment;

public class GoodListActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().hasExtra(GoodsListFragment.SearchTarget)) {
			replaceFragment(new GoodsListFragment(getIntent().getStringExtra(GoodsListFragment.SearchTarget)), false);
		}else if(getIntent().hasExtra(GoodsListFragment.Mode_GoodsCatIdOne)){
			replaceFragment(new GoodsListFragment(getIntent().getIntExtra(GoodsListFragment.Mode_GoodsCatIdOne,47),GoodsListFragment.Mode_GoodsCatIdOne), false);
		} else if(getIntent().hasExtra(GoodsListFragment.Mode_GoodsCatIdSecond)){
			replaceFragment(new GoodsListFragment(getIntent().getIntExtra(GoodsListFragment.Mode_GoodsCatIdSecond,47),GoodsListFragment.Mode_GoodsCatIdSecond), false);
		} else {
			replaceFragment(new GoodsListFragment(getIntent().getIntExtra(GoodsListFragment.GoodsCatId, 61)), false);
		}
	}
}
