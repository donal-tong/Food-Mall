
package com.wstmall.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GoodsListBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String shopName;//店铺名称
	public String shopId;//店铺ID
	public String goodsId;//商品ID
	public String goodsName;//商品名称
	public String goodsThums;//商品缩略图
	public String shopPrice;//商品价格
	public double userDistance;//距离
	public double deliveryStartMoney;//订单配送起步价
	public double deliveryFreeMoney;//商店免运费价格
	public double score;//星星
	public String goodsAttrId; //商品属性ID,
	public int goodsStock; //商品库存,
	public String goodsSn;//商品编号
	public boolean isBook;
	
	public int goodscount;//用于购物车单个商品数目
	public boolean cbchild; //用于购物车记录checkbox状态
	public String attrVal;
	public String attrName;
	public String goodsSpec;// 规格
	public String goodsUnit;
	public String appraiseNum;//评论数量
	
	public double totalMoney;
	public int goodsNum;
	public double goodsPrice;
	//商品属性
	public String priceAttrName;
	public String priceAttrVal;
	public List<GoodPriceAttrs> priceAttrs=new ArrayList<GoodPriceAttrs>();
	public List<GoodAttrs> attrs=new ArrayList<GoodAttrs>();
	
	public void setGoodscount(int goodscount){
		this.goodscount=goodscount;
	}
	public void Setchild(boolean cbchild){
		this.cbchild=cbchild;
	}
}
