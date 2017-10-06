package com.keepsa.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.BadBinaryOpValueExpException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Service;

import com.keepsa.dao.ProductDaoImpl;
import com.keepsa.enumeration.ResponseCodeEnum;
import com.keepsa.pojo.FbmOrderVo;
import com.keepsa.pojo.OrderVo;
import com.keepsa.pojo.ProductAggregateInfoVo;
import com.keepsa.pojo.ProductBaseInfoVo;
import com.keepsa.pojo.ProductDetailInfoVo;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.pojo.VendorDetailVo;
import com.keepsa.utils.DateUtils;
import com.keepsa.utils.ExceptionUtils;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.TransformUtils;

@Service
public class ProductServiceImpl {
	@Resource
	private ProductDaoImpl productDao;

	public List<ProductAggregateInfoVo> getProductAggregateInfo(String sku) {

		List<ProductAggregateInfoVo> products = null;

		products = productDao.getProductDetails(sku);

		if (null != products && !products.isEmpty()) {
			// Store all the skus
			List<String> skus = new ArrayList<>();

			// Store all mapping like sku->vendors
			Map<String, List<VendorDetailVo>> skuVendors = new HashMap<>();

			for (ProductAggregateInfoVo ele : products) {
				skus.add(ele.getSku());
			}

			List<Map<String, Object>> vendorWithSku = productDao.getVendorsWithSkus(skus);
			if (null != vendorWithSku && !vendorWithSku.isEmpty()) {
				String tmpSku = null;
				List<VendorDetailVo> tmpList = null;
				VendorDetailVo tmpVo = null;
				for (Map<String, Object> ele : vendorWithSku) {
					tmpSku = (String) ele.get("sku");
					tmpVo = transformMap2VendorDetailVo(ele);
					if (skuVendors.containsKey(tmpSku)) {
						skuVendors.get(tmpSku).add(tmpVo);
					} else {
						tmpList = new ArrayList<VendorDetailVo>() {
						};
						tmpList.add(tmpVo);
						skuVendors.put(tmpSku, tmpList);
					}
				}

				ProductAggregateInfoVo vo = null;
				for (int cnt = 0, size = products.size(); cnt < size; cnt++) {
					vo = products.get(cnt);
					vo.setVendors(skuVendors.get(vo.getSku()));
					products.set(cnt, vo);
				}
			}
		}

		return products;
	}

	private VendorDetailVo transformMap2VendorDetailVo(Map<String, Object> ele) {
		VendorDetailVo vo = new VendorDetailVo();
		vo.setVendorId((String) ele.get("vendorId"));
		vo.setName((String) ele.get("name"));
		vo.setLink((String) ele.get("link"));
		vo.setRank((String) ele.get("rank"));
		vo.setIsManufacturer((Boolean) ele.get("isManufacturer"));
		vo.setScale((String) ele.get("scale"));
		vo.setRegion((String) ele.get("region"));
		vo.setNote((String) ele.get("note"));
		return vo;
	}

	public List<ProductBaseInfoVo> getProductInfo(String sku) {

		List<ProductBaseInfoVo> products = new ArrayList<>();

		products = productDao.getProductBaseInfo(sku);

		return products;
	}

	public Object addProductInfo(ProductBaseInfoVo productBaseInfoVo) {
		Integer numOfAffectedRows = productDao.addProductInfo(productBaseInfoVo);
		if (numOfAffectedRows.compareTo(0) != 0) {
			return "Insert Success";
		}
		return "Insert Failure";
	}

