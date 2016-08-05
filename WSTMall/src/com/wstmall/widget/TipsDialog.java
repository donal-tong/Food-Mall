package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TipsDialog extends Dialog implements OnClickListener {
	private TextView tv_ok;
	private TextView tv_what_shop;
	private Context context;
	private String whatShow;
	private TipsTodo tipsTodo;

	public TipsDialog(Context context, int theme, String whatShow) {
		super(context, theme);
		this.context = context;
		this.whatShow = whatShow;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips_dialog);
		tv_ok = (TextView) findViewById(R.id.tv_ok);
		tv_what_shop = (TextView) findViewById(R.id.tv_what_shop);
		tv_what_shop.setText(whatShow);
		tv_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ok:
			tipsTodo.sureTodo();
			dismiss();
			break;

		}
	}

	public interface TipsTodo {
		void sureTodo();
	}
	public void setSureToDoListener(TipsTodo tipsTodo){
		this.tipsTodo=tipsTodo;
	}
}
