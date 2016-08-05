package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingDialog extends Dialog {
	private Context mContext;
	private String message;

	public LoadingDialog(Context context, String mesString) {
		super(context, R.style.loading_dialog);
		this.mContext = context;
		this.message = mesString;
		setCanceledOnTouchOutside(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.loading_dialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		layout.getBackground().setAlpha(0);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);

		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				mContext, R.anim.loading_animation);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(message);// 设置加载信息
		// loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
	}

}
