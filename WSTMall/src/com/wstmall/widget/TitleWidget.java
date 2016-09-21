
package com.wstmall.widget;

import com.zhy_9.food_test.R;
import com.wstmall.activity.search.SearchActivity;
import com.wstmall.activity.widget.SelectLocationActivity;
import com.wstmall.application.Const;
import com.wstmall.application.SortFiled;
import com.zbar.lib.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TitleWidget extends RelativeLayout implements OnClickListener {

	public static final int Left_Title_Right_Mode = 1;// 这个是默认模式
	public static final int Location_Search_Zbar_Mode = 2;
	public static final int Search_Mode=3;
	public static final int Left_Search_Zbar_Mode=4;
	public static final int Left_Search_Right_Mode = 5;
	private Context mContext;
	private boolean isChangeAlpha=false;
	private View mView;
	public View left;
	public View center;
	public View right;
	public View rightSearch;

	private Button left_view;
	private TextView center_view;
	private Button right_view;
	private LinearLayout mainLaytou;

	private TextView title_city;
	private View title_location;
	private View title_search;
	private View title_zbar;
	private View title_search_edittext_layout;
	
	public ClearEditText title_search_edittext;

	public TitleWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		findViews();
		initViews(attrs);
		refreshCity();
		invalidate();
		if(isChangeAlpha){
			setTitleAlpha(0);
		}else{
			setTitleAlpha(255);
		}
	}
	public void setChageAlpha(){
		isChangeAlpha=true;
		setTitleAlpha(0);
	}
	private void findViews() {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.title_bar, this);
		mainLaytou = (LinearLayout) mView.findViewById(R.id.mainLayout);
		left = mView.findViewById(R.id.left);
		center = mView.findViewById(R.id.center);
		right = mView.findViewById(R.id.right);
		rightSearch = mView.findViewById(R.id.right_search);

		left_view = (Button) mView.findViewById(R.id.left_view);
		center_view = (TextView) mView.findViewById(R.id.center_view);
		right_view = (Button) mView.findViewById(R.id.right_view);

		title_location = mView.findViewById(R.id.title_location);
		title_search = mView.findViewById(R.id.title_search);
		title_zbar = mView.findViewById(R.id.title_zbar);
		title_search_edittext_layout=mView.findViewById(R.id.title_search_edittext_layout);
		title_city = (TextView) mView.findViewById(R.id.title_city);
		title_search_edittext = (ClearEditText) mView.findViewById(R.id.title_search_edittext);

		title_location.setOnClickListener(this);
		title_search.setOnClickListener(this);
		title_zbar.setOnClickListener(this);
		rightSearch.setOnClickListener(this);
	}

	public void changeMode(int mode) {
		if (mode == Left_Title_Right_Mode) {
			center.setVisibility(View.VISIBLE);
		} else if (mode == Location_Search_Zbar_Mode) {
			left.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			title_location.setVisibility(View.VISIBLE);
			title_search.setVisibility(View.VISIBLE);
			center.setVisibility(View.GONE);
			title_zbar.setVisibility(View.VISIBLE);
		}else if(mode==Search_Mode){
			left.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			title_location.setVisibility(View.VISIBLE);
			title_search.setVisibility(View.GONE);
			title_zbar.setVisibility(View.VISIBLE);
			
			center.setVisibility(View.GONE);
			title_search_edittext_layout.setVisibility(View.VISIBLE);
		}else if(mode==Left_Search_Zbar_Mode){
			right.setVisibility(View.GONE);
			center.setVisibility(View.GONE);
			title_zbar.setVisibility(View.VISIBLE);
			title_search.setVisibility(View.VISIBLE);
		}else if (mode == Left_Search_Right_Mode) {
//			title_search.setVisibility(View.VISIBLE);
			title_location.setVisibility(View.VISIBLE);
			center.setVisibility(View.GONE);
			right.setVisibility(View.GONE);
			rightSearch.setVisibility(View.VISIBLE);
		}
	}
	public void setTitleAlpha(int alphaValue){
		//test alpha
		mainLaytou.getBackground().setAlpha(alphaValue);
		mainLaytou.invalidate();
	}
	public void refreshCity() {
		title_city.setText(Const.cache.city.getCityname());
	}

