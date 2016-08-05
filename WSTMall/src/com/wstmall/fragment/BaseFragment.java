
package com.wstmall.fragment;

import java.lang.reflect.Field;

import org.apache.http.Header;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.application.Const;
import com.wstmall.application.WSTMallApplication;
import com.wstmall.bean.AbstractParam;
import com.wstmall.fragment.user.ShippingAdressFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.util.JSONTool;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
import com.wstmall.widget.BottomPopWindow;
import com.wstmall.widget.LoadingDialog;
import com.wstmall.widget.TitleWidget;

import android.app.Fragment;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;


public abstract class BaseFragment extends Fragment implements TitleWidget.IOnClick {

	protected TitleWidget tWidget;
	private View conteView;
	public LoadingDialog progressDialog;
	protected Gson gson;
	protected PopupWindow buttomPop;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentView fragmentView = this.getClass().getAnnotation(FragmentView.class);
		conteView = inflater.inflate(fragmentView.id(), container, false);
		return conteView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gson = new Gson();
	}
	
	protected void showButtomPop(BottomPopWindow.OnMenuSelect onMenuSelect, String[] menus) {
		BottomPopWindow popLayout = new BottomPopWindow(getActivity(), onMenuSelect);
		popLayout.setMenu(menus);
		popLayout.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_bottom));
		buttomPop = new PopupWindow(popLayout, WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);
		buttomPop.setFocusable(true);
		buttomPop.setBackgroundDrawable(new BitmapDrawable());
		buttomPop.setOutsideTouchable(true);
		buttomPop.update();
		buttomPop.showAsDropDown(tWidget);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		((WSTMallApplication) getActivity().getApplication()).saveCache();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tWidget = (TitleWidget) conteView.findViewById(R.id.titleBar);
		if (tWidget != null) {
			tWidget.setTitleListener(this);
		}
		injectView();
		bindDataForUIElement();
		bindEvent();
	}

	private void injectView() {
		Field[] field = JSONTool.getField(this.getClass(), InjectView.class);
		for (Field f : field) {
			f.setAccessible(true);
			InjectView injectView = f.getAnnotation(InjectView.class);
			int rid = injectView.id();
			try {
				f.set(this, conteView.findViewById(rid));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				System.out.println(getClass().getName() + "  :  " + f.getName() + "  :  " + rid);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadOnImage(String uri, ImageView imageView) {
		((BaseActivity) getActivity()).loadOnImage(uri,imageView);
	}
	
	public void loadOnRectangleImage(String uri, ImageView imageView) {
		((BaseActivity) getActivity()).loadOnRectangleImage(uri,imageView);
	}
	
	public void loadOnRoundImage(String uri, ImageView imageView) {
		((BaseActivity) getActivity()).loadOnRoundImage(uri,imageView);
	}

	public abstract void bindDataForUIElement();

	protected abstract void bindEvent();

	@Override
	public void leftClick() {
		((BaseActivity)getActivity()).popFragement();
	}

	@Override
	public void rightClick() {
	}

	@Override
	public void centerClick() {
	}

	private AbstractParam param;

	public void request(final AbstractParam ap) {
		showDialog();
		Class clazz = ap.getClass();
		param = ap;
		RequestType type = (RequestType) clazz.getAnnotation(RequestType.class);
		if (type != null) {
			final String url = Const.API_BASE_URL + param.getA();
			RequestParams p = ap.getChildFatherRequestParam();
			AsyncHttpClient c = new AsyncHttpClient();
			if (type.type().equals(HttpMethod.GET)) {
				c.get(url + ap.getString(), textHttpResponseHandler);
				Log.i("get给服务器的data", url + ap.getString());
			} else {
				c.post(url, p, textHttpResponseHandler);
				Log.i("post给服务器的data", url + p);
			}
		}
	}

	public void requestNoDialog(final AbstractParam ap) {
		Class clazz = ap.getClass();
		param = ap;
		RequestType type = (RequestType) clazz.getAnnotation(RequestType.class);
		if (type != null) {
			final String url = Const.API_BASE_URL + param.getA();
			RequestParams p = ap.getChildFatherRequestParam();
			AsyncHttpClient c = new AsyncHttpClient();
			if (type.type().equals(HttpMethod.GET)) {
				c.get(url + ap.getString(), textHttpResponseHandler);
				Log.i("get给服务器的data", url + ap.getString());
			} else {
				c.post(url, p, textHttpResponseHandler);
				Log.i("post给服务器的data", url + p);
			}
		}
	}

	TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers, String responseBody) {
			// TODO Auto-generated method stub
			Log.i("接收到的json", responseBody);
			System.out.println("StatusCode : " + statusCode);
			dimissDialog();
			try {
				if (responseBody.startsWith("\ufeff")) {
					responseBody = responseBody.substring(1);
				}
				if (responseBody.indexOf("{") > -1) {
					responseBody = responseBody.substring(responseBody.indexOf("{"));
					JSONObject response = new JSONObject(responseBody);
					if (response.getInt("status") == -1000) {
						Toast.makeText(getActivity(), "用户令牌已过期，请重新登录", Toast.LENGTH_SHORT).show();
						BaseActivity.reLogin();
						return;
					}else if (response.getInt("status") == 1) {
						requestSuccess(param.getA(), response.toString());
					} else {
						flagFailed(param.getA());
						Toast.makeText(getActivity(), response.getString("msg"), Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "请求出错，请重试！", Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			// TODO Auto-generated method stub
			dimissDialog();
			requestFailed();
		}
	};

	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = new LoadingDialog(getActivity(), "正在加载更多数据");
			progressDialog.show();
		} else {
			progressDialog.show();
		}
	}
	
	public void replaceFragment(BaseFragment newFragment, boolean isAddToBackStack) {
		((BaseActivity) getActivity()).replaceFragment(newFragment, isAddToBackStack);
	}

	protected void requestSuccess(String url, String data) {
	}

	protected void requestFailed() {
		Toast.makeText(getActivity(), getString(R.string.net_error), Toast.LENGTH_SHORT);
	}

	protected void flagFailed(String url) {
	}

	public void dimissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

}
