package com.keepsa.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.keepsa.pojo.ProductAggregateInfoVo;
import com.keepsa.pojo.ProductBaseInfoVo;
import com.keepsa.pojo.ProductDetailInfoVo;

@Repository
public class ProductDaoImpl extends AbstractDao{

	public ProductAggregateInfoVo getProductAggregateInfo(String sku) {
		if (StringUtils.isEmpty(sku)) {
			return null;
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("sku", sku);
		ProductAggregateInfoVo vo = sstSlave.selectOne("productMapper.getProductAggregateInfo", param);
		if (null != vo) {		
			vo.setVendors(sstSlave.selectList("productMapper.getVendorsBySku", param));
		}
		
		return vo;
	}
	
	public List<ProductAggregateInfoVo> getProductDetails(String sku) {		
		Map<String, Object> param = new HashMap<>();
		param.put("sku", sku);
		return sstSlave.selectList("productMapper.getProductAggregateInfo", param);

	}

	public List<Map<String, Object>> getVendorsWithSkus(List<String> skus) {
		return sstSlave.selectList("productMapper.getVendorsWithSkus", skus);
	}

	public List<ProductBaseInfoVo> getProductBaseInfo(String sku) {
		Map<String, Object> param = new HashMap<>();
		param.put("sku", sku);
		return sstSlave.selectList("productMapper.getProductBaseInfo", param);
	}

	public Integer addProductInfo(ProductBaseInfoVo vo) {
		return sstMaster.insert("productMapper.addProductInfo", vo);
	}

	public List<ProductBaseInfoVo> queryOrderLiteInfo(List<String> skus) {
		return sstSlave.selectList("productMapper.queryOrderLiteInfo", skus);
	}

	public ProductDetailInfoVo getProductDetailInfo(String sku) {
		Map<String, Object> param = new HashMap<>();
		param.put("sku", sku);
		return sstSlave.selectOne("productMapper.getProductDetailInfo", param);
	}

	public Integer insert(List<ProductDetailInfoVo> newProductDetailInfoVos) {
		return sstMaster.insert("productMapper.insertProductDetailInfoVos", newProductDetailInfoVos);
	}
}
