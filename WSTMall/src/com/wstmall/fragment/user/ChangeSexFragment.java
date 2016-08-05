
package com.wstmall.fragment.user;

import android.view.View;
import android.widget.ImageView;

import com.zhy_9.food_test.R;
import com.wstmall.api.user.EditUserInfo;
import com.wstmall.application.Const;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;


@FragmentView(id = R.layout.fragment_change_sex)
public class ChangeSexFragment extends BaseFragment implements View.OnClickListener {
	
	@InjectView(id = R.id.man_layout)
	private View man_layout;
	@InjectView(id = R.id.lady_layout)
	private View lady_layout;
	@InjectView(id = R.id.secret_layout)
	private View secret_layout;
	
	@InjectView(id = R.id.man_right)
	private ImageView man_right;
	@InjectView(id = R.id.lady_right)
	private ImageView lady_right;
	@InjectView(id = R.id.secret_right)
	private ImageView secret_right;
	
	private EditUserInfo editUserInfo=new EditUserInfo();
	
	private String tempSex;
	private String nowSex;

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		
		editUserInfo.tokenId=Const.cache.getTokenId();
		if(Const.user.userName!=null&&!Const.user.userName.equals("")){
		editUserInfo.userName=Const.user.userName;
		}
		
		nowSex=Const.user.getSex();
		if(nowSex.equals("男")){
			man_right.setVisibility(View.VISIBLE);
		}else if(nowSex.equals("女")){
			lady_right.setVisibility(View.VISIBLE);
		}else{
			secret_right.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		man_layout.setOnClickListener(this);
		lady_layout.setOnClickListener(this);
		secret_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		man_right.setVisibility(View.INVISIBLE);
		lady_right.setVisibility(View.INVISIBLE);
		secret_right.setVisibility(View.INVISIBLE);
		
		switch (v.getId()) {
		case R.id.man_layout:
			man_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("男");
			break;
		case R.id.lady_layout:
			lady_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("女");
			break;
		case R.id.secret_layout:
			secret_right.setVisibility(View.VISIBLE);
			tempSex=Const.user.getSexNum("保密");
			break;
		}
		if(!tempSex.equals(nowSex)){
			editUserInfo.userSex=tempSex;
			request(editUserInfo);
		}else{
			leftClick();
		}
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		Const.user.userSex=tempSex;
		leftClick();
	}

}
