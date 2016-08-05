package com.wstmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 	"catId":分类ID
	"catName":分类名称
	"shopName":店铺名称,
	"shopId":店铺ID,
	"goodsId":商品ID,
	"goodsName":商品名称,
	"goodsThums":商品图片,
	"shopPrice":商品价格
 * @author Administrator
 *
 */
public class RecommendGoodsBean implements Serializable {	
	public String catId;
	public String catName;
	public List<GoodsListBean> goodlistbean;
}
