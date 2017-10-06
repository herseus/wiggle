package com.keepsa.dao;

import org.springframework.stereotype.Repository;

@Repository
public class StockDaoImpl extends AbstractDao{
	
	public Object getStocks() {
		return sstMaster.selectList("stockMapper.getStockNum");
//		return 22;
	}
}
