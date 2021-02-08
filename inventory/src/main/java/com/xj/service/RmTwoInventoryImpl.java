package com.xj.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RmTwoInventoryImpl implements RmTwoInventory {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String inventoryTry(BusinessActionContext businessActionContext) {
        System.out.println("预备");
        System.out.println("inventory try");
//        System.out.println(1/0);

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inventoryCommit(BusinessActionContext businessActionContext) {
        System.out.println("提交");
        System.out.println("inventory confirm");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean inventoryRollback(BusinessActionContext businessActionContext) {
        System.out.println("回滚");
        System.out.println("inventory rollback");
        return true;
    }
}
