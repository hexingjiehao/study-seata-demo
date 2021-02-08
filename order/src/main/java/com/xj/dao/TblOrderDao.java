package com.xj.dao;

import com.xj.entity.TblOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblOrderDao {

    int insert(TblOrder record);
}