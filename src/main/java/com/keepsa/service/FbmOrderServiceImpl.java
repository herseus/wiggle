package com.keepsa.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.keepsa.dao.FbmOrderDaoImpl;
import com.keepsa.enumeration.FBMOrderColumnIndexEnum;
import com.keepsa.enumeration.FBMOrderStatusEnum;
import com.keepsa.enumeration.FbmOrderFileColumnEnum;
import com.keepsa.pojo.FbmOrderVo;
import com.keepsa.pojo.ResponseVo;
import com.keepsa.pojo.UnshippedFBMOrderVo;
import com.keepsa.utils.DateUtils;
import com.keepsa.utils.ExceptionUtils;
import com.keepsa.utils.ResponseUtils;
import com.keepsa.utils.TransformUtils;

@Service
public class FbmOrderServiceImpl {
	@Resource
	private FbmOrderDaoImpl fbmOrderDao;

	private static String hzj_img_base_url = "http://amzimage-1253899278.cossh.myqcloud.com/";

	private static String hjc_img_base_url = "http://1amzimages-1253487862.cossh.myqcloud.com/";

	/*
	 * Import order reports (ORDERS -> Order Reports -> New Orders) which
	 * contain all of the sell-fulfilled orders that you have received over the
	 * number of days that you select. This includes orders that you have
	 * cancelled or confirmed as shipped.
	 * 
	 * ORDERS -> Order Reports -> Unshipped Orders is only available once every
	 * four hours.
	 * 
	 */
	public void importOrderReports(String content) {
		if (StringUtils.isNotBlank(content)) {
			BufferedReader reader = TransformUtils.getBufferedReaderFromString(content);
			importOrderReportsWithBufferedReader(reader);
		}
	}

	/*
	 * Import Order Reports for FBM orders
	 */
	public void importOrderReportsWithFilePath(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {

			BufferedReader reader = null;

			try {
				reader = TransformUtils.getBufferedReaderFromFilePath(filePath);
				importOrderReportsWithBufferedReader(reader);

			} catch (Exception ex) {

			}
		}
	}

