
package com.wstmall.fragment.widget;

import com.zhy_9.food_test.R;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@FragmentView(id = R.layout.fragment_htmlview)
public class HtmlViewFragment extends BaseFragment {

	@InjectView(id = R.id.htmlView)
	private WebView mWebview;

	private String Url;
	private String title;

	public HtmlViewFragment(String Url, String title) {
		this.Url = Url;
		this.title = title;
	}

	private void webViewSetting() {
		WebSettings webSettings = mWebview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(true);// 支持缩放
		webSettings.setBuiltInZoomControls(true);// 允许缩放控制
		webSettings.setDisplayZoomControls(false); // 不显示缩放按钮
		webSettings.setLoadWithOverviewMode(true); // 当需要从一个网页跳转到另一个网页时,目标网页仍然在当前WebView中显示,而不是打开系统浏览器
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setSupportMultipleWindows(false);// 禁止网页多窗口
		webSettings.setUseWideViewPort(true);// 任意比例缩放
	}
	
	public void closePop(){
		mWebview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void bindDataForUIElement() {
		tWidget.setCenterViewText(title);
		webViewSetting();
		closePop();
		loading();
	}

	private void loading() {
		mWebview.loadUrl(Url);
	}

	@Override
	protected void bindEvent() {

	}

	@Override
	public void leftClick() {
		getActivity().finish();
	}

}

