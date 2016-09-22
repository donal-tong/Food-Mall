package com.wstmall.fragment.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.api.useradress.editadress.DeleteAddress;
import com.wstmall.api.useradress.editadress.EditAddress;
import com.wstmall.application.Const;
import com.wstmall.bean.UserAdressBean;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.EditTextBar;

@FragmentView(id = R.layout.fragment_modify_adress)
public class ModifyAddAdressFragment extends BaseFragment implements
		View.OnClickListener {
	private static final String TAG = "ModifyAddAdressFragment";
	@InjectView(id = R.id.bt_save_adress)
	private TextView bt_save_adress;

	@InjectView(id = R.id.ll_area)
	private LinearLayout ll_area;
	@InjectView(id = R.id.tv_area)
	private TextView tv_area;

	@InjectView(id = R.id.et_adress_personname)
	private EditTextBar et_adress_personname;
	@InjectView(id = R.id.et_adress_cellphone)
	private EditTextBar et_adress_cellphone;
	@InjectView(id = R.id.et_adress_telephone)
	private EditTextBar et_adress_telephone;
    @InjectView(id = R.id.et_adress_adress)
    private EditTextBar et_adress_adress;
	@InjectView(id = R.id.et_adress_postalcode)
	private EditTextBar et_adress_postalcode;

	@InjectView(id = R.id.cb_isDefault_adress)
	private CheckBox cb_isDefault_adress;

	@InjectView(id = R.id.re_moadress_bot)
	private RelativeLayout re_moadress_bot;
	
	@InjectView(id = R.id.re_adress_postalcode)
	private RelativeLayout re_adress_postalcode;
	private UserAdressBean useradressbean;
	private String titleflag;
	private boolean area; // 判断有没重新获取地区信息
	private String adressid;
	private EditAddress editadress = new EditAddress();

	// 计算高度，防止键盘遮挡输入
	
	@InjectView(id = R.id.ll_emty)
	private LinearLayout ll_emty;
	private int bot_height;
	private int bot_tab;
	private int set_height;
	private int pos_height;
	private int sHeight;
	private int pos_y;
	public ModifyAddAdressFragment(String titleflag) {
		this.titleflag = titleflag;
	}

	public ModifyAddAdressFragment(UserAdressBean useradressbean,
			String adressid, String titleflag) {
		this.useradressbean = useradressbean;
		this.adressid = adressid;
		this.titleflag = titleflag;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_area:
			((BaseActivity) getActivity()).replaceFragment(
					new UserAdressListDistrictFragment(), true);
			area = true;
			break;
		case R.id.bt_save_adress:

			if (titleflag.equals("change")) { //
				// 修改已有地址类别：根据地址ID，修改某个地址信息上传到服务器，replace
				// 在ShippingAdressFragment重载画面。
				editadress.tokenId = Const.cache.getTokenId();
				editadress.id = useradressbean.getAddressId();
				editadress.userName = et_adress_personname.getText().toString();
				if (area) {
					editadress.areaId1 = "";
					editadress.areaId2 = Const.cache.city.getCityid();
					editadress.areaId3 = UserAdressListCommunitysFragment.areaId3;
					editadress.communityId = UserAdressListCommunitysFragment.communityId;
				} else {
					editadress.areaId1 = "";
					editadress.areaId2 = Const.cache.city.getCityid();
					editadress.areaId3 = useradressbean.getAreaId3();// 空的时候上传数据失败,接口没给出。
					editadress.communityId = useradressbean.getCommunityId();
					Log.i(TAG,
							"区域IDidiidididi" + useradressbean.getCommunityId());
				}

				editadress.userPhone = et_adress_cellphone.getText().toString();
				editadress.userTel = et_adress_telephone.getText().toString();
				editadress.address = et_adress_adress.getText().toString();

				request(editadress);
				getActivity().getFragmentManager().popBackStack();

			} else {
				// 新增地址类别：
				editadress.tokenId = Const.cache.getTokenId();
				editadress.id = "";
				editadress.userName = et_adress_personname.getText().toString();
				editadress.areaId1 = "";
				editadress.areaId2 = Const.cache.city.getCityid();
				editadress.areaId3 = UserAdressListCommunitysFragment.areaId3;
				editadress.communityId = UserAdressListCommunitysFragment.communityId;
				editadress.userPhone = et_adress_cellphone.getText().toString();

				editadress.userTel = et_adress_telephone.getText().toString();
				editadress.address = et_adress_adress.getText().toString();
				request(editadress);

				getActivity().getFragmentManager().popBackStack();

			}
			break;

		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		bt_save_adress.setEnabled(false);
		if (titleflag.equals("add")) {
			tWidget.setCenterViewText("新建地址");
		} else {
			tWidget.setCenterViewText("修改地址");
			tv_area.setText(useradressbean.getAreaName());
			setmoditydata();
		}
		
		if (area) {
			tv_area.setText(UserAdressListCommunitysFragment.districtsandcommunity);
			area = false;
		}
		tWidget.setLeftVisibility("visible");
		tWidget.setRightBtnText("删除");
		//tWidget.setRightViewBg(R.drawable.file_delete);
		setDefaultAdress();
//		setHeight();
	}

	public void setHeight() {
		ViewTreeObserver vto = re_moadress_bot.getViewTreeObserver();  
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
			@Override  
            public void onGlobalLayout() {
            	re_moadress_bot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            	bot_tab=re_moadress_bot.getHeight();
            	pos_height=re_adress_postalcode.getHeight();
            	Log.e("adfds", pos_height+""+bot_tab);
                int[] location = new  int[2] ;
                re_adress_postalcode.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                pos_y=location [1];
                countScroeenHeight();
            	set_height=sHeight-pos_y-pos_height-bot_tab;
            	ll_emty.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, set_height));
            }  
        });

	}

	@SuppressWarnings("deprecation")
	private void countScroeenHeight() {
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		sHeight = wm.getDefaultDisplay().getHeight();
	}
	public void rightClick() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("确定删除");
		builder.setTitle("提示");

		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				DeleteAddress deleteadress = new DeleteAddress();
				deleteadress.tokenId = Const.cache.getTokenId();
				deleteadress.id = adressid;
				request(deleteadress);
				getActivity().getFragmentManager().popBackStack();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		bt_save_adress.setOnClickListener(this);
		ll_area.setOnClickListener(this);
		et_adress_personname.addTextChangedListener(textWatcher);
		et_adress_cellphone.addTextChangedListener(textWatcher);
		tv_area.addTextChangedListener(textWatcher);
		et_adress_adress.addTextChangedListener(textWatcher);
		et_adress_telephone.addTextChangedListener(textWatcher);
		et_adress_postalcode.addTextChangedListener(textWatcher);

		cb_isDefault_adress
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (!TextUtils.isEmpty(et_adress_personname.getText()
								.toString().trim())
								&& !TextUtils.isEmpty(et_adress_cellphone
										.getText().toString().trim())
								&& !TextUtils.isEmpty(tv_area.getText()
										.toString().trim())
								&& !TextUtils.isEmpty(et_adress_adress
										.getText().toString().trim())) {
							bt_save_adress.setEnabled(true);
							bt_save_adress.setBackgroundDrawable(getResources()
									.getDrawable(R.drawable.round_login_bg));
						} else {
							bt_save_adress.setEnabled(false);
							bt_save_adress.setBackgroundDrawable(getResources()
									.getDrawable(R.drawable.round_grey_deep));
						}
						// TODO Auto-generated method stub
						if (ShippingAdressFragment.useradressbeanlist.size() != 0) {
							if (isChecked) {
								cb_isDefault_adress
										.setButtonDrawable(R.drawable.btn_c_open);
								editadress.isDefault = "1";
							} else {
								cb_isDefault_adress
										.setButtonDrawable(R.drawable.btn_c_close);
								editadress.isDefault = "0";
							}
						} else {
							cb_isDefault_adress.setChecked(true);
						}
					}
				});
	}

	public void setDefaultAdress() {
		if (ShippingAdressFragment.useradressbeanlist.size() < 2) {
			cb_isDefault_adress.setChecked(true);
			editadress.isDefault = "1";
		} else {
			if(useradressbean!=null){
			if(useradressbean.isDefault.equals("1")){
				cb_isDefault_adress.setChecked(true);
				editadress.isDefault = "1";
			}else{
				cb_isDefault_adress.setChecked(false);
				editadress.isDefault = "0";
			}
			}
		}
	}

	public void setmoditydata() {
		et_adress_personname.setText(useradressbean.getUserName());
		et_adress_cellphone.setText(useradressbean.getUserPhone());
		et_adress_telephone.setText(useradressbean.getUserTel());
		et_adress_adress.setText(useradressbean.getAddress());
		et_adress_postalcode.setText(useradressbean.getPostCode());
	}

	private TextWatcher textWatcher = new TextWatcher() {

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
			if (!TextUtils.isEmpty(et_adress_personname.getText().toString()
					.trim())
					&& !TextUtils.isEmpty(et_adress_cellphone.getText()
							.toString().trim())
					&& !TextUtils.isEmpty(tv_area.getText().toString().trim())
					&& !TextUtils.isEmpty(et_adress_adress.getText().toString()
							.trim())) {
				bt_save_adress.setEnabled(true);
				bt_save_adress.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.round_login_bg));
			} else {
				bt_save_adress.setEnabled(false);
				bt_save_adress.setBackgroundDrawable(getResources()
						.getDrawable(R.drawable.round_grey_deep));
			}
		}
	};

}
