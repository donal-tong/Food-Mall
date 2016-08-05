
package com.wstmall.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class GoodsKinds {

	public static List<GoodsKindsId> goodsKindsList;

	public GoodsKinds(JSONArray jsonArray) {

		try {
			Gson gson=new Gson();
			goodsKindsList=new ArrayList<GoodsKindsId>();
			for(int i=0;i<jsonArray.length();i++){
			goodsKindsList.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), GoodsKindsId.class));
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	String tempString = "47tan时蔬水果、网上菜场tan0tan56tan虚拟服务、优惠票券tan0tan55tan床上用品、玩具乐器tan0tan54tan家居家电、厨具卫浴tan0tan53tan营养保健、调理用品tan0tan52tan休闲食品、进口食品tan0tan51tan美容护理、洗浴用品tan0tan50tan粮油食品、南北干货tan0tan49tan酒水饮料、茶叶冲饮tan0tan48tan厨卫清洁、纸制用品tan0tan71tan其它tan47tan66tan速冻食品tan47tan65tan海鲜水产tan47tan64tan肉类蛋禽tan47tan63tan美食净菜tan47tan62tan新鲜蔬菜tan47tan61tan进口水果tan47tan258tan厨卫清洁tan48tan259tan居室清洁tan48tan74tan纸制品tan48tan260tan整理收纳tan48tan261tan一次性用品tan48tan82tan成人奶粉tan49tan81tan饮料饮品tan49tan80tan茶叶tan49tan79tan咖啡tan49tan78tan酒水tan49tan219tan南北干货tan50tan221tan大米面粉tan50tan222tan健康杂粮tan50tan223tan方便速食tan50tan220tan食用油tan50tan167tan缤纷彩妆tan51tan166tan女性护理tan51tan165tan进口美护tan51tan164tan洗浴用品tan51tan189tan坚果/蜜饯tan52tan193tan进口饼干/糕点tan52tan190tan休闲零食tan52tan191tan进口速食调料tan52tan192tan进口休闲零食tan52tan138tan营养健康tan53tan139tan调理用药tan53tan140tan传统滋补tan53tan137tan日常用药tan53tan141tan营养成分tan53tan120tan电器附件tan54tan118tan厨具锅具tan54tan119tan厨房电器tan54tan121tan日常电器tan54tan109tan床上用品tan55tan108tan婴幼家纺tan55tan103tan儿童玩具tan55tan85tan本地生活tan56tan86tan演出票务tan56tan87tan教育培训tan56tan84tan通讯充值tan56tan76tan凤梨tan61tan250tan芒果tan61tan72tan橙柚tan61tan77tan火龙果tan61tan251tan蓝莓tan61tan249tan梨tan61tan73tan苹果tan61tan252tan茄果类tan62tan254tan叶菜类tan62tan257tan豆角类tan62tan256tan根茎类tan62tan255tan营养菜tan62tan253tan农场菜tan62tan321tan乳肉tan63tan318tan凉菜tan63tan327tan骨头tan64tan317tan新鲜鸡蛋tan64tan328tan牛肉tan64tan322tan新鲜鸭蛋tan64tan326tan肉类tan64tan290tan蟹类tan65tan291tan虾类tan65tan292tan加工水产tan65tan289tan贝类tan65tan288tan海鲜tan65tan332tan冰冻海鲜tan66tan319tan冰冻鸡翅tan66tan324tan冰激凌tan66tan323tan冰冻鸭tan66tan320tan其他蔬果tan71tan262tan卷筒纸tan74tan265tan湿巾纸tan74tan266tan商务卫生纸tan74tan264tan平板纸tan74tan263tan手帕纸tan74tan75tan软包抽纸tan74tan293tan白酒tan78tan294tan葡萄酒tan78tan295tan黄酒tan78tan297tan起泡酒tan78tan296tan啤酒tan78tan299tan白咖啡tan79tan302tan咖啡伴侣tan79tan308tan雀巢咖啡tan79tan298tan速溶咖啡tan79tan310tan咖啡豆tan79tan311tan绿茶tan80tan309tan花草茶tan80tan307tan红茶tan80tan303tan乌龙茶tan80tan312tan水果茶tan80tan314tan凉茶tan81tan304tan果蔬汁tan81tan313tan功能饮料tan81tan315tan椰汁tan81tan316tan碳酸饮料tan81tan301tan全脂tan82tan300tan低脂tan82tan305tan甜奶粉tan82tan306tan高钙tan82tan248tan脱脂tan82tan102tan联通话费充值tan84tan101tan电信话费充值tan84tan100tan移动话费充值tan84tan98tan生活缴费tan85tan97tan外卖订座tan85tan99tan汽车养护tan85tan95tan音乐会tan86tan96tan体育赛事tan86tan93tan电影选座tan86tan94tan演唱会tan86tan90tan语言培训tan87tan92tan学习培训tan87tan88tan早教幼教tan87tan89tan艺术培训tan87tan91tan网络课程tan87tan107tan滑板车tan103tan106tan积木拼插tan103tan105tan电动车tan103tan104tan毛绒玩具tan103tan116tan床品套件tan108tan110tan婴儿枕tan108tan111tan冬抱被tan108tan112tan床垫tan108tan113tan记忆枕tan109tan115tan床单床笠tan109tan117tan全棉四件套tan109tan114tan电热毯tan109tan125tan烘焙工具tan118tan124tan铲勺tan118tan123tan平底锅tan118tan122tan压力锅tan118tan127tan电磁炉tan119tan126tan电饭煲tan119tan128tan电炖锅tan119tan131tan电视挂架tan120tan130tan电池tan120tan129tan万能遥控器tan120tan132tan插头tan120tan134tan电吹风tan121tan135tan脱毛器tan121tan133tan理发器tan121tan136tan美发器tan121tan142tan感冒发热tan137tan146tan跌打损伤tan137tan145tan牙龈肿痛tan137tan143tan咽喉肿痛tan137tan144tan止咳化痰tan137tan148tan改善睡眠tan138tan147tan增强免疫tan138tan150tan延缓衰老tan138tan151tan美白养颜tan138tan149tan补肾健体tan138tan152tan补脑安神tan139tan153tan减肥瘦身tan139tan154tan健脾消食tan139tan155tan养肝护胆tan139tan158tan燕窝tan140tan157tan阿胶tan140tan156tan蜂蜜/蜂产品tan140tan159tan鱼油tan141tan161tan海狗/海豹油tan141tan162tan蛋白质/氨基酸tan141tan163tan葡萄糖tan141tan160tan螺旋藻tan141tan168tan沐浴露tan164tan169tan舒肤佳tan164tan170tan浴球/浴擦tan164tan171tan威露士tan164tan186tan女性洗液tan164tan187tan香皂tan164tan184tan沐浴套装tan164tan185tan滴露tan164tan175tan进口美妆tan165tan173tan进口男士护理tan165tan172tan进口护肤品tan165tan174tan进口沐浴tan165tan176tan日用卫生巾tan166tan177tan夜用卫生巾tan166tan178tan护垫tan166tan179tan卫生棉条tan166tan188tan指甲油tan167tan180tan润唇膏tan167tan181tan眼线笔tan167tan182tan粉底tan167tan183tan眼影tan167tan331tan化妆品套装tan167tan202tan开心果tan189tan201tan夏威夷果tan189tan200tan核桃tan189tan199tan坚果tan189tan203tan百草味tan189tan208tan凤爪鸡翅tan190tan207tan鱼干tan190tan206tan鱼豆腐tan190tan205tan牛肉干tan190tan204tan薯片tan190tan213tan进口咖喱tan191tan212tan进口水果罐头tan191tan211tan进口肉罐头tan191tan210tan进口调味油tan191tan209tan进口意面酱tan191tan215tan进口玉米片tan192tan218tan进口海鲜零食tan192tan217tan进口海苔tan192tan216tan进口果冻tan192tan214tan进口薯片tan192tan196tan进口西式糕点tan193tan197tan进口夹心饼干tan193tan195tan进口蛋卷tan193tan198tan进口威化tan193tan194tan进口曲奇tan193tan224tan枸杞tan219tan225tan桂圆/龙眼干tan219tan226tan莲子/枣子tan219tan227tan腌干水产品tan219tan228tan猴头菇tan219tan245tan花生油tan220tan242tan调和油tan220tan246tan橄榄油tan220tan243tan葵花籽油tan220tan244tan玉米油tan220tan229tan大米tan221tan231tan豆类tan221tan247tan粗粮tan221tan230tan面粉tan221tan240tan杂粮tan222tan241tan薏仁米tan222tan237tan小米tan222tan238tan糙米tan222tan239tan玉米tan222tan333tan其他tan223tan236tan罐头食品tan223tan233tan方便粉丝tan223tan235tan八宝粥tan223tan234tan蛋制品tan223tan232tan方便面tan223tan330tan火腿肠tan223tan329tan洗衣粉tan258tan267tan洗洁精tan258tan273tan油污净tan258tan276tan管道疏通tan258tan275tan浴室清洁tan258tan274tan洁厕剂tan258tan281tan家具清洁tan259tan278tan地板清洁tan259tan282tan玻璃清洁tan259tan280tan空气清新tan259tan279tan灭蟑驱虫tan259tan283tan挂钩/粘钩tan260tan285tan防尘罩tan260tan286tan整理架tan260tan284tan收纳盒tan260tan287tan压缩袋tan260tan272tan一次性鞋套tan261tan271tan牙签tan261tan268tan垃圾袋tan261tan270tan保鲜膜tan261tan269tan保鲜袋tan261tan277tan一次性手套tan261tan";
		String[] tempStrings=tempString.split("tan");
		
		goodsKindsList=new ArrayList<GoodsKindsId>();
		
		//第一层
		int i;
		for(i=0;i<30;i=i+3){
			goodsKindsList.add(new GoodsKindsId(tempStrings[i], tempStrings[i+1], tempStrings[i+2]));
		}
		
		//利用暂存，缩短遍历
		GoodsKindsId tempGoodsKind=goodsKindsList.get(0);
		String parentId = "47";
		
		//第二层
		for(;i<57*3;i=i+3){
			if(tempStrings[i+2].equals(parentId)){
				tempGoodsKind.getChildList().add(new GoodsKindsId(tempStrings[i], tempStrings[i+1], tempStrings[i+2]));
			}else{
				parentId=tempStrings[i+2];
				for(int j=0;j<goodsKindsList.size();j++){
					if(goodsKindsList.get(j).getId().equals(parentId)){
						tempGoodsKind=goodsKindsList.get(j);
						break;
					}
				}
				tempGoodsKind.getChildList().add(new GoodsKindsId(tempStrings[i], tempStrings[i+1], tempStrings[i+2]));
			}
		}
		
		//第三层
		for(;i<tempStrings.length;i=i+3){
			if(tempStrings[i+2].equals(parentId)){
				tempGoodsKind.getChildList().add(new GoodsKindsId(tempStrings[i], tempStrings[i+1], tempStrings[i+2]));
			}else{
				parentId=tempStrings[i+2];
				Boolean breakBoolean=true;
				for(int j=0;j<goodsKindsList.size()&&breakBoolean;j++){
					for(int k=0;k<goodsKindsList.get(j).getChildList().size()&&breakBoolean;k++){
						if(goodsKindsList.get(j).getChildList().get(k).getId().equals(parentId)){
							tempGoodsKind=goodsKindsList.get(j).getChildList().get(k);
							breakBoolean=false;
						}
					}
				}
				tempGoodsKind.getChildList().add(new GoodsKindsId(tempStrings[i], tempStrings[i+1], tempStrings[i+2]));
			}
		}*/
	}
	
	public class GoodsKindsId{
		public String id;
		public String name;
		public String parentId;
		public List<GoodsKindsId> childList=new ArrayList<GoodsKindsId>();
		
		public GoodsKindsId(String id,String name,String parentId){
			this.id=id;
			this.name=name;
			this.parentId=parentId;
		}
		
		public String getId(){
			return id;
		}
		
		public String getName(){
			return name;
		}
		
		public String getParentId(){
			return id;
		}
		
		public List<GoodsKindsId> getChildList(){
			return childList;
		}
		
	}

}
