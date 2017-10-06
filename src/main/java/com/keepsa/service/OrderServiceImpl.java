package com.keepsa.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.keepsa.dao.OrderDaoImpl;
import com.keepsa.dao.ProductDaoImpl;
import com.keepsa.enumeration.AmazonOrderColIndexEnum;
import com.keepsa.enumeration.FulfillmentChannelEnum;
import com.keepsa.enumeration.ResponseCodeEnum;
import com.keepsa.pojo.ExRateMapper;
import com.keepsa.pojo.OrderInfoWithFeeVo;
import com.keepsa.pojo.OrderVo;
import com.keepsa.pojo.ProductAggregateInfoVo;
import com.keepsa.pojo.ProductBaseInfoVo;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.pojo.SalesData4OneCountry;
import com.keepsa.utils.ConstantUtils;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.TransformUtils;

@Service
public class OrderServiceImpl {
	@Resource
	private OrderDaoImpl orderDao;

	@Resource
	private ProductDaoImpl productDao;

	@Resource
	private FinanceServiceImpl financeService;

	private static Integer numCols = 29;

	/**
	 * 导入txt文件，解析
	 */
	public ResponseVo importOrder(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {
			// Parse .txt file

			// Read the head
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

				String line = null;

				line = reader.readLine();
				String[] arr = line.split("\t", -1);
				if (null == arr || arr.length != numCols) {
					return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(),
							"Incorrect number of columns", null);
				}

				// Process the data
				List<OrderVo> orders = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					arr = line.split("\t", -1);

					orders.add(loadFromArr(arr));

				}

