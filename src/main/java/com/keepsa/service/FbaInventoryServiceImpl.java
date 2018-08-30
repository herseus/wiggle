package com.keepsa.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.keepsa.dao.FbaInventoryDaoImpl;
import com.keepsa.pojo.FbaInventoryVo;
import com.keepsa.utils.ExceptionUtils;
import com.keepsa.utils.TransformUtils;

@Service
public class FbaInventoryServiceImpl {
	@Resource
	private FbaInventoryDaoImpl fbaInventoryDao;

	// Manage Inventory File columns
	private static int sellerSkuIndex = 0;
	private static int fullfillmentChannelSkuIndex = 1;
	private static int asinIndex = 2;
	private static int conditionTypeIndex = 3;
	private static int warehouseConditionCodeIndex = 4;
	private static int quantityAvailableIndex = 5;

	public void readFbaInventoryFile(String content) {
		if (StringUtils.isNotBlank(content)) {
			BufferedReader reader = TransformUtils.getBufferedReaderFromString(content);
			readFbaInventoryFileWithBufferedReader(reader);
		}
	}

	public void readFbaInventoryFileWithBufferedReader(BufferedReader reader) {
		if (null != reader) {

			String line = null;
			String arr[] = null;
			String targetSku = null;
			FbaInventoryVo fbaInventoryVo = null;

			try {
				// Skip the first line(header)
				line = reader.readLine();

				arr = line.split("\t", -1);

				Map<String, FbaInventoryVo> fbaInventoryVoMap = new HashMap<>();

				while ((line = reader.readLine()) != null) {
					arr = line.split("\t", -1);

					if (arr[warehouseConditionCodeIndex].equals("SELLABLE")) {

						targetSku = TransformUtils.removeCountryCodeAndFBMSuffixFromSku(arr[sellerSkuIndex]);

						fbaInventoryVo = fbaInventoryVoMap.get(targetSku);

						if (null == fbaInventoryVo) {
							fbaInventoryVo = new FbaInventoryVo();
							fbaInventoryVo.setTargetSku(targetSku);
							fbaInventoryVo.setAsin(arr[asinIndex]);
							fbaInventoryVo.setConditionType(arr[conditionTypeIndex]);
							fbaInventoryVo.setWarehouseConditionCode(arr[warehouseConditionCodeIndex]);
							fbaInventoryVo.setQuantityAvailable(Integer.valueOf(arr[quantityAvailableIndex]));
						} else {
							fbaInventoryVo.setQuantityAvailable(fbaInventoryVo.getQuantityAvailable()
									+ Integer.valueOf(arr[quantityAvailableIndex]));

						}

						fbaInventoryVoMap.put(targetSku, fbaInventoryVo);
					}
				}

				// Insert or update
				for (FbaInventoryVo vo : fbaInventoryVoMap.values()) {
					FbaInventoryVo tmpFbaInventoryVo = fbaInventoryDao.queryFbaInventory(vo);
					if (null == tmpFbaInventoryVo) {
						fbaInventoryDao.insertFbaInventoryVo(Arrays.asList(vo));
					} else {
						fbaInventoryDao.updateFbaInventoryVo(vo);
					}
				}

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

}
