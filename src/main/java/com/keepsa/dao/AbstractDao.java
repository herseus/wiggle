package com.keepsa.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

@Component
public class AbstractDao {
	@Resource(name="sqlSessionTemplate_master")
	protected SqlSessionTemplate sstMaster;
	
	@Resource(name="sqlSessionTemplate_slave")
	protected SqlSessionTemplate sstSlave;
}
