package com.wstmall.fragment.user;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.activity.BaseActivity;
import com.wstmall.api.user.EditPass;
import com.wstmall.application.Const;
import com.wstmall.bean.User;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;
import com.wstmall.widget.EditTextBar;

/**
 * @author pzl
 */
@FragmentView(id = R.layout.fragment_mine_accountsecurity)
public class AccountSecurityFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.et_old_count)
	private EditTextBar et_old_count;
	@InjectView(id = R.id.et_new_count_first)
	private EditTextBar et_new_count_first;
	@InjectView(id = R.id.et_new_count_second)
	private EditTextBar et_new_count_second;

	@InjectView(id = R.id.bt_change_account)
	private TextView bt_change_account;

	private EditPass editPass = new EditPass();

	public AccountSecurityFragment() {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_change_account:
			editPass.tokenId = Const.cache.getTokenId();
			editPass.editPassKey=Base64.encodeToString(
					(Base64.encodeToString(et_old_count.getText().toString().getBytes(), Base64.URL_SAFE) + "_" + Base64.encodeToString(
							et_new_count_first.getText().toString().getBytes(), Base64.URL_SAFE)).getBytes(), Base64.NO_WRAP);
			request(editPass);
			break;
		}
	}

	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(editPass.getA())) {
			Toast.makeText(getActivity(), "密码修改成功!", Toast.LENGTH_SHORT).show();
			leftClick();
		}
	}

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("修改密码");
		bt_change_account.setEnabled(false);
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		bt_change_account.setOnClickListener(this);
		et_old_count.addTextChangedListener(textWatcher);
		et_new_count_first.addTextChangedListener(textWatcher);
		et_new_count_second.addTextChangedListener(textWatcher);
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			if (!TextUtils.isEmpty(et_old_count.getText().toString().trim())
					&& !TextUtils.isEmpty(et_new_count_first.getText().toString().trim())
					&& !TextUtils.isEmpty(et_new_count_second.getText().toString().trim())
					&& et_new_count_first.getText().toString().equals(et_new_count_second.getText().toString())) {
				bt_change_account.setEnabled(true);
				bt_change_account.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_login_bg));
			} else {
				bt_change_account.setEnabled(false);
				bt_change_account.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_grey_deep));
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}
	};

}
