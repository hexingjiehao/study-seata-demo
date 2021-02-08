package com.xj.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/*
* 本地事务接口
* */
@LocalTCC
public interface RmTwoInventory {

    /*
    * 二阶段提交的事务方法
    * */
    @TwoPhaseBusinessAction(name = "inventoryTccAction" , commitMethod = "inventoryCommit" ,rollbackMethod = "inventoryRollback")
    public String inventoryTry(BusinessActionContext businessActionContext);

    /*
     * 二阶段提交的确认提交方法
     * */
    public boolean inventoryCommit(BusinessActionContext businessActionContext);

    /*
     * 二阶段提交的回滚提交方法
     * */
    public boolean inventoryRollback(BusinessActionContext businessActionContext);
}
