package com.wstmall.widget;

import com.zhy_9.food_test.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextBar extends EditText {
	private Context context;
	private Drawable imgDelete;
	private Drawable imgLeft;

	public EditTextBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		imgDelete = context.getResources().getDrawable(
				R.drawable.icon_search_clear_hover);
		Drawable[] drawables = getCompoundDrawables();
		imgLeft = drawables[0];
		addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() != 0&&hasFocus()) {
					setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,
							imgDelete, null);

				} else {
					setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,
							null, null);
				}
			}
		});
		setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus && getText().length() != 0) {
					setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,
							imgDelete, null);
				} else {
					setCompoundDrawablesWithIntrinsicBounds(imgLeft, null,
							null, null);
				}
			}
		});
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (imgDelete != null && event.getAction() == MotionEvent.ACTION_UP) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}

}