	/*
	 * Import Order Reports for FBM orders
	 */
	public void importOrderReportsWithBufferedReader(BufferedReader reader) {
		if (null != reader) {

			String line = null;
			String arr[] = null;
			FbmOrderVo fbmOrderVo = null;
			List<FbmOrderVo> newFBMOrderVos = new ArrayList<>();

			try {
				// Skip the first line and read the content
				line = reader.readLine();

				while ((line = reader.readLine()) != null) {

					arr = line.split("\t", -1);

					fbmOrderVo = transformArr2FBMOrderVo(arr);

					if (null != fbmOrderVo && !ifExistsFBMOrder(fbmOrderVo)) {
						newFBMOrderVos.add(fbmOrderVo);
					}

				}

				fbmOrderDao.insert(newFBMOrderVos);

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

	private FbmOrderVo transformArr2FBMOrderVo(String arr[]) {
		try {
			FbmOrderVo vo = new FbmOrderVo();
			vo.setOrderStatus(FBMOrderStatusEnum.Unshipped.getStatus());

			vo.setOrderId(arr[FBMOrderColumnIndexEnum.OrderId.getIndex()]);
			vo.setPurchaseDate(
					TransformUtils.transformUTCTimeToNormalTime(arr[FBMOrderColumnIndexEnum.PurchaseDate.getIndex()]));
			vo.setBuyerName(arr[FBMOrderColumnIndexEnum.BuyerName.getIndex()]);
			vo.setSku(arr[FBMOrderColumnIndexEnum.Sku.getIndex()]);
			vo.setTargetSku(
					TransformUtils.removeCountryCodeAndFBMSuffixFromSku(arr[FBMOrderColumnIndexEnum.Sku.getIndex()]));
			vo.setQuantityPurchased(Integer.valueOf(arr[FBMOrderColumnIndexEnum.QuantityPurchased.getIndex()]));
			vo.setRecipientName(arr[FBMOrderColumnIndexEnum.RecipientName.getIndex()]);
			vo.setShipAddress1(arr[FBMOrderColumnIndexEnum.ShipAddress1.getIndex()]);
			vo.setShipAddress2(arr[FBMOrderColumnIndexEnum.ShipAddress2.getIndex()]);
			vo.setShipAddress3(arr[FBMOrderColumnIndexEnum.ShipAddress3.getIndex()]);
			vo.setShipCity(arr[FBMOrderColumnIndexEnum.ShipCity.getIndex()]);
			vo.setShipState(arr[FBMOrderColumnIndexEnum.ShipState.getIndex()]);
			vo.setShipCountry(arr[FBMOrderColumnIndexEnum.ShipCountry.getIndex()]);
			vo.setShipPostalCode(arr[FBMOrderColumnIndexEnum.ShipPostalCode.getIndex()]);
			vo.setShipPhoneNumber(arr[FBMOrderColumnIndexEnum.ShipPhoneNumber.getIndex()]);

			return vo;
		} catch (Exception ex) {
			return null;
		}
	}

	boolean ifExistsFBMOrder(FbmOrderVo fbmOrderVo) {
		List<FbmOrderVo> list = fbmOrderDao.query(fbmOrderVo);
		if (null == list || list.isEmpty()) {
			return false;
		}
		return true;
	}

	public ResponseVo setUnshippedFbmOrderShipped() {
		try {
			int numOfUpdatedOrders = fbmOrderDao.setUnshippedFbmOrderShipped();
			return ResponseUtils.getSuccessResponseVo(numOfUpdatedOrders + "个订单状态被更新");
		} catch (Exception e) {
			return ResponseUtils.getFailureResponseVo(e.getMessage());

		}
	}

	public ResponseVo queryUnshippedFbmOrders() {
		try {
			return ResponseUtils.getSuccessResponseVo(fbmOrderDao.queryUnshippedFbmOrders());
		} catch (Exception e) {
			return ResponseUtils.getFailureResponseVo(e.getMessage());
		}
	}

	/**
	 * 生成自发货清单（*.xlsx工作簿）
	 * 
	 * @param response
	 */
	public void generateUnshippedFbmOrderXlsxFile(HttpServletResponse response) {

		Workbook wb = new XSSFWorkbook();
		String sheet1Name = "自发货清单";
		@SuppressWarnings("unused")
		Sheet sheet1 = wb.createSheet(sheet1Name);

		// 保存为Excel文件
		FileOutputStream out = null;
		Row row = null;
		File outputFile = null;

		String fileName = "FbmOrders-" + DateUtils.getTodayLiteral() + ".xlsx";

		try {
			outputFile = new File(fileName);

			out = new FileOutputStream(outputFile);
			Integer cnt = 0;

			row = wb.getSheet(sheet1Name).createRow(cnt);

			createCell(FbmOrderFileColumnEnum.OrderId.getName(), wb, row, FbmOrderFileColumnEnum.OrderId.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.PurchaseDate.getName(), wb, row,
					FbmOrderFileColumnEnum.PurchaseDate.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.Sku.getName(), wb, row, FbmOrderFileColumnEnum.Sku.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Image.getName(), wb, row, FbmOrderFileColumnEnum.Image.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Title.getName(), wb, row, FbmOrderFileColumnEnum.Title.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Qty.getName(), wb, row, FbmOrderFileColumnEnum.Qty.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Status.getName(), wb, row, FbmOrderFileColumnEnum.Status.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.RecipientName.getName(), wb, row,
					FbmOrderFileColumnEnum.RecipientName.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.ShipAddress.getName(), wb, row,
					FbmOrderFileColumnEnum.ShipAddress.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.ShipCity.getName(), wb, row, FbmOrderFileColumnEnum.ShipCity.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.ShipState.getName(), wb, row, FbmOrderFileColumnEnum.ShipState.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.ShipPostalCode.getName(), wb, row,
					FbmOrderFileColumnEnum.ShipPostalCode.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.ShipCountry.getName(), wb, row,
					FbmOrderFileColumnEnum.ShipCountry.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.ShipPhoneNumber.getName(), wb, row,
					FbmOrderFileColumnEnum.ShipPhoneNumber.getIndex(), CellStyle.ALIGN_CENTER,
					CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.CustomsName.getName(), wb, row,
					FbmOrderFileColumnEnum.CustomsName.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.HSCode.getName(), wb, row, FbmOrderFileColumnEnum.HSCode.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Cost.getName(), wb, row, FbmOrderFileColumnEnum.Cost.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.Weight.getName(), wb, row, FbmOrderFileColumnEnum.Weight.getIndex(),
					CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);
			createCell(FbmOrderFileColumnEnum.DeliveryMethod.getName(), wb, row,
					FbmOrderFileColumnEnum.DeliveryMethod.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);
			createCell(FbmOrderFileColumnEnum.TrackingId.getName(), wb, row,
					FbmOrderFileColumnEnum.TrackingId.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
					true);

			wb.getSheet(sheet1Name).setDefaultColumnWidth(15);
			wb.getSheet(sheet1Name).setDefaultRowHeight((short) 950);

			String orderId = null;
			String mainImageUrl = null;
			String shipAddress = null;

			List<UnshippedFBMOrderVo> unshippedFBMOrders = fbmOrderDao.queryUnshippedFBMOrder();

			// If one order has more than one sku, the order ids except first
			// one are omitted
			String lastOrderId = StringUtils.EMPTY;

			for (UnshippedFBMOrderVo unshippedFBMOrderVo : unshippedFBMOrders) {

				row = wb.getSheet(sheet1Name).createRow(cnt + 1);

				// Set the Order Id
				orderId = unshippedFBMOrderVo.getOrderId();
				if (orderId.equals(lastOrderId)) {
					orderId = StringUtils.EMPTY;
				}
				createCell(orderId, wb, row, FbmOrderFileColumnEnum.OrderId.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the purchase date
				createCell(unshippedFBMOrderVo.getPurchaseDate(), wb, row,
						FbmOrderFileColumnEnum.PurchaseDate.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the target SKU
				createCell(unshippedFBMOrderVo.getSku(), wb, row, FbmOrderFileColumnEnum.Sku.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the image
				// FileInputStream jpeg =
				// newFileInputStream("C:\\Users\\huangzejun\\Desktop\\AC2A12-02.jpg");
				mainImageUrl = unshippedFBMOrderVo.getMainImageUrl();
				if (StringUtils.isBlank(mainImageUrl)) {
					mainImageUrl = hzj_img_base_url + unshippedFBMOrderVo.getSku() + ".jpg";
				}
				URL url = new URL(mainImageUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5 * 1000);
				InputStream inStream = conn.getInputStream();// 通过输入流获取图片数
				byte[] bytes = IOUtils.toByteArray(inStream);

				int pictureIndex = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
				inStream.close();

				XSSFCreationHelper helper = (XSSFCreationHelper) wb.getCreationHelper();

				XSSFDrawing patriarch = ((XSSFSheet) wb.getSheet(sheet1Name)).createDrawingPatriarch();

				XSSFClientAnchor clientAnchor = helper.createClientAnchor();

				clientAnchor.setCol1(FbmOrderFileColumnEnum.Image.getIndex());
				clientAnchor.setRow1(cnt + 1);
				// clientAnchor.setDx1(50);
				// clientAnchor.setDy1(50);

				XSSFPicture picture = patriarch.createPicture(clientAnchor, pictureIndex);
				picture.resize();

				// Set the title
				createCell(unshippedFBMOrderVo.getTitle(), wb, row, FbmOrderFileColumnEnum.Title.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the number
				createCell(unshippedFBMOrderVo.getQuantityPurchased().toString(), wb, row,
						FbmOrderFileColumnEnum.Qty.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the order status
				createCell("待发货", wb, row, FbmOrderFileColumnEnum.Status.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the recipient name
				createCell(unshippedFBMOrderVo.getRecipientName(), wb, row,
						FbmOrderFileColumnEnum.RecipientName.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the ship address
				shipAddress = TransformUtils.getAmazonAddress(unshippedFBMOrderVo.getShipAddress1(),
						unshippedFBMOrderVo.getShipAddress2(), unshippedFBMOrderVo.getShipAddress3(),
						unshippedFBMOrderVo.getShipPostalCode(), unshippedFBMOrderVo.getShipCity(),
						unshippedFBMOrderVo.getShipState(), unshippedFBMOrderVo.getShipCountry());
				createCell(shipAddress, wb, row, FbmOrderFileColumnEnum.ShipAddress.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the ship city
				createCell(unshippedFBMOrderVo.getShipCity(), wb, row, FbmOrderFileColumnEnum.ShipCity.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the ship state
				createCell(unshippedFBMOrderVo.getShipState(), wb, row, FbmOrderFileColumnEnum.ShipState.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the ship postal code
				createCell(unshippedFBMOrderVo.getShipPostalCode(), wb, row,
						FbmOrderFileColumnEnum.ShipPostalCode.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the ship country
				createCell(unshippedFBMOrderVo.getShipCountry(), wb, row, FbmOrderFileColumnEnum.ShipCountry.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the ship phone number
				createCell(unshippedFBMOrderVo.getShipPhoneNumber(), wb, row,
						FbmOrderFileColumnEnum.ShipPhoneNumber.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the customs name
				createCell(unshippedFBMOrderVo.getCustomsApplicationTitle(), wb, row,
						FbmOrderFileColumnEnum.CustomsName.getIndex(), CellStyle.ALIGN_CENTER,
						CellStyle.VERTICAL_CENTER, true);

				// Set the HS code
				createCell(unshippedFBMOrderVo.getHsCode(), wb, row, FbmOrderFileColumnEnum.HSCode.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				// Set the cost
				createCell((String.valueOf(Double.valueOf(unshippedFBMOrderVo.getCost()) / 8.0)), wb, row,
						FbmOrderFileColumnEnum.Cost.getIndex(), CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER,
						true);

				// Set the weight
				createCell(unshippedFBMOrderVo.getWeight(), wb, row, FbmOrderFileColumnEnum.Weight.getIndex(),
						CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER, true);

				cnt++;
			}

			wb.write(out);
			out.close();

			// Attach the Excel file to response
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + outputFile.getName());
			ServletOutputStream servletOutputStream = response.getOutputStream();

			try {
				BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(outputFile));

				byte[] buf = new byte[1024 * 10];
				int n;
				while ((n = inputStream.read(buf)) > 0) {
					servletOutputStream.write(buf, 0, n);
				}
			} catch (Exception e) {
				response.setStatus(404);
				System.out.println(ExceptionUtils.getExceptionStackTrace(e));
			} finally {
				servletOutputStream.flush();
			}

		} catch (Exception e) {
			System.out.println(ExceptionUtils.getExceptionStackTrace(e));
		}

	}

	protected void createCell(String str, Workbook wb, Row row, int column, short halign, short valign,
			boolean wrapText) {
		Cell cell = row.createCell(column);
		cell.setCellValue(str);
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cellStyle.setWrapText(wrapText);
		cell.setCellStyle(cellStyle);
	}
}
