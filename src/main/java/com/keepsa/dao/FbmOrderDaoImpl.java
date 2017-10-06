package com.keepsa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keepsa.pojo.FbmOrderVo;
import com.keepsa.pojo.UnshippedFBMOrderVo;

@Repository
public class FbmOrderDaoImpl extends AbstractDao {

	public List<FbmOrderVo> query(FbmOrderVo fbmOrderVo) {
		return sstSlave.selectList("fbmOrderMapper.query", fbmOrderVo);
	}

	public void insert(List<FbmOrderVo> fbmOrderVos) {
		if (null != fbmOrderVos && !fbmOrderVos.isEmpty()) {
			// Is it NECESSARY to validate the parameters in dao layer????
			sstMaster.insert("fbmOrderMapper.insert", fbmOrderVos);
		}
	}

	public List<UnshippedFBMOrderVo> queryUnshippedFBMOrder() {
		return sstSlave.selectList("fbmOrderMapper.queryUnshippedFBMOrder");
	}

	public int setUnshippedFbmOrderShipped() {
		return sstMaster.update("fbmOrderMapper.setUnshippedFbmOrderShipped");
	}

	public List<FbmOrderVo> queryUnshippedFbmOrders() {
		return sstSlave.selectList("fbmOrderMapper.queryUnshippedFbmOrders");
	}

}
