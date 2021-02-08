package com.xj.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Component
public class RmTwoOrderImpl implements RmTwoOrder {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String orderTry(BusinessActionContext businessActionContext) {
        System.out.println("预备");
        System.out.println("order try");

        restTemplate.getForEntity("http://inventory/inventory-tcc", null);

//        System.out.println(1/0);
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderCommit(BusinessActionContext businessActionContext) {
        System.out.println("提交");
        System.out.println("order confirm");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean orderRollback(BusinessActionContext businessActionContext) {
        System.out.println("回滚");
        System.out.println("order rollback");
        return true;
    }
}
