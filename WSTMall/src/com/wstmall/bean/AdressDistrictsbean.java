package com.wstmall.bean;

import java.io.Serializable;
/**
 * "areaId":"县区ID",
 *	"areaName":"名称"
 * @author Administrator
 *
 */
public class AdressDistrictsbean implements Serializable {
	public String areaId;
	public String areaName;
	
	public AdressDistrictsbean(String areaId, String areaName) {
		super();
		this.areaId = areaId;
		this.areaName = areaName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
}
