
package com.wstmall.fragment.goods;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy_9.food_test.R;
import com.wstmall.api.goods.AddAppraises;
import com.wstmall.application.Const;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;


@FragmentView(id = R.layout.fragment_appraise_submit)
public class SubmitAppraiseFragment extends BaseFragment implements View.OnClickListener {

	@InjectView(id = R.id.goods_image)
	private ImageView goods_image;
	@InjectView(id = R.id.goods_title)
	private TextView goods_title;
	@InjectView(id = R.id.goods_star_1)
	private ImageView goods_star_1;
	@InjectView(id = R.id.goods_star_2)
	private ImageView goods_star_2;
	@InjectView(id = R.id.goods_star_3)
	private ImageView goods_star_3;
	@InjectView(id = R.id.goods_star_4)
	private ImageView goods_star_4;
	@InjectView(id = R.id.goods_star_5)
	private ImageView goods_star_5;
	@InjectView(id = R.id.time_star_1)
	private ImageView time_star_1;
	@InjectView(id = R.id.time_star_2)
	private ImageView time_star_2;
	@InjectView(id = R.id.time_star_3)
	private ImageView time_star_3;
	@InjectView(id = R.id.time_star_4)
	private ImageView time_star_4;
	@InjectView(id = R.id.time_star_5)
	private ImageView time_star_5;
	@InjectView(id = R.id.service_star_1)
	private ImageView service_star_1;
	@InjectView(id = R.id.service_star_2)
	private ImageView service_star_2;
	@InjectView(id = R.id.service_star_3)
	private ImageView service_star_3;
	@InjectView(id = R.id.service_star_4)
	private ImageView service_star_4;
	@InjectView(id = R.id.service_star_5)
	private ImageView service_star_5;
	@InjectView(id = R.id.appraise_text)
	private EditText appraise_text;
	@InjectView(id = R.id.text_backwards)
	private TextView text_backwards;
	@InjectView(id = R.id.submit_button)
	private Button submit_button;
	
	private int goods_star_num=5;
	private int time_star_num=5;
	private int service_star_num=5;
	
	private AddAppraises addAppraises=new AddAppraises();
	
	public SubmitAppraiseFragment(){
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit_button:
			submit();
			break;
		}
	}
	
	private OnClickListener goods_starOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.goods_star_5:
				goods_star_num=5;
				goods_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_5.setImageResource(R.drawable.android_ratingbar_single_light);
				break;
			case R.id.goods_star_4:
				goods_star_num=4;
				goods_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.goods_star_3:
				goods_star_num=3;
				goods_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_4.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.goods_star_2:
				goods_star_num=2;
				goods_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				goods_star_3.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_4.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.goods_star_1:
				goods_star_num=1;
				goods_star_2.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_3.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_4.setImageResource(R.drawable.android_ratingbar_single);
				goods_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			}
		}
	};
	
	private OnClickListener time_starOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.time_star_5:
				time_star_num=5;
				time_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_5.setImageResource(R.drawable.android_ratingbar_single_light);
				break;
			case R.id.time_star_4:
				time_star_num=4;
				time_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.time_star_3:
				time_star_num=3;
				time_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_4.setImageResource(R.drawable.android_ratingbar_single);
				time_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.time_star_2:
				time_star_num=2;
				time_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				time_star_3.setImageResource(R.drawable.android_ratingbar_single);
				time_star_4.setImageResource(R.drawable.android_ratingbar_single);
				time_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.time_star_1:
				time_star_num=1;
				time_star_2.setImageResource(R.drawable.android_ratingbar_single);
				time_star_3.setImageResource(R.drawable.android_ratingbar_single);
				time_star_4.setImageResource(R.drawable.android_ratingbar_single);
				time_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			}
		}
	};
	
	private OnClickListener service_starOnClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.service_star_5:
				service_star_num=5;
				service_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_5.setImageResource(R.drawable.android_ratingbar_single_light);
				break;
			case R.id.service_star_4:
				service_star_num=4;
				service_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_4.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.service_star_3:
				service_star_num=3;
				service_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_3.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_4.setImageResource(R.drawable.android_ratingbar_single);
				service_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.service_star_2:
				service_star_num=2;
				service_star_2.setImageResource(R.drawable.android_ratingbar_single_light);
				service_star_3.setImageResource(R.drawable.android_ratingbar_single);
				service_star_4.setImageResource(R.drawable.android_ratingbar_single);
				service_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			case R.id.service_star_1:
				service_star_num=1;
				service_star_2.setImageResource(R.drawable.android_ratingbar_single);
				service_star_3.setImageResource(R.drawable.android_ratingbar_single);
				service_star_4.setImageResource(R.drawable.android_ratingbar_single);
				service_star_5.setImageResource(R.drawable.android_ratingbar_single);
				break;
			}
		}
	};
	
	private TextWatcher text_backwards_watcher=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			text_backwards.setHint(""+(150-appraise_text.getText().length()));
		}
	};

	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		goods_star_1.setOnClickListener(goods_starOnClickListener);
		goods_star_2.setOnClickListener(goods_starOnClickListener);
		goods_star_3.setOnClickListener(goods_starOnClickListener);
		goods_star_4.setOnClickListener(goods_starOnClickListener);
		goods_star_5.setOnClickListener(goods_starOnClickListener);
		time_star_1.setOnClickListener(time_starOnClickListener);
		time_star_2.setOnClickListener(time_starOnClickListener);
		time_star_3.setOnClickListener(time_starOnClickListener);
		time_star_4.setOnClickListener(time_starOnClickListener);
		time_star_5.setOnClickListener(time_starOnClickListener);
		service_star_1.setOnClickListener(service_starOnClickListener);
		service_star_2.setOnClickListener(service_starOnClickListener);
		service_star_3.setOnClickListener(service_starOnClickListener);
		service_star_4.setOnClickListener(service_starOnClickListener);
		service_star_5.setOnClickListener(service_starOnClickListener);
		submit_button.setOnClickListener(this);
		appraise_text.addTextChangedListener(text_backwards_watcher);
	}
	
	private void submit(){
		if(appraise_text.getText().toString().trim().length()<10){
			Toast.makeText(getActivity(), "评价必须多于10个字", Toast.LENGTH_LONG).show();
			return;
		}
		addAppraises.goodsScore=goods_star_num;
		addAppraises.timeScore=time_star_num;
		addAppraises.serviceScore=service_star_num;
		addAppraises.content=appraise_text.getText().toString();
		request(addAppraises);
	}
	
	@Override
	protected void requestSuccess(String url, String data) {
		if (url.contains(addAppraises.getA())) {
			Toast.makeText(getActivity(), "评价发表成功～", Toast.LENGTH_LONG).show();
			leftClick();
		}
	}

}
