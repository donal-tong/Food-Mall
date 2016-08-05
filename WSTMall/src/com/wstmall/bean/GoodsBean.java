
package com.wstmall.bean;

import java.io.Serializable;


public class GoodsBean implements Serializable {

	public String goodsId;// 商品ID
	public String goodsName;// 商品名称
	public String goodsThums;// 商品图标
	public String shopPrice;// 商品价格
	public String goodsSpec;// 规格
	public String goodsStock;// 是否有库存 0：无货
	public String shopName;//店铺名称
	public String shopId;//店铺ID
	public String appraiseNum;//评论数量

}
