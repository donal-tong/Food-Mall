package com.wstmall.bean;

import com.wstmall.application.GoodsKinds.GoodsKindsId;

	public class SortRight{
		public boolean isParentBoolean;
		public GoodsKindsId parent;
		public GoodsKindsId child_1;
		public GoodsKindsId child_2;
		public GoodsKindsId child_3;
		
		public SortRight(GoodsKindsId parent){
			this.parent=parent;
			isParentBoolean=true;
		}
		
		public SortRight(GoodsKindsId child_1,GoodsKindsId child_2,GoodsKindsId child_3){
			this.child_1=child_1;
			this.child_2=child_2;
			this.child_3=child_3;
			isParentBoolean=false;
		}

}