	public ResponseVo createPurchasePlan(String filePath) {
		if (StringUtils.isNotEmpty(filePath)) {
			// Parse txt file
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

				String line = null;
				String arr[][] = new String[100][];
				int cnt = 0;
				List<String> skus = new ArrayList<>();

				// Process the data
				List<OrderVo> orders = new ArrayList<>();
				while ((line = reader.readLine()) != null) {
					arr[cnt] = line.split("[ *\t*]", -1);
					skus.add(arr[cnt][0]);
					cnt++;

				}

				// If the arr is not empty, create the purchase plan
				// Query the titles
				List<ProductBaseInfoVo> products = productDao.queryOrderLiteInfo(skus);
				Map<String, ProductBaseInfoVo> map = new HashMap<>();
				for (ProductBaseInfoVo ele : products) {
					map.put(ele.getSku(), ele);
				}

				// Create Excel file
				HSSFWorkbook wb = new HSSFWorkbook();
				@SuppressWarnings("unused")
				Sheet sheet1 = wb.createSheet("采购表");

				// 保存为Excel文件
				FileOutputStream out = null;
				Row row = null;
				Cell cell = null;

				out = new FileOutputStream(
						"C:\\Users\\huangzejun\\Desktop\\采购清单" + DateUtils.getTodayLiteral() + ".xls");
				row = wb.getSheet("采购表").createRow(0);
				cell = row.createCell(0);
				cell.setCellValue("SKU");
				cell = row.createCell(1);
				cell.setCellValue("FNSKU");
				cell = row.createCell(2);
				cell.setCellValue("Image");
				cell = row.createCell(3);
				cell.setCellValue("Title");
				cell = row.createCell(4);
				cell.setCellValue("Number");
				wb.getSheet("采购表").setDefaultColumnWidth(15);
				wb.getSheet("采购表").setDefaultRowHeight((short) 950);

				String sku = null;
				String title = null;
				String fnsku = null;

				for (int i = 0; i < cnt; i++) {

					row = wb.getSheet("采购表").createRow(i + 1);

					// Set the SKU
					cell = row.createCell(0);
					sku = arr[i][0];
					cell.setCellValue(sku);

					// Set the FNSKU
					cell = row.createCell(1);
					fnsku = map.get(sku).getFnsku();
					cell.setCellValue(fnsku);

					// Set the image
					// FileInputStream jpeg = new
					// FileInputStream("C:\\Users\\huangzejun\\Desktop\\AC2A12-02.jpg");
					URL url = new URL("http://amzimage-1253899278.cossh.myqcloud.com/" + arr[i][0] + ".jpg");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5 * 1000);
					InputStream inStream = conn.getInputStream();// 通过输入流获取图片数
					byte[] bytes = IOUtils.toByteArray(inStream);

					int pictureIndex = wb.addPicture(bytes, HSSFWorkbook.PICTURE_TYPE_JPEG);
					inStream.close();

					HSSFCreationHelper helper = (HSSFCreationHelper) wb.getCreationHelper();

					HSSFPatriarch patriarch = ((HSSFSheet) wb.getSheet("采购表")).createDrawingPatriarch();

					HSSFClientAnchor clientAnchor = helper.createClientAnchor();

					clientAnchor.setCol1(2);
					clientAnchor.setRow1(i + 1);
					// clientAnchor.setDx1(50);
					// clientAnchor.setDy1(50);

					HSSFPicture picture = patriarch.createPicture(clientAnchor, pictureIndex);
					picture.resize();

					// Set the title
					cell = row.createCell(3);
					title = map.get(sku).getTitle();
					cell.setCellValue(title);

					// Set the number
					cell = row.createCell(4);
					cell.setCellValue(Integer.valueOf(arr[i][1]));

				}

				wb.write(out);
				out.close();
				reader.close();
				return ResponseUtils.getSuccessResponseVo(null);

			} catch (Exception e) {
				System.out.println(ExceptionUtils.getExceptionStackTrace(e));
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

	public ProductDetailInfoVo getProductDetailInfo(String sku) {
		return productDao.getProductDetailInfo(sku);
	}

	public ResponseVo importProductFile(String content) {
		try {
			if (StringUtils.isNotBlank(content)) {
				BufferedReader reader = TransformUtils.getBufferedReaderFromString(content);
				if (null != reader) {

					String line = null;
					String arr[] = null;
					ProductDetailInfoVo productDetailInfoVo = null;

					try {

						// Read the first line: header
						line = reader.readLine();
						arr = line.split("\t", -1);
						String skuRegEx = "[A-Z0-9-]+";
						while (!arr[0].matches(skuRegEx)) {
							line = reader.readLine();
							arr = line.split("\t", -1);
						}

						List<ProductDetailInfoVo> newProductDetailInfoVos = new ArrayList<>();

						while ((line = reader.readLine()) != null) {
							arr = line.split("\t", -1);

							productDetailInfoVo = transformArr2ProductDetailInfoVo(arr);
							if (null != productDetailInfoVo && !ifExistsProduct(productDetailInfoVo)) {
								newProductDetailInfoVos.add(productDetailInfoVo);
							}

						}

						productDao.insert(newProductDetailInfoVos);

						reader.close();

					} catch (Exception ex) {
						System.out.println(ExceptionUtils.getExceptionStackTrace(ex));
					} finally {
						try {
							reader.close();
						} catch (IOException e) {
							System.out.println(ExceptionUtils.getExceptionStackTrace(e));
						}
					}

				}
			}

			return ResponseUtils.getSuccessResponseVo(null);
		} catch (Exception e) {
			return ResponseUtils.getFailureResponseVo(e.getMessage());
		}
	}

	private boolean ifExistsProduct(ProductDetailInfoVo productDetailInfoVo) {
		if (null != productDao.queryOrderLiteInfo(Arrays.asList(productDetailInfoVo.getSku()))) {
			return true;
		}
		return false;
	}

	private ProductDetailInfoVo transformArr2ProductDetailInfoVo(String[] arr) {
		ProductDetailInfoVo productDetailInfoVo = new ProductDetailInfoVo();

		productDetailInfoVo.setSku(arr[0]);
		productDetailInfoVo.setCategory(arr[1]);
		productDetailInfoVo.setTitle(arr[2]);
		productDetailInfoVo.setCustomsApplicationTitle(arr[3]);
//		productDetailInfoVo.setProductParameter(arr[5]);
		productDetailInfoVo.setEnglishTitle(arr[6]);
		productDetailInfoVo.setBulletPoint1(arr[7]);
		productDetailInfoVo.setBulletPoint2(arr[8]);
		productDetailInfoVo.setBulletPoint3(arr[9]);
		productDetailInfoVo.setBulletPoint4(arr[10]);
		productDetailInfoVo.setBulletPoint5(arr[11]);
		productDetailInfoVo.setProductDescription(arr[12]);
		productDetailInfoVo.setSearchTerms(arr[13]);
		productDetailInfoVo.setProductStatus(arr[14]);
		productDetailInfoVo.setMainImageUrl(arr[15]);
		productDetailInfoVo.setOtherImageUrl1(arr[16]);
		productDetailInfoVo.setOtherImageUrl2(arr[17]);
		productDetailInfoVo.setOtherImageUrl3(arr[18]);
		productDetailInfoVo.setOtherImageUrl4(arr[19]);
		productDetailInfoVo.setOtherImageUrl5(arr[20]);
		productDetailInfoVo.setOtherImageUrl6(arr[21]);
		productDetailInfoVo.setOtherImageUrl7(arr[22]);
		productDetailInfoVo.setOtherImageUrl8(arr[23]);
		productDetailInfoVo.setProductWeight(fromString2Decimal(arr[24]));
		productDetailInfoVo.setPackageWeight(fromString2Decimal(arr[25]));
		productDetailInfoVo.setLength(fromString2Decimal(arr[26]));
		productDetailInfoVo.setWidth(fromString2Decimal(arr[27]));
		productDetailInfoVo.setHeight(fromString2Decimal(arr[28]));
		productDetailInfoVo.setVolumeWeight(fromString2Decimal(arr[29]));

		productDetailInfoVo.setPriceUs(fromString2Decimal(arr[38]));
		productDetailInfoVo.setPriceCa(fromString2Decimal(arr[39]));
		productDetailInfoVo.setPriceDe(fromString2Decimal(arr[40]));
		productDetailInfoVo.setPriceFr(fromString2Decimal(arr[41]));
		productDetailInfoVo.setPriceIt(fromString2Decimal(arr[42]));
		productDetailInfoVo.setPriceEs(fromString2Decimal(arr[43]));
		productDetailInfoVo.setPriceUk(fromString2Decimal(arr[44]));
		
		productDetailInfoVo.setVendorName(arr[45]);
		productDetailInfoVo.setVendorLink(arr[46]);
		productDetailInfoVo.setCost(fromString2Decimal(arr[48]));
		
		return productDetailInfoVo;
	}
	
	private BigDecimal fromString2Decimal(String str) {
		BigDecimal bigDecimal = BigDecimal.ZERO;
		
		if (StringUtils.isNotBlank(str)) {
			try {
				bigDecimal = BigDecimal.valueOf(Double.valueOf(str));
			} catch (Exception e) {
				System.out.println(ExceptionUtils.getExceptionStackTrace(e));
			}
		}
		
		return bigDecimal;
	}
}
