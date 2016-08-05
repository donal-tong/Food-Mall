package com.wstmall.api.addorder;

/*
 tokenId:tokenId
 goodsIds:商品信息列表。格式goodsId1_goodsAttrId_goodsNum;goodsId2_goodsAttrId_goodsNum;goodsId3_goodsAttrId_goodsNum
 adressId:收貨人地址ID
 payWay:付款方式 0-货到付款 1-在线支付
 isInvoice:是否需要發票 1-需要 0-不需要
 invoiceClient:發票抬頭
 remarks:訂單備註
 requireTime:要求送達時間
 isSelf:是否自提 0-不自提 1-自提*/
import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

@RequestType(type = HttpMethod.GET)
public class AddOrders extends AbstractParam {
	private String a = "addOrder";
	public String tokenId;
	public String goodsIds;
	public String adressId;
	public String payWay;
	public String isInvoice;
	public String invoiceClient;
	public String remarks;
	public String requireTime;
	public String isSelf;
	@Override
	public String getA() {
		return a;
	}
}
