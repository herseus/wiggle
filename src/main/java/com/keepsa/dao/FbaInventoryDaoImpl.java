package com.keepsa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.keepsa.pojo.FbaInventoryVo;

@Repository
public class FbaInventoryDaoImpl extends AbstractDao {

	public FbaInventoryVo queryFbaInventory(FbaInventoryVo fbaInventoryVo) {
		return sstSlave.selectOne("fbaInventoryMapper.queryFbaInventory", fbaInventoryVo);
	}

	public int updateFbaInventoryVo(FbaInventoryVo fbaInventoryVo) {
		return sstMaster.update("fbaInventoryMapper.updateFbaInventory", fbaInventoryVo);
	}

	public int insertFbaInventoryVo(List<FbaInventoryVo> fbaInventoryVoList) {
		return sstMaster.insert("fbaInventoryMapper.insertFbaInventory", fbaInventoryVoList);
	}
}
