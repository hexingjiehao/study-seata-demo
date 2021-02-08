package com.xj.service;

import com.xj.dao.TblOrderDao;
import com.xj.entity.TblOrder;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.util.Date;

@Service
public class LocalOrderService {

	@Autowired
    TblOrderDao tblOrderDao;
	@Autowired
	private RestTemplate restTemplate;

	/*
	* seata分布式事务的使用，先本地事务成功，然后远程服务失败。全部事务回滚
	* */
	@GlobalTransactional(rollbackFor = Exception.class)
	@Transactional(rollbackFor = Exception.class)
	public String addOrder() {
		TblOrder tblOrder = new TblOrder();
		tblOrder.setOrderId(1);
		tblOrder.setBuyer("马士兵");
		tblOrder.setUpdateTime(new Date());
		
		tblOrderDao.insert(tblOrder);

		restTemplate.getForEntity("http://inventory/reduce?goodId=1", null);
		System.out.println(1/0);
		
		return "";
	}

	@GlobalTransactional(rollbackFor = Exception.class)
	@Transactional(rollbackFor = Exception.class)
	public String reserveStock() {
		restTemplate.getForEntity("http://inventory/reserveStock", null);

		/*测试占预留成功，但是单据提交失败：结果：两边都执行失败，效果OK*/
//		System.out.println(1/0);

		TblOrder tblOrder = new TblOrder();
		tblOrder.setOrderId(1);
		tblOrder.setBuyer("马士兵");
		tblOrder.setUpdateTime(new Date());
		tblOrderDao.insert(tblOrder);

		return "";
	}
}
