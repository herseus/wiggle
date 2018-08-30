package com.keepsa.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.keepsa.service.StockServiceImpl;

@Controller
public class StockController {
	@Resource
	private StockServiceImpl stockService;
	@RequestMapping(value="/getCurStock") 
	public void getCurStock(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Method starts");
		System.out.println("Current Num of Stocks:" + stockService.getStocks());
		System.out.println("Method ends");
	}
}
