
package com.wstmall.fragment.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.activity.MainActivity;
import com.wstmall.activity.imagecrop.CropActivity;
import com.wstmall.activity.user.MineActivity;
import com.wstmall.api.UploadPic;
import com.wstmall.api.user.EditUserPhoto;
import com.wstmall.application.Const;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FileUtil;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.BottomPopWindow;


@FragmentView(id = R.layout.fragment_edit_user_info)
public class EditUserInfoFragment extends BaseFragment implements View.OnClickListener, BottomPopWindow.OnMenuSelect {

	@InjectView(id = R.id.head_image_layout)
	private View head_image_layout;
	@InjectView(id = R.id.user_name_layout)
	private View user_name_layout;
	@InjectView(id = R.id.sex_layout)
	private View sex_layout;

	@InjectView(id = R.id.head_image)
	private ImageView head_image;

	@InjectView(id = R.id.login_name)
	private TextView login_name;
	@InjectView(id = R.id.user_name)
	private TextView user_name;
	@InjectView(id = R.id.sex)
	private TextView sex;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_image_layout:
			showButtomPop(this, new String[] { "拍照", "我的相册" });
			break;
		case R.id.user_name_layout:
			replaceFragment(new ChangeUserNameFragment(), true);
			break;
		case R.id.sex_layout:
			replaceFragment(new ChangeSexFragment(), true);
			break;
		}
	}
	
	@Override
	public void leftClick() {
		super.leftClick();
		MainActivity.mHost.getTabWidget().setVisibility(View.VISIBLE);
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		login_name.setText(Const.user.loginName);
		if (Const.user.userName != null && !Const.user.userName.equals("")) {
			user_name.setText(Const.user.userName);
		}
		sex.setText(Const.user.getSex());
		if (Const.user.userPhoto != null && !Const.user.userPhoto.equals("")) {
			loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, head_image);
		}
		((MineActivity)getActivity()).editUserInfoFragment=this;
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		head_image_layout.setOnClickListener(this);
		user_name_layout.setOnClickListener(this);
		sex_layout.setOnClickListener(this);
	}
	
	public void refreshHeadImage(){
		loadOnRoundImage(Const.BASE_URL + Const.user.userPhoto, head_image);
	}

	@Override
	public void onItemMenuSelect(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			((MineActivity)getActivity()).startCamera();
			break;
		case 1:
			((MineActivity)getActivity()).startSelectPhoto();
			break;
		}
		buttomPop.dismiss();
	}

	@Override
	public void onCancelSelect() {
		// TODO Auto-generated method stub
		buttomPop.dismiss();
	}

}
