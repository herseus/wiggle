package com.keepsa.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.keepsa.enumeration.FulfillmentChannelEnum;
import com.keepsa.pojo.OrderInfoWithFeeVo;
import com.keepsa.pojo.OrderVo;

@Repository
public class OrderDaoImpl extends AbstractDao {
	/**
	 * Insert orders
	 * 
	 * @param orders
	 * @return
	 */
	public Integer insertOrder(List<OrderVo> orders) {
		if (null == orders || orders.isEmpty()) {
			return 0;
		}
		
		return sstMaster.insert("orderMapper.insertOrder", orders);
	}
	
	/**
	 * Query orders
	 * 
	 * @param orderVo
	 * @return
	 */
	public List<OrderVo> queryOrder(OrderVo orderVo) {
		List<OrderVo> orderVos = new ArrayList<>();
		if (null != orderVo) {
			orderVos = sstSlave.selectList("orderMapper.queryOrder", orderVo);
		}
		
		return orderVos;
	}
	
	/**
	 * Update orders
	 * 
	 * @param orderVo
	 * @return
	 */
	public Integer updateOrder(OrderVo orderVo) {
		if (null != orderVo 
				&& StringUtils.isNotEmpty(orderVo.getOrderId())
				&& StringUtils.isNotEmpty(orderVo.getAsin())) {
			return sstMaster.update("orderMapper.updateOrder", orderVo);
		}
		
		return 0;
	}

	public List<OrderInfoWithFeeVo> queryOrderInfoWithFee(String fromDate, String toDate, Integer fulfillmentChannel) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
		if (fulfillmentChannel.equals(FulfillmentChannelEnum.Amazon.getCode())) {
			paramMap.put("fulfillmentChannel", "Amazon");
		} else {
			paramMap.put("fulfillmentChannel", "Merchant");
		}
		
		return sstSlave.selectList("orderMapper.queryOrderInfoWithFee", paramMap);
	}

	public void updateAmazonFee(String orderId, String sku, double amazonFee) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId);
		paramMap.put("sku", sku);
		paramMap.put("amazonFee", amazonFee);
		sstMaster.update("orderMapper.updateAmazonFee", paramMap);
	}

	public void updateAmazonFee4NormalOrder(String orderId, String sku, Double fbaFee, Double commission,
			Double shippingChargeback) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId);
		paramMap.put("sku", sku);
		paramMap.put("fbaFee", fbaFee);
		paramMap.put("commission", commission);
		paramMap.put("shippingChargeback", shippingChargeback);
		sstMaster.update("orderMapper.updateAmazonFee4NormalOrder", paramMap);
	}

	public void updateAmazonFee4RefundedOrder(String orderId, String sku, String postedDate, Double refundCommission) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId);
		paramMap.put("sku", sku);
//		paramMap.put("postedDate", postedDate);
		paramMap.put("refundCommission", refundCommission);
		sstMaster.update("orderMapper.updateAmazonFee4RefundedOrder", paramMap);
	}

	public List<String> queryOrderIds() {
		return sstSlave.selectList("orderMapper.queryOrderIds");
	}

	public void updateCostFirstTripFee(String orderId, String sku, BigDecimal cost, BigDecimal firstTripFee) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId);
		paramMap.put("sku", sku);
		paramMap.put("cost", cost);
		paramMap.put("firstTripFee", firstTripFee);
		sstMaster.update("orderMapper.updateCostFirstTripFee", paramMap);
	}

	public List<OrderVo> queryOrder(String fromDate, String toDate, Integer fulfillmentChannel) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
		if (fulfillmentChannel.equals(FulfillmentChannelEnum.Amazon.getCode())) {
			paramMap.put("fulfillmentChannel", "Amazon");
		} else if (fulfillmentChannel.equals(FulfillmentChannelEnum.Merchant.getCode())) {
			paramMap.put("fulfillmentChannel", "Merchant");
		}
		
		return sstSlave.selectList("orderMapper.queryOrderWithinRange", paramMap);
	}

}
