
package com.wstmall.fragment.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.api.user.EditUserInfo;
import com.wstmall.application.Const;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.ClearEditText;


@FragmentView(id = R.layout.fragment_change_username)
public class ChangeUserNameFragment extends BaseFragment{
	
	@InjectView(id = R.id.user_name)
	private ClearEditText user_name;
	
	private String tempName;
	private EditUserInfo editUserInfo=new EditUserInfo();

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setRightBtnText("确定");
		editUserInfo.tokenId=Const.cache.getTokenId();
		editUserInfo.userSex=Const.user.userSex;
	}
	
	@Override
	public void rightClick() {
		tempName=user_name.getText().toString().trim();
		if(tempName.equals("")){
			Toast.makeText(getActivity(), "昵称不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		editUserInfo.userName=tempName;
		request(editUserInfo);
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		Const.user.userName=tempName;
		leftClick();
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		
	}

}