				if (!orders.isEmpty()) {
					orderDao.insertOrder(orders);
				}
				reader.close();
				return ResponseUtils.getSuccessResponseVo(null);

			} catch (Exception e) {
				return ResponseUtils.getFailureResponseVo(e);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}

		}
		return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(), "Invalid file path", null);
	}

	private OrderVo loadFromArr(String[] arr) {
		OrderVo orderVo = new OrderVo();
		orderVo.setOrderId(arr[AmazonOrderColIndexEnum.OrderId.getIndex()]);
		orderVo.setMerchantOrderId(arr[AmazonOrderColIndexEnum.MerchantOrderId.getIndex()]);
		orderVo.setPurchaseDate(
				arr[AmazonOrderColIndexEnum.PurchaseDate.getIndex()].replaceAll("T", " ").substring(0, 19));
		orderVo.setLastUpdatedDate(
				arr[AmazonOrderColIndexEnum.LastUpdatedDate.getIndex()].replaceAll("T", " ").substring(0, 19));
		orderVo.setOrderStatus(arr[AmazonOrderColIndexEnum.OrderStatus.getIndex()]);
		orderVo.setFulfillmentChannel(arr[AmazonOrderColIndexEnum.FulfillmentChannel.getIndex()]);
		orderVo.setSalesChannel(arr[AmazonOrderColIndexEnum.SalesChannel.getIndex()]);
		orderVo.setSku(arr[AmazonOrderColIndexEnum.Sku.getIndex()]);
		orderVo.setAsin(arr[AmazonOrderColIndexEnum.Asin.getIndex()]);
		orderVo.setItemStatus(arr[AmazonOrderColIndexEnum.ItemStatus.getIndex()]);
		orderVo.setQuantity(Integer.valueOf(arr[AmazonOrderColIndexEnum.Quantity.getIndex()]));
		orderVo.setCurrency(arr[AmazonOrderColIndexEnum.Currency.getIndex()]);
		
		orderVo.setTargetSku(TransformUtils.removeCountryCodeAndFBMSuffixFromSku(orderVo.getSku()));

		// Set item price
		BigDecimal itemPrice = transformString2Decimal(arr[AmazonOrderColIndexEnum.ItemPrice.getIndex()]);
		BigDecimal shippingPrice = transformString2Decimal(arr[AmazonOrderColIndexEnum.ShippingPrice.getIndex()]);
		BigDecimal itemPromotionDiscount = transformString2Decimal(
				arr[AmazonOrderColIndexEnum.ItemPromotionDiscount.getIndex()]);
		BigDecimal shipPromotionDiscount = transformString2Decimal(
				arr[AmazonOrderColIndexEnum.ShipPromotionDiscount.getIndex()]);

		orderVo.setItemPrice(itemPrice);
		orderVo.setShippingPrice(shippingPrice);
		orderVo.setItemPromotionDiscount(itemPromotionDiscount);
		orderVo.setShipPromotionDiscount(shipPromotionDiscount);

		orderVo.setShipCountry(arr[AmazonOrderColIndexEnum.ShipCountry.getIndex()]);

		return orderVo;
	}

	private BigDecimal transformString2Decimal(String str) {
		BigDecimal decimal = null;
		if (StringUtils.isBlank(str)) { // Cancelled order
			decimal = BigDecimal.ZERO;
		} else {
			decimal = BigDecimal.valueOf(Double.valueOf(str));
		}
		return decimal;
	}

	/**
	 * 如果订单不存在，新增； 反之，更新(以order id & asin为维度更新)
	 * 
	 * @param filePath
	 * @return
	 */
	public ResponseVo insertOrUpdateOrder(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {
			// Parse .txt file

			// Read the head
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

				String line = null;

				line = reader.readLine();
				String[] arr = line.split("\t", -1);
				if (null == arr || arr.length != numCols) {
					return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(),
							"Incorrect number of columns", null);
				}

				// Process the orders one at a time
				OrderVo orderVo = null;
				List<OrderVo> orders = new ArrayList<>();
				List<OrderVo> tmpOrders = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					arr = line.split("\t", -1);
					orderVo = loadFromArr(arr);

					tmpOrders = orderDao.queryOrder(orderVo);
					if (!tmpOrders.isEmpty()) {
						orderDao.updateOrder(orderVo);
					} else {
						orders.add(orderVo);
					}

				}

				if (!orders.isEmpty()) {
					orderDao.insertOrder(orders);
				}

				reader.close();
				return ResponseUtils.getSuccessResponseVo(null);

			} catch (Exception e) {
				return ResponseUtils.getFailureResponseVo(e);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}

		}
		return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(), "Invalid file path", null);
	}

	/**
	 * 计算fromDate->toDate自发货/FBA的相关财务数据，包括已完成订单的利润
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param fulfillmentChannel
	 * @return
	 */
	public ResponseVo queryNetProfit(String fromDate, String toDate, Integer fulfillmentChannel) {
		if (null == fulfillmentChannel) {
			// 默认FBA发货
			fulfillmentChannel = FulfillmentChannelEnum.Amazon.getCode();
		}

		// 计算所有SKU采购价
		Map<String, BigDecimal> allCosts = new HashMap<>();
		List<ProductAggregateInfoVo> list = productDao.getProductDetails(null);
		for (ProductAggregateInfoVo vo : list) {
			allCosts.put(vo.getSku(), vo.getCost());
		}

		// 计算所有订单的
		List<OrderInfoWithFeeVo> orderInfoWithFeeVos = orderDao.queryOrderInfoWithFee(fromDate, toDate,
				fulfillmentChannel);
		Map<String, Object> resultMap = calculate(orderInfoWithFeeVos, allCosts);
		return ResponseUtils.getSuccessResponseVo(resultMap);
	}

	private Map<String, Object> calculate(List<OrderInfoWithFeeVo> orderInfoWithFeeVos,
			Map<String, BigDecimal> allCosts) {
		Map<String, Object> resultMap = new HashMap<>();

		String sku = null;
		BigDecimal cost = null;
		BigDecimal totalNetProfitInCNY = BigDecimal.ZERO;
		BigDecimal totalSalePriceInCNY = BigDecimal.ZERO;
		BigDecimal currSalePriceInCNY4OneProduct = null;
		BigDecimal currNetProfitInCNY4OneProduct = null;
		Map<String, BigDecimal> tmpMap = null;

		for (OrderInfoWithFeeVo vo : orderInfoWithFeeVos) {
			// find the cost
			sku = vo.getSku();
			if (ifContainsCountryAbbr(sku)) {
				sku = sku.substring(0, 1) + sku.substring(3);
				cost = allCosts.get(sku);
				if (null == cost) {
					System.err.println("sku has no cost: " + sku);
					break;
				}

				try {
					tmpMap = querySalePriceAndNetProfitInCNY4OneProduct(vo.getItemPrice(), vo.getCurrency(), cost,
							vo.getFee(), new BigDecimal(0.15));
				} catch (Exception ex) {
					System.err.println(vo.getItemPrice() + "," + vo.getCurrency() + "," + cost + "," + vo.getFee());
					break;
				}

				currSalePriceInCNY4OneProduct = tmpMap.get("salePriceInCNY");
				currNetProfitInCNY4OneProduct = tmpMap.get("netProfitInCNY");
				totalSalePriceInCNY = totalSalePriceInCNY
						.add(currSalePriceInCNY4OneProduct.multiply(new BigDecimal(vo.getQuantity())));
				totalNetProfitInCNY = totalNetProfitInCNY
						.add(currNetProfitInCNY4OneProduct.multiply(new BigDecimal(vo.getQuantity())));
			}
		}

		resultMap.put("totalSalePrice", totalSalePriceInCNY.setScale(2, RoundingMode.HALF_DOWN));
		resultMap.put("totalNetProfit", totalNetProfitInCNY.setScale(2, RoundingMode.HALF_DOWN));
		resultMap.put("EURCNY", ConstantUtils.ExRate.get("EUR").setScale(2, RoundingMode.HALF_DOWN));
		resultMap.put("GBPCNY", ConstantUtils.ExRate.get("GBP").setScale(2, RoundingMode.HALF_DOWN));
		resultMap.put("USDCNY", ConstantUtils.ExRate.get("USD").setScale(2, RoundingMode.HALF_DOWN));

		return resultMap;
	}

	Boolean ifContainsCountryAbbr(String sku) {
		String subString = sku.substring(1, 3);
		if (subString.equalsIgnoreCase("UK") || subString.equalsIgnoreCase("DE") || subString.equalsIgnoreCase("FR")
				|| subString.equalsIgnoreCase("IT") || subString.equalsIgnoreCase("ES")
				|| subString.equalsIgnoreCase("US") || subString.equalsIgnoreCase("CA")) {
			return true;
		}

		return false;
	}

	private Map<String, BigDecimal> querySalePriceAndNetProfitInCNY4OneProduct(BigDecimal salePrice, String currency,
			BigDecimal cost, BigDecimal fee, BigDecimal commissionRate) {
		Map<String, BigDecimal> resultMap = new HashMap<>();

		BigDecimal salePriceInCNY = salePrice.multiply(ConstantUtils.ExRate.get(currency));
		BigDecimal netProfitInCNY = salePrice.multiply(BigDecimal.ONE.subtract(commissionRate)).subtract(fee)
				.multiply(ConstantUtils.ExRate.get(currency)).subtract(cost);

		resultMap.put("salePriceInCNY", salePriceInCNY);
		resultMap.put("netProfitInCNY", netProfitInCNY);

		return resultMap;
	}

	public ResponseVo querySales4OneWeek() {
		return null;
	}

	/**
	 * Download Flat File from Reports>Payments>All Statements
	 * (https://sellercentral.amazon.co.uk/gp/payments-account/past-settlements.html)
	 * 
	 * Note: Currency
	 * 
	 * This method only calculates the Amazon Fee(FBA+Commission) for each
	 * order, skipping the principal & shipping & promotion.
	 */
	public ResponseVo analyzePaymentDetail(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {

			BufferedReader reader = null;
			String line = null;
			String arr[] = null;

			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

				// Read the first line: header
				line = reader.readLine();

				arr = line.split("\t", -1);

				// For Germany, there are 36 columns, 3 more columns than that
				// of UK
				if (null == arr) {
					return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(),
							"Incorrect number of columns", null);
				}

				// Skip the second line
				line = reader.readLine();

				// Store the fee details of the order
				// Data structure(Map of map of map): Order Id -> SKU -> Fee
				// Type -> Fee Amount
				Map<String, Map<String, Map<String, Object>>> normalOrderFeeDetails = new HashMap<>();
				Map<String, Map<String, Map<String, Object>>> refundedOrderFeeDetails = new HashMap<>();

				// Read the content
				String transactionType = null; // 7th column
				String orderId = null; // 8th column
				String sku = null; // 22nd column
				String feeType = null; // 26th column
				double feeAmount = 0; // 27th column

				Integer transactionTypeIndex = 6;
				Integer orderIdIndex = 7;
				Integer skuIndex = 21;
				Integer feeTypeIndex = 25;
				Integer feeAmountIndex = 26;

				while ((line = reader.readLine()) != null) {
					arr = line.split("\t", -1);

					orderId = arr[orderIdIndex];
					feeType = arr[feeTypeIndex];

					// If the order id or fee type is blank, skip the line
					if (StringUtils.isBlank(orderId) || StringUtils.isBlank(feeType)) {
						continue;
					}

					transactionType = arr[transactionTypeIndex];
					sku = arr[skuIndex];
					feeAmount = transformCurrencyToDecimal(arr[feeAmountIndex]);

					if ("Refund".equals(transactionType)) {
						buildOrderFeeDetailMap(refundedOrderFeeDetails, orderId, sku, feeType, feeAmount);

					} else {
						buildOrderFeeDetailMap(normalOrderFeeDetails, orderId, sku, feeType, feeAmount);
					}

				}

				processNormalOrderFeeDetail(normalOrderFeeDetails);
				processRefundedOrderFeeDetail(refundedOrderFeeDetails);

				reader.close();
				return ResponseUtils.getSuccessResponseVo(null);

			} catch (Exception e) {
				return ResponseUtils.getFailureResponseVo(e);
			} finally {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}

		}

		return ResponseUtils.getResponseVo(false, ResponseCodeEnum.FAILURE.getCode(), "Invalid file path", null);

	}

	private void processRefundedOrderFeeDetail(Map<String, Map<String, Map<String, Object>>> refundedOrderFeeDetails) {
		Double refundCommission = null;
		String postedDate = null;
		Map<String, Map<String, Object>> feeDetailsForOneOrder = null;
		Map<String, Object> feeDetailsForOneOrderOneSku = null;
		Object data = null;

		for (String orderId : refundedOrderFeeDetails.keySet()) {
			feeDetailsForOneOrder = refundedOrderFeeDetails.get(orderId);
			for (String sku : feeDetailsForOneOrder.keySet()) {
				refundCommission = 0.0;
				feeDetailsForOneOrderOneSku = feeDetailsForOneOrder.get(sku);
				for (String type : feeDetailsForOneOrderOneSku.keySet()) {
					data = feeDetailsForOneOrderOneSku.get(type);
					// if (type.equals("posted-date")) {
					// postedDate = ((String) data).replaceAll("T", "
					// ").substring(0, 19);
					// }
					if (type.equals("RefundCommission")) {
						refundCommission = (Double) data;
					}

				}

				orderDao.updateAmazonFee4RefundedOrder(orderId, sku, postedDate, refundCommission);

			}
		}
	}

	private void processNormalOrderFeeDetail(Map<String, Map<String, Map<String, Object>>> normalOrderFeeDetails) {

		Double fbaFee = null;
		Double commission = null;
		Double shippingChargeback = null;
		Map<String, Map<String, Object>> feeDetailsForOneOrder = null;
		Map<String, Object> feeDetailsForOneOrderOneSku = null;
		Object data = null;

		for (String orderId : normalOrderFeeDetails.keySet()) {
			feeDetailsForOneOrder = normalOrderFeeDetails.get(orderId);
			for (String sku : feeDetailsForOneOrder.keySet()) {
				fbaFee = 0.0;
				commission = 0.0;
				shippingChargeback = 0.0;

				feeDetailsForOneOrderOneSku = feeDetailsForOneOrder.get(sku);
				for (String type : feeDetailsForOneOrderOneSku.keySet()) {
					data = feeDetailsForOneOrderOneSku.get(type);

					if (type.equals("FBAPerUnitFulfillmentFee")) {
						fbaFee = (Double) data;
					}
					if (type.equals("Commission")) {
						commission = (Double) data;
					}
					if (type.equals("ShippingChargeback")) {
						shippingChargeback = (Double) data;
					}
				}

				orderDao.updateAmazonFee4NormalOrder(orderId, sku, fbaFee, commission, shippingChargeback);

			}
		}

	}

	/**
	 * 将金额从欧元的形式转换为十进制的形式，负数转换为正数
	 * 
	 * @param total
	 * @return
	 */
	private double transformCurrencyToDecimal(String total) {
		if (StringUtils.isBlank(total)) {
			return 0;
		}
		double totalInDecimal = Double.valueOf(total.replace(",", "."));
		if (totalInDecimal < 0) {
			totalInDecimal = totalInDecimal * (-1);
		}
		return totalInDecimal;
	}

	/**
	 * 构建订单费用详情Map型数据
	 * 
	 * @param orderFeeDetails
	 * @param orderId
	 * @param sku
	 * @param type
	 * @param data
	 */
	private void buildOrderFeeDetailMap(Map<String, Map<String, Map<String, Object>>> orderFeeDetails, String orderId,
			String sku, String type, Object data) {

		Map<String, Map<String, Object>> feeDetailsForOneOrder = new HashMap<>();
		Map<String, Object> feeDetailsForOneOrderOneSku = new HashMap<>();
		Object val = null;

		if (orderFeeDetails.containsKey(orderId)) {
			feeDetailsForOneOrder = orderFeeDetails.get(orderId);
			if (feeDetailsForOneOrder.containsKey(sku)) {
				feeDetailsForOneOrderOneSku = feeDetailsForOneOrder.get(sku);
				if (feeDetailsForOneOrderOneSku.containsKey(type)) {
					val = feeDetailsForOneOrderOneSku.get(type);
					feeDetailsForOneOrderOneSku.put(type, (double) data + (double) val);
				} else {
					feeDetailsForOneOrderOneSku.put(type, data);
				}

			} else {
				feeDetailsForOneOrderOneSku = new HashMap<>();
				feeDetailsForOneOrderOneSku.put(type, data);
				feeDetailsForOneOrder.put(sku, feeDetailsForOneOrderOneSku);
			}
		} else {
			feeDetailsForOneOrderOneSku = new HashMap<>();
			feeDetailsForOneOrderOneSku.put(type, data);
			feeDetailsForOneOrder = new HashMap<>();
			feeDetailsForOneOrder.put(sku, feeDetailsForOneOrderOneSku);
			orderFeeDetails.put(orderId, feeDetailsForOneOrder);
		}
	}

	protected Map<String, BigDecimal> getExchangeRates() {
		Map<String, BigDecimal> exRates = new HashMap<>();

		// Temporarily set default
		exRates.put("GBP", new BigDecimal(8.94));
		exRates.put("EUR", new BigDecimal(7.73));
		exRates.put("USD", new BigDecimal(6.89));
		exRates.put("CAD", new BigDecimal(5.10));
		exRates.put("JPY", new BigDecimal(0.06));

		return exRates;
	}

	/**
	 * Update cost and first trip fee in orders table from product table
	 * 
	 * @return
	 */
	public ResponseVo updateCostFirstTripFee() {

		// Read cost and first trip fee from product table
		List<ProductBaseInfoVo> products = productDao.getProductBaseInfo(null);
		Map<String, ProductBaseInfoVo> productMap = new HashMap<>();
		for (ProductBaseInfoVo vo : products) {
			productMap.put(vo.getSku(), vo);
		}

		// Read orders
		List<OrderVo> orders = orderDao.queryOrder(new OrderVo());

		// Update orders
		ProductBaseInfoVo productBaseInfoVo = null;
		String sku = null;
		for (OrderVo order : orders) {
			sku = removeCountryCodeAndFBMSuffixFromSku(order.getSku());
			if (productMap.containsKey(sku)) {
				productBaseInfoVo = productMap.get(sku);
				orderDao.updateCostFirstTripFee(order.getOrderId(), order.getSku(), productBaseInfoVo.getCost(),
						productBaseInfoVo.getFirstTripFee());
			}
		}

		return null;

	}

	private String removeCountryCodeAndFBMSuffixFromSku(String sku) {
		sku = sku.replace("-FBM", "");
		if (ifContainsCountryAbbr(sku)) {
			sku = sku.substring(0, 1) + sku.substring(3);

		}
		return sku;
	}

	public ResponseVo computeOrderFinancialData(String fromDate, String toDate, Integer fulfillmentChannel) {
		if (null == fulfillmentChannel) {
			// 默认FBA发货
			fulfillmentChannel = FulfillmentChannelEnum.Amazon.getCode();
		}

		// Read orders
		List<OrderVo> orders = orderDao.queryOrder(fromDate, toDate, fulfillmentChannel);

		Map<String, Object> resultMap = new HashMap<>();

		ExRateMapper exRateMapper = new ExRateMapper(financeService.queryExRate());

		SalesData4OneCountry salesData4GB = new SalesData4OneCountry("GB");
		SalesData4OneCountry salesData4DE = new SalesData4OneCountry("DE");
		SalesData4OneCountry salesData4FR = new SalesData4OneCountry("FR");
		SalesData4OneCountry salesData4IT = new SalesData4OneCountry("IT");
		SalesData4OneCountry salesData4ES = new SalesData4OneCountry("ES");
		List<SalesData4OneCountry> salesData = Arrays.asList(salesData4GB, salesData4DE, salesData4FR, salesData4IT,
				salesData4ES);

		if (null != orders && !orders.isEmpty()) {
			for (OrderVo orderVo : orders) {
				switch (orderVo.getShipCountry()) {
				case "GB":
					addOrderFinancialData4OneCountry(salesData4GB, orderVo, exRateMapper);
					break;
				case "DE":
					addOrderFinancialData4OneCountry(salesData4DE, orderVo, exRateMapper);
					break;
				case "FR":
					addOrderFinancialData4OneCountry(salesData4FR, orderVo, exRateMapper);
					break;
				case "IT":
					addOrderFinancialData4OneCountry(salesData4IT, orderVo, exRateMapper);
					break;
				case "ES":
					addOrderFinancialData4OneCountry(salesData4ES, orderVo, exRateMapper);
					break;
				default:
					break;
				}

			}
		}

		resultMap.put("exRate", exRateMapper.getExRateVo());
		resultMap.put("total", totalize(salesData));
		resultMap.put("details", salesData);
		return ResponseUtils.getSuccessResponseVo(resultMap);
	}

	private Object totalize(List<SalesData4OneCountry> salesData) {
		SalesData4OneCountry salesData4Euro = new SalesData4OneCountry("EURO");

		for (SalesData4OneCountry ele : salesData) {
			salesData4Euro.addToNumOfOrders(ele.getNumOfOrders());
			salesData4Euro.addToNumOfItems(ele.getNumOfItems());
			salesData4Euro.addToGrossSales(ele.getGrossSales());
			salesData4Euro.addToGrossProfit(ele.getGrossProfit());
			salesData4Euro.addToGrossCost(ele.getGrossCost());
			salesData4Euro.addToGrossFirstTripFee(ele.getGrossFirstTripFee());
			salesData4Euro.addToGrossFBAFee(ele.getGrossFBAFee());
			salesData4Euro.addToGrossCommission(ele.getGrossCommission());
		}

		return salesData4Euro;
	}

	private void addOrderFinancialData4OneCountry(SalesData4OneCountry salesData4OneCountry, OrderVo orderVo,
			ExRateMapper exRateMapper) {
		BigDecimal exRate = exRateMapper.getExRateToCNY(orderVo.getShipCountry()).multiply(new BigDecimal(0.99)).setScale(2, RoundingMode.HALF_UP);
		BigDecimal sales = orderVo.getItemPrice().multiply(exRate);

		BigDecimal cost = orderVo.getCost();
		BigDecimal firsTripFee = orderVo.getFirstTripFee();
		BigDecimal fbaFee = orderVo.getFbaFee().multiply(exRate);
		BigDecimal commission = orderVo.getCommission().multiply(exRate);
		BigDecimal profit = sales.subtract(cost).subtract(firsTripFee).subtract(fbaFee).subtract(commission);

		salesData4OneCountry.addToNumOfOrders(1);
		salesData4OneCountry.addToNumOfItems(orderVo.getQuantity());
		salesData4OneCountry.addToGrossSales(sales);
		salesData4OneCountry.addToGrossProfit(profit);
		salesData4OneCountry.addToGrossCost(cost);
		salesData4OneCountry.addToGrossFirstTripFee(firsTripFee);
		salesData4OneCountry.addToGrossFBAFee(fbaFee);
		salesData4OneCountry.addToGrossCommission(commission);
	}
}
