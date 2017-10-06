package com.keepsa.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.keepsa.dao.StockDaoImpl;

@Service
public class StockServiceImpl {
	@Resource
	private StockDaoImpl stockDao;
	
	public Object getStocks() {
		return stockDao.getStocks();
	}
}
