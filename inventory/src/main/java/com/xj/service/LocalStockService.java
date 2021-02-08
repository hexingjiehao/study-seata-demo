package com.xj.service;

import com.xj.dao.TblInventoryDao;
import com.xj.entity.TblInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LocalStockService {

    @Autowired
    TblInventoryDao tblInventoryDao;

    @Transactional(rollbackFor = Exception.class)
    public String reduce(int goodId) {
        TblInventory tblInventory = tblInventoryDao.selectByPrimaryKey(goodId);
        tblInventory.setNum(tblInventory.getNum() - 1);
        tblInventoryDao.updateByPrimaryKey(tblInventory);
        return "";
    }

    @Transactional(rollbackFor = Exception.class)
    public void reserveStock() {
		TblInventory tblInventory=new TblInventory();
		tblInventory.setNum(1);
		tblInventory.setUpdateTime(new Date());
        tblInventoryDao.insert(tblInventory);
    }
}