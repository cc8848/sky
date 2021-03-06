package com.xmbl.ops.dao.house.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xmbl.ops.dao.base.EntityDaoMPDBImpl;
import com.xmbl.ops.dao.house.IBaseHouseDao;
import com.xmbl.ops.model.house.BaseHouse;

@Repository
public class BaseHouseDaoImpl extends EntityDaoMPDBImpl<BaseHouse> implements IBaseHouseDao{
    
	
	@Override
	public long searchCount(Long id,String title,Integer type,
		    String housename,Integer tradetype,Double price,
		    Double rental,Double unitprice,Integer rentalpricetype,
		    Integer floor,String room,Double acreage,String orientation,
		    String officetag,String officetype, String paymentmethod,
		    String seemethod,String source,String iskey,String remarks,
		    String founder,String owner,String ownerphone,
		    String propertycompany,String propertphone,String operator,Date startDate,Date endDate) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		para.put("title", title);
		para.put("housename", housename);
		para.put("tradetype", tradetype);
		para.put("operator", operator);
		para.put("startDate", startDate);
		para.put("endDate", endDate);
		long count = getSqlSessionTemplate().selectOne(getNameSpace() + ".searchCount", para);
		return count;
	}
	
	@Override
	public List<BaseHouse> searchList(Long id,String title,Integer type,
		    String housename,Integer tradetype,Double price,
		    Double rental,Double unitprice,Integer rentalpricetype,
		    Integer floor,String room,Double acreage,String orientation,
		    String officetag,String officetype, String paymentmethod,
		    String seemethod,String source,String iskey,String remarks,
		    String founder,String owner,String ownerphone,
		    String propertycompany,String propertphone,String operator,Date startDate,Date endDate,
			Long page, int limit){
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		para.put("title", title);
		para.put("housename", housename);
		para.put("tradetype", tradetype);
		para.put("operator", operator);
		para.put("startDate", startDate);
		para.put("endDate", endDate);
		para.put("offset", page);
		para.put("limit", limit);
		List<BaseHouse> results = getSqlSessionTemplate().selectList(getNameSpace() + ".searchList", para);
		return results;
	}
	
	
	
	@Override
	public long searchCount(Long id,String title,Integer type,
		    String housename,Integer tradetype,Double price,
		    Double rental,Double unitprice,Integer rentalpricetype,
		    Integer floor,String room,Double acreage,String orientation,
		    String officetag,String officetype, String paymentmethod,
		    String seemethod,String source,String iskey,String remarks,
		    String founder,String owner,String ownerphone,
		    String propertycompany,String propertphone,String operator,Date startDate,Date endDate,
		    Double startAcreage,Double endAcreage,Double startRental,Double endRental,
		    String housestatus,String isstatus,String diskstatus,String sealingdisk) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		para.put("title", title);
		para.put("housename", housename);
		para.put("tradetype", tradetype);
		para.put("operator", operator);
		para.put("startDate", startDate);
		para.put("endDate", endDate);
		
		
		
		para.put("founder", founder);
		para.put("owner", owner);
		para.put("ownerphone", ownerphone);
		para.put("startAcreage", startAcreage);
		para.put("endAcreage", endAcreage);
		para.put("startRental", startRental);
		para.put("endRental", endRental);
		para.put("housestatus", housestatus);
		para.put("isstatus", isstatus);
		para.put("diskstatus", diskstatus);
		para.put("sealingdisk", sealingdisk);
		
		
		para.put("type", type);
		para.put("tradetype", tradetype);
		para.put("orientation", orientation);
		para.put("paymentmethod", paymentmethod);
		para.put("source", source);
		para.put("seemethod", seemethod);
		para.put("rentalpricetype", rentalpricetype);
		
		
		long count = getSqlSessionTemplate().selectOne(getNameSpace() + ".searchCount", para);
		return count;
	}
	
	@Override
	public List<BaseHouse> searchList(Long id,String title,Integer type,
		    String housename,Integer tradetype,Double price,
		    Double rental,Double unitprice,Integer rentalpricetype,
		    Integer floor,String room,Double acreage,String orientation,
		    String officetag,String officetype, String paymentmethod,
		    String seemethod,String source,String iskey,String remarks,
		    String founder,String owner,String ownerphone,
		    String propertycompany,String propertphone,String operator,Date startDate,Date endDate,
		    Double startAcreage,Double endAcreage,Double startRental,Double endRental,
		    String housestatus,String isstatus,String diskstatus,String sealingdisk,
			Long page, int limit){
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		para.put("title", title);
		para.put("housename", housename);
		para.put("tradetype", tradetype);
		para.put("operator", operator);
		para.put("startDate", startDate);
		para.put("endDate", endDate);
		
		
		
		para.put("founder", founder);
		para.put("owner", owner);
		para.put("ownerphone", ownerphone);
		para.put("startAcreage", startAcreage);
		para.put("endAcreage", endAcreage);
		para.put("startRental", startRental);
		para.put("endRental", endRental);
		para.put("housestatus", housestatus);
		para.put("isstatus", isstatus);
		para.put("diskstatus", diskstatus);
		para.put("sealingdisk", sealingdisk);
		
		
		para.put("type", type);
		para.put("orientation", orientation);
		para.put("paymentmethod", paymentmethod);
		para.put("source", source);
		para.put("seemethod", seemethod);
		para.put("rentalpricetype", rentalpricetype);
		
		para.put("offset", page);
		para.put("limit", limit);
		List<BaseHouse> results = getSqlSessionTemplate().selectList(getNameSpace() + ".searchList", para);
		return results;
	}
	
	@Override
	public int deleteBaseHouse(Long id) {
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("id", id);
		int count = getSqlSessionTemplate().update(getNameSpace() + ".deleteById", para);
		return count;
	}

}
