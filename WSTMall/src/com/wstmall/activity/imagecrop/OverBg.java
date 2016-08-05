
package com.wstmall.activity.imagecrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ImageView;

public class OverBg extends ImageView {

	private final Paint paint;
	private final Context context;
	private int locationX=0;
	private int locationY=0;
	private int width=0;
	private int height=0;
	private int screenWidth=0;
	private int screenHeight=0;

	public OverBg(Context context, AttributeSet attrs) {
		super(context,attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.paint = new Paint();
		this.paint.setAntiAlias(true); // 消除锯齿
	}
	
	public void set(int locationX,int locationY,int width,int height,int screenWidth,int screenHeight){
		this.locationX=locationX;
		this.locationY=locationY;
		this.width=width;
		this.height=height;
		this.screenWidth=screenWidth;
		this.screenHeight=screenHeight;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		this.paint.setStyle(Style.FILL);
		this.paint.setARGB(55, 0, 0, 0);
		this.paint.setStrokeWidth(1); 
		
		
		//left   top   right   bottom
		//left ： 矩形左边的X坐标
		//top:    矩形顶部的Y坐标  
		//right :  矩形右边的X坐标
		//bottom： 矩形底部的Y坐标
		
		Rect rect = new Rect(0, 0, screenWidth,locationY);//上面
		canvas.drawRect(rect, this.paint);

		rect = new Rect(0, locationY, locationX,locationY+height);//左面
		canvas.drawRect(rect, this.paint);
		
		rect = new Rect(locationX+width, locationY, screenWidth,locationY+height);//右面
		canvas.drawRect(rect, this.paint);
		
		rect = new Rect(0, locationY+height, screenWidth,screenHeight);//下面
		canvas.drawRect(rect, this.paint);

		//橙色框
		this.paint.setStyle(Style.STROKE);
		this.paint.setARGB(255, 215, 139, 39);
		this.paint.setStrokeWidth(3F);
		rect = new Rect(locationX, locationY, locationX+width, locationY+height);
		canvas.drawRect(rect, this.paint);
		
		super.onDraw(canvas);

	}

	/* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}

