package com.wstmall.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.zhy_9.food_test.R;
import com.wstmall.activity.goodlist.GoodListActivity;
import com.wstmall.activity.goods.GoodsActivity;
import com.wstmall.activity.search.SearchActivity;
import com.wstmall.activity.widget.HtmlViewActivity;
import com.wstmall.activity.widget.SelectLocationActivity;
import com.wstmall.api.GetCitys;
import com.wstmall.application.Const;
import com.wstmall.application.WSTMallApplication;
import com.wstmall.bean.AbstractParam;
import com.wstmall.bean.City;
import com.wstmall.bean.Point;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.fragment.goods.GoodsListFragment;
import com.wstmall.fragment.mainPage.MainPageFragment;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;
import com.wstmall.widget.LoadingDialog;
import com.zbar.lib.CaptureActivity;

public abstract class BaseActivity extends Activity {

	private ImageLoader imageLoader;
	public LoadingDialog progressDialog;
	private FragmentManager baseFragmentManager;
	private GetCitys getCitys = new GetCitys();
	private City tempCity;
	private City tempCity2;
	private int numOfGoodsList = 0;
	protected Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((WSTMallApplication) getApplication()).addActivityToList(this);
		Log.e("CurrentActivity", getLocalClassName().toString());
		baseFragmentManager = getFragmentManager();
		imageLoader = ImageLoader.getInstance();
		gson = new Gson();
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
				Log.i("get信息", url + ap.getString());
			} else {
				c.post(url, p, textHttpResponseHandler);
				Log.i("post信息", url + p);
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
				Log.i("get信息", url + ap.getString());
			} else {
				c.post(url, p, textHttpResponseHandler);
				Log.i("post信息", url + p);
			}
		}
	}

	TextHttpResponseHandler textHttpResponseHandler = new TextHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseBody) {
			System.out.println("responseBody : " + responseBody);
			dimissDialog();
			try {
				if (responseBody.startsWith("\ufeff")) {
					responseBody = responseBody.substring(1);
				}
				if (responseBody.indexOf("{") > -1) {
					responseBody = responseBody.substring(responseBody
							.indexOf("{"));
					JSONObject response = new JSONObject(responseBody);
					if (response.getInt("status") == -1000) {
						Toast.makeText(BaseActivity.this, "用户令牌已过期，请重新登录",
								Toast.LENGTH_SHORT).show();
						reLogin();
						return;
					} else if (response.getInt("status") == 1) {
						requestSuccess(param.getA(), response.toString());
					} else {
						Toast.makeText(BaseActivity.this,
								response.getString("msg"), Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(BaseActivity.this, "请求出错，请重试！",
							Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2,
				Throwable arg3) {
			dimissDialog();
			requestFailed();
		}
	};

	protected void requestSuccess(String url, String conent) {
		if (url.contains(getCitys.getA())) {
			JSONObject jsonobj;
			try {
				jsonobj = new JSONObject(conent);
				jsonobj = new JSONObject(jsonobj.getJSONObject("data")
						.toString());
				tempCity.setCityid(jsonobj.getString("areaId"));
				tempCity2.setCityid(jsonobj.getString("areaId2"));
				Const.cache.city = tempCity;
				Const.cache.city2 = tempCity2;
				Log.e("citys", "city" + tempCity + "city2" + tempCity2);
				// MainPageFragment.mainPageFragment.GetAdsNoDialog();
				MainPageFragment.mainPageFragment.refreshOperation();
				MainPageFragment.mainPageFragment.refreshCity();
			} catch (JSONException e) {
			}
		}
	}

	protected void requestFailed() {
		Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT)
				.show();
	}

	public void loadOnImage(String uri, ImageView imageView) {
		imageLoader.displayImage(uri, imageView,
				(WSTMallApplication.imageEllipseOptions));
	}

	public void loadOnRectangleImage(String uri, ImageView imageView) {
		imageLoader.displayImage(uri, imageView,
				(WSTMallApplication.imageRectangleOptions));
	}

	public void loadOnRoundImage(String uri, ImageView imageView) {
		imageLoader.displayImage(uri, imageView,
				(WSTMallApplication.imageRoundOptions));
	}
	public void loadOnRoundImage(String uri, ImageView imageView, int size){
		imageLoader.displayImage(uri, imageView, new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.person_img) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.person_img) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.person_img) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(size)) // 设置成圆型图片
		.build());
	}

	public static void reLogin() {
		Const.isLogin = false;
		Const.cache.setTokenId(null);
		Const.user = null;
	}

	public void dimissDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		((WSTMallApplication) getApplication()).saveCache();
	}

	public void showDialog() {
		if (progressDialog == null) {
			progressDialog = new LoadingDialog(this, "正在加载...");
			progressDialog.show();
		} else {
			progressDialog.show();
		}
	}

	public void showDialog(String message) {
		if (progressDialog == null) {
			progressDialog = new LoadingDialog(this, message);
			progressDialog.show();
		} else {
			progressDialog.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// requestCode标示请求的标示 resultCode表示有数据
		if (requestCode == CaptureActivity.sign && resultCode == RESULT_OK) {
			// 处理二维码传回的String
			if (data.getStringExtra(CaptureActivity.Scan_type).equals("goods")) {
				Intent intent = new Intent(this, GoodsActivity.class);
				intent.putExtra(GoodsActivity.goodsID,
						data.getStringExtra(CaptureActivity.Scan_content));
				startActivity(intent);
			} else if (data.getStringExtra(CaptureActivity.Scan_type).equals(
					"url")) {
				Intent intent = new Intent(this, HtmlViewActivity.class);
				intent.putExtra("title", "二维码网站");
				intent.putExtra("Url",
						data.getStringExtra(CaptureActivity.Scan_content));
				startActivity(intent);
			}

		} else if (requestCode == SelectLocationActivity.sign
				&& resultCode == RESULT_OK) {
			Const.cache.point = (Point) data
					.getSerializableExtra(SelectLocationActivity.Point);

			// 判断城市是否有变化
			if (!((City) data
					.getSerializableExtra(SelectLocationActivity.City2))
					.getCityname().equals(Const.cache.city2.getCityname())) {
				tempCity2 = (City) data
						.getSerializableExtra(SelectLocationActivity.City2);
				tempCity = (City) data
						.getSerializableExtra(SelectLocationActivity.City);
				getCitys.key = tempCity.getCityname();
				getCitys.key2 = tempCity2.getCityname();
				requestNoDialog(getCitys);
			}
		} else if (requestCode == SearchActivity.sign
				&& resultCode == RESULT_OK) {
			String searchTarget = data
					.getStringExtra(SearchActivity.SearchTarget_String);
			// 解决的商品列表重复问题
			Intent intent = new Intent(this, GoodListActivity.class);
			intent.putExtra(GoodsListFragment.SearchTarget, searchTarget);
			startActivity(intent);
		}

	}

	public String whatFragmentTopNow() {
		return baseFragmentManager.findFragmentById(android.R.id.content)
				.getClass().getName();
	}

	public boolean isEmptyFragmentManager() {
		if (baseFragmentManager.getBackStackEntryCount() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void replaceFragment(BaseFragment newFragment,
			boolean isAddToBackStack) {
		String tag = getClass().getSimpleName();
		FragmentTransaction transaction = baseFragmentManager
				.beginTransaction();
		transaction.replace(android.R.id.content, newFragment, tag);
		if (isAddToBackStack) {
			transaction.addToBackStack(tag);
		}
		transaction.commitAllowingStateLoss();
	}

	public void popFragement() {
		if (isEmptyFragmentManager()) {
			finish();
		} else {
			baseFragmentManager.popBackStack();
		}
	}

}
