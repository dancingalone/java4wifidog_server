package com.bandgear.apfree.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.bandgear.apfree.bean.Host;
import com.bandgear.apfree.bean.IPWhite;
import com.bandgear.apfree.bean.MacBlack;
import com.bandgear.apfree.dao.Dao;
import com.bandgear.apfree.dao.impl.HostDao;
import com.bandgear.apfree.dao.impl.IPWhiteDao;
import com.bandgear.apfree.dao.impl.MacBlackDao;
import com.bandgear.apfree.service.PingService;

public class PingServiceImpl implements PingService{
	private Dao<Host> hostDao=null;
	private Dao<IPWhite> ipwhiteDao=null;
	private Dao<MacBlack> macblackDao=null;
	public PingServiceImpl(){
		if(hostDao==null){
			hostDao=new HostDao();
		}
		if(ipwhiteDao==null){
			ipwhiteDao=new IPWhiteDao();
		}
		if(macblackDao==null){
			macblackDao=new MacBlackDao();
		}
	}
	@Override
	public String getPongStr(String dev_id) {
		List<Host> hosts=new ArrayList<Host>();
		List<IPWhite> ipwhites=new ArrayList<IPWhite>();
		List<MacBlack> macblacks=new ArrayList<MacBlack>();
		try {
			hosts = ((HostDao)hostDao).findByDevId(dev_id);
			ipwhites = ((IPWhiteDao)ipwhiteDao).findByDevId(dev_id);
			macblacks = ((MacBlackDao)macblackDao).findByDevId(dev_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JSONObject ruleObj=new JSONObject();
		ruleObj.put("host", hosts);
		ruleObj.put("ipwhite", ipwhites);
		ruleObj.put("macblack", macblacks);
		
		JSONObject pongObj=new JSONObject();
		pongObj.put("rule", ruleObj);
		
		String pongStr="Pong result="+pongObj.toString();
		/**
		 * 返回Pong
		 * 格式为:
		 * 		Pong+空格+result=json字符串
		 * 
		 * 
		 * Pong result={
		 * 		"rule":{
		 * 			"hostlist":[{"ap_id":1,"down":1234,"enable":0,"id":0,"ip":"12.1.1.2","netmask":"","up":0},{"ap_id":1,"down":1234,"enable":0,"id":0,"ip":"12.1.1.2","netmask":"","up":0}],
		 * 			"ipwhite":[{"enable":1,"id":145,"ip":"","netmask":"255.255.255.255"},{"enable":1,"id":145,"ip":"","netmask":"255.255.255.255"}]}
		 * }
		 * json字符串的完整格式请参见wifidog官方文档
		 */
		return pongStr;
	}

}
