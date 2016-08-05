package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RemarkDialog extends Dialog implements OnClickListener {
	private EditText et_remark;
	private TextView tv_remark_type;
	private Button bt_submit_remark;
	private Context context;
	private SubmitRemark submitRemark;
	private String title;
	public RemarkDialog(Context context, int theme,String title) {
		super(context, theme);
		this.context = context;
		this.title=title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_order_remark);
		tv_remark_type= (TextView) findViewById(R.id.tv_remark_type);
		et_remark = (EditText) findViewById(R.id.et_remark);
		bt_submit_remark = (Button) findViewById(R.id.bt_submit_remark);
		tv_remark_type.setText(title);
		bt_submit_remark.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_submit_remark:
			submitRemark.sureTodo();
			dismiss();
			break;
			
		}
	}
	public String getRemarkContent(){
		return et_remark.getText().toString();
	}
	public interface SubmitRemark {
		void sureTodo();
	}
	public void setSureToDoListener(SubmitRemark submitRemark){
		this.submitRemark=submitRemark;
	}
}
