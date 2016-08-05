package com.wstmall.api.getshop.good;

/**
 * areaId3:县区ID
 brandName:品牌信息 
 */
import com.wstmall.bean.AbstractParam;
import com.wstmall.util.http.HttpMethod;
import com.wstmall.util.http.RequestType;

/*shopId:店铺ID
 shopCatId1:店铺一级分类
 shopCatId2:店铺二级分类
 startPrice:商品搜索开始价格
 endPrice:商品搜索结束价格
 desc:排序类型 0:销量 1:价格 2:好评
 descType:排序方式 0:升序 1:降序
 p:页码 */
@RequestType(type = HttpMethod.GET)
public class GetShopGoods extends AbstractParam {
	private String a = "getShopGoods";
	public String shopId;
	public String shopCatId1;
	public String shopCatId2;
	public double startPrice;
	public double endPrice;
	public int  desc;
	public int descType;
	public int p;

	@Override
	public String getA() {
		return a;
	}
}
