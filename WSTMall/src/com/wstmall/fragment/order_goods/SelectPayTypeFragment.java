package com.wstmall.fragment.order_goods;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy_9.food_test.R;
import com.wstmall.fragment.BaseFragment;
import com.wstmall.util.FragmentView;
import com.wstmall.util.InjectView;

@FragmentView(id = R.layout.fragment_select_pay_type)
public class SelectPayTypeFragment extends BaseFragment {
	@InjectView(id = R.id.lv_pay_type)
	private ListView re_seletc_pay_type;
	
	@Override
	public void bindDataForUIElement() {
		// TODO Auto-generated method stub
		tWidget.setCenterViewText("支付方式");
		re_seletc_pay_type.setAdapter(new PayTypeAdapter());
	}

	@Override
	protected void bindEvent() {
		// TODO Auto-generated method stub
		re_seletc_pay_type.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				OrderForGoodsFragment.PAY_POSITION=position;
				if(OrderForGoodsFragment.payType.size()!=0){
					OrderForGoodsFragment.addorderIsInvoice.payWay=OrderForGoodsFragment.payType.get(position).isOnline;
					}
				getActivity().getFragmentManager().popBackStack();
			}
		});

	}

	private class PayTypeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return OrderForGoodsFragment.payType.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(getActivity()).inflate(
					R.layout.pay_type_item, null);
			TextView tv_pay_type = (TextView) convertView
					.findViewById(R.id.tv_pay_type);
			tv_pay_type
					.setText(OrderForGoodsFragment.payType.get(position).payName);
			return convertView;
		}

	}
}
