package com.xj.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface RmTwoOrder {

    @TwoPhaseBusinessAction(name = "rm1TccAction" , commitMethod = "orderCommit" ,rollbackMethod = "orderRollback")
    public String orderTry(BusinessActionContext businessActionContext);

    public boolean orderCommit(BusinessActionContext businessActionContext);

    public boolean orderRollback(BusinessActionContext businessActionContext);
}