package com.keepsa.dao;

import org.springframework.stereotype.Repository;

import com.keepsa.pojo.ExRateVo;

@Repository
public class ExRateDaoImpl extends AbstractDao {

	public ExRateVo queryExRate() {
		return sstSlave.selectOne("exRateMapper.queryExRate");
	}

	public void updateExRate(ExRateVo exRateVo) {
		sstMaster.update("exRateMapper.updateExRate", exRateVo);
	}
	
}
