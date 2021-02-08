package com.xj.dao;

import com.xj.entity.TblInventory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblInventoryDao {

    TblInventory selectByPrimaryKey(Integer goodId);

    int updateByPrimaryKey(TblInventory record);

    void insert(TblInventory tblInventory);
}