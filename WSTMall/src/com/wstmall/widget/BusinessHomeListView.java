package com.wstmall.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
public class BusinessHomeListView extends ListView {
	private CallBacks callBacks;
	private int startY;
	private int endY;
	public BusinessHomeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY=(int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			endY=(int) ev.getY();
			if(endY>startY){
				callBacks.isMoveDown(true);
			}else{
				callBacks.isMoveDown(false);
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setCallBacks(CallBacks callBacks){
		this.callBacks=callBacks;
	}
	public interface CallBacks{
		void isMoveDown(boolean movedown);
	}
}
