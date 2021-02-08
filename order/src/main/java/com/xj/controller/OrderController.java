package com.xj.controller;

import com.xj.service.RmTwoOrder;
import com.xj.service.LocalOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    LocalOrderService localOrderService;
    @Autowired
    private RmTwoOrder rmTwoOrder;

    @GetMapping("/order-add")
    public String addOrder() throws InterruptedException {
        localOrderService.addOrder();
        return "success";
    }


    @GetMapping("/order-tcc")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String oneTcc() throws InterruptedException {
        rmTwoOrder.orderTry(null);
        return "success";
    }

    /*
     * 测试场景，单据占预留
     * */
    @GetMapping("/stock-reserve")
    public String reserveStock() throws InterruptedException {
        localOrderService.reserveStock();
        return "success";
    }

}