//	public void setBackColor(String color) {
//		mainLaytou.setBackgroundColor(Color.parseColor(color));
//	}
	public void setSearchAnimaScla(){
		title_zbar.startAnimation(SortFiled.getFadeOut());
		title_location.startAnimation(SortFiled.getFadeOut());
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				title_zbar.setVisibility(View.GONE);
				right.setVisibility(View.VISIBLE);
				right_view.setText("搜索");
				left.setVisibility(View.VISIBLE);
				title_location.setVisibility(View.GONE);
			}
		}, 1000);
		
		
		//title_search_edittext_layout.startAnimation(SortFiled.getScaleIn());
	}
	private Drawable leftBg;
	private Integer leftViewBg;
	private String leftViewStr;
	private String leftVisibility;

	private Drawable rightBg;
	private Integer rightViewBg;
	private String rightViewStr;
	private String rightVisibility;

	private String centerViewStr;
	private String centerVisibility;

	private void initViews(AttributeSet attrs) {
		// TypedArray是一个数组容器
		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
		leftBg = array.getDrawable(R.styleable.TitleBar_left_bg);
		leftViewBg = array.getResourceId(R.styleable.TitleBar_left_view_bg, R.drawable.back);
		leftViewStr = array.getString(R.styleable.TitleBar_left_view_text);
		leftVisibility = array.getString(R.styleable.TitleBar_left_visibility);
		setLeftBg(leftBg);
		setLeftViewBg(leftViewBg);
		setLeftViewText(leftViewStr);
		setLeftVisibility(leftVisibility);

		rightBg = array.getDrawable(R.styleable.TitleBar_right_bg);
		rightViewBg = array.getResourceId(R.styleable.TitleBar_right_view_bg, R.drawable.tran_bg);
		rightViewStr = array.getString(R.styleable.TitleBar_right_view_text);
		rightVisibility = array.getString(R.styleable.TitleBar_right_visibility);
		setRightBg(rightBg);
		setRightViewBg(rightViewBg);
		setRightBtnText(rightViewStr);
		setRightVisibility(rightVisibility);

		centerViewStr = array.getString(R.styleable.TitleBar_center_view_text);
		centerVisibility = array.getString(R.styleable.TitleBar_center_visibility);
		setCenterViewText(centerViewStr);
		setCenterVisibility(centerVisibility);

		left.setOnClickListener(this);
		right.setOnClickListener(this);
		center.setOnClickListener(this);

		array.recycle();
	}

	/**************** start init left view *****************/
	private void setLeftBg(Drawable bg) {
		if (bg == null) {
			left.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed_dark_bg));
		} else {
			left.setBackgroundDrawable(bg);
		}
	}

	public void setLeftViewBg(int resid) {
		left_view.setBackgroundResource(resid);
	}

	private void setLeftViewText(String str) {
		left_view.setText(str);
	}

	public View getLeftView() {
		return left_view;
	}

	public Button getRightButton() {
		return right_view;
	}

	/**
	 * @param visibility
	 *            </br> visibility =>"invisible"为不可见</br> visibility
	 *            =>"visible"为可见</br>
	 */
	public void setLeftVisibility(String visibility) {
		if (visibility != null) {
			if (visibility.toString().equals("visible")) {
				left.setVisibility(View.VISIBLE);
			} else if (visibility.toString().equals("invisible")) {
				left.setVisibility(View.INVISIBLE);
			}
		} else if (leftViewBg != null || leftViewStr != null) {
			left.setVisibility(View.VISIBLE);
		} else {
			left.setVisibility(View.VISIBLE);
		}
	}

	/****************** end init left view ****************/

	/** =================start init right view======================== */
	private void setRightBg(Drawable bg) {
		if (bg == null) {
			right.setBackgroundDrawable(getResources().getDrawable(R.drawable.pressed_dark_bg));
		} else {
			right.setBackgroundDrawable(bg);
		}
	}

	public void setRightViewBg(int resid) {
		right_view.setBackgroundResource(resid);
	}

	public void setRightBtnText(String str) {
		right_view.setText(str);
	}

	public View getRightView() {
		View v = findViewById(R.id.right_view);
		return v;
	}

	/**
	 * @param visibility
	 *            </br> visibility =>"invisible"为不可见</br> visibility
	 *            =>"visible"为可见</br>
	 */
	public void setRightVisibility(String visibility) {
		if (visibility != null) {
			if (visibility.toString().equals("visible")) {
				right.setVisibility(View.VISIBLE);
			} else if (visibility.toString().equals("invisible")) {
				right.setVisibility(View.INVISIBLE);
			}
		} else if (rightViewBg != R.drawable.tran_bg || rightViewStr != null) {
			right.setVisibility(View.VISIBLE);
		} else {
			right.setVisibility(View.INVISIBLE);
		}
	}

	/** =================end init right view======================== */

	/** =================start init center view======================== */
	public void setCenterViewText(CharSequence str) {
		center_view.setText(str);
	}

	public void setCenterViewText(Integer stringid) {
		center_view.setText(stringid);
	}

	public View getCenterView() {
		return center_view;
	}

	public View getLeftL() {
		return left;
	}

	public View getRightL() {
		return right;
	}

	/**
	 * @param visibility
	 *            </br> visibility =>"invisible"为不可见</br> visibility
	 *            =>"visible"为可见</br>
	 */
	public void setCenterVisibility(String visibility) {
		if (visibility != null) {
			if (visibility.toString().equals("visible")) {
				center.setVisibility(View.VISIBLE);
			} else if (visibility.toString().equals("invisible")) {
				center.setVisibility(View.INVISIBLE);
			}
		}
	}

	/** =================end init center view======================== */

	public void setTitleListener(IOnClick l) {
		this.iOnClick = l;
	}

	public interface IOnClick {
		public void leftClick();

		public void rightClick();

		public void centerClick();
	}

	private IOnClick iOnClick;

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.left:
			iOnClick.leftClick();
			break;

		case R.id.center:
			iOnClick.centerClick();
			break;

		case R.id.right:
			iOnClick.rightClick();
			break;

		case R.id.title_zbar:
			intent = new Intent(mContext, CaptureActivity.class);
			((Activity) mContext).startActivityForResult(intent,CaptureActivity.sign);
			break;

		case R.id.title_search:
			intent = new Intent(mContext, SearchActivity.class);
			((Activity) mContext).startActivityForResult(intent, SearchActivity.sign);
			if(((Activity) mContext).getClass().getName().equals("com.wstmall.activity.mainPage.MainPageActivity")){
				Log.e("opopop", ((Activity) mContext).getClass()+"");
				((Activity) mContext).getParent().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}else{
				((Activity) mContext).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}
			break;

		case R.id.title_location:
			intent = new Intent(mContext,SelectLocationActivity.class);
			intent.putExtra(SelectLocationActivity.Latitude, Const.cache.point.getGeoLat());
			intent.putExtra(SelectLocationActivity.Longitude, Const.cache.point.getGeoLng());
			((Activity)mContext).startActivityForResult(intent,SelectLocationActivity.sign);
			break;
			
		case R.id.right_search:
			intent = new Intent(mContext, SearchActivity.class);
			((Activity) mContext).startActivityForResult(intent, SearchActivity.sign);
			if(((Activity) mContext).getClass().getName().equals("com.wstmall.activity.mainPage.MainPageActivity")){
				Log.e("opopop", ((Activity) mContext).getClass()+"");
				((Activity) mContext).getParent().overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}else{
				((Activity) mContext).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
			}
			break;

		default:
			break;
		}
	}

}
