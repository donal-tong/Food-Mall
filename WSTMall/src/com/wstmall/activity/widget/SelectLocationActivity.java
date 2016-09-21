package com.wstmall.activity.widget;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zhy_9.food_test.R;
import com.wstmall.application.Const;
import com.wstmall.bean.City;
import com.wstmall.bean.Point;

/**
 * 
 * 调用方法： Intent intent = new Intent(getActivity(),SelectLocationActivity.class);
 * intent.putExtra(SelectLocationActivity.Latitude, latitude);
 * intent.putExtra(SelectLocationActivity.Longitude, longitude)
 * getActivity().startActivityForResult(intent,
 * SelectLocationActivity.sign_SelectLocationActivity);
 * 
 * 返回 City SelectLocationActivity.City Point SelectLocationActivity.Point
 */
public class SelectLocationActivity extends Activity implements
		OnClickListener, OnGeocodeSearchListener, LocationSource,
		AMapLocationListener {

	public static final String Latitude = "latitude";
	public static final String Longitude = "longitude";
	public static final String Point = "point";
	public static final String City = "city";
	public static final String City2 = "city2";
	public static final int sign = 50;

	private MapView mMapView;
	private Button cancelButton;
	private Button okButton;

	private AMap aMap;
	private Marker marker;

	private double latitude;
	private double longitude;

	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_location);
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);

		cancelButton = (Button) findViewById(R.id.cancel);
		okButton = (Button) findViewById(R.id.ok);

		this.latitude = getIntent().getDoubleExtra(Latitude,
				Const.defaultPoint.getGeoLat());
		this.longitude = getIntent().getDoubleExtra(Longitude,
				Const.defaultPoint.getGeoLng());
		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);

		initMap();
	}

	private void initMap() {
		if (aMap == null) {
			try {
				aMap = mMapView.getMap();
				setUpMap();
				LatLng latlng = new LatLng(latitude, longitude);
				aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 16));

				// 整个屏幕宽高获取
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);

				marker = aMap.addMarker(new MarkerOptions().position(latlng)
						.draggable(true));

				marker.setPositionByPixels(dm.widthPixels / 2,
						(dm.heightPixels - getStatusBarHeight()) / 2);

			} catch (Exception e) {
			}
		}
	}

	// 获取状态栏方法，几乎万能
	private int getStatusBarHeight() {

		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			return getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			return 0;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancel:
			finish();
			break;
		case R.id.ok:

			this.latitude = marker.getPosition().latitude;
			this.longitude = marker.getPosition().longitude;
			// 查找城市
			GeocodeSearch geocodeSearch = new GeocodeSearch(this);
			geocodeSearch.setOnGeocodeSearchListener(this);
			LatLonPoint latLonPoint = new LatLonPoint(this.latitude,
					this.longitude);
			RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
					GeocodeSearch.AMAP);
			geocodeSearch.getFromLocationAsyn(query);

			break;
		default:
			break;
		}
	}

	private void setUpMap() {
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		// aMap.setMyLocationEnabled(true);//
		// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式：定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）
		// 地图根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）三种模式
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		deactivate();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 0 && result != null
				&& result.getRegeocodeAddress() != null
				&& result.getRegeocodeAddress().getFormatAddress() != null) {
			Point point = new Point(this.latitude, this.longitude);
			City city = new City(Const.cache.city.getCityid(), result
					.getRegeocodeAddress().getCity());
			if (city.getCityname().equals("")) {
				city = new City(Const.cache.city.getCityid(), result
						.getRegeocodeAddress().getProvince());
			}
			City city2 = new City(Const.cache.city.getCityid(), result
					.getRegeocodeAddress().getDistrict());
			Intent intent = new Intent();
			intent.putExtra(Point, point);
			intent.putExtra(City, city);
			intent.putExtra(City2, city2);
			setResult(Activity.RESULT_OK, intent);
			finish();
		} else {
			Toast.makeText(this, "获取位置数据失败", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this);
			// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
			// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
			// 在定位结束后，在合适的生命周期调用destroy()方法
			// 其中如果间隔时间为-1，则定位只定一次
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 30 * 1000, 10, this);
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
			}
		}
	}

}
