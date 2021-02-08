package com.xj.controller;

import com.xj.service.RmTwoInventory;
import com.xj.service.LocalStockService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @Autowired
    private LocalStockService localStockService;
    @Autowired
    private RmTwoInventory rmTwoInventory;

    /*
    * 被其他服务调用的本地事务
    * */
    @GetMapping("/reduce")
    public String reduce(Integer goodId){
        localStockService.reduce(goodId);
        return "success";
    }

    /*
    * 被其他服务调用的本地二阶段提交的全局事务
    * */
    @GetMapping("/inventory-tcc")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String twoTcc(){
        rmTwoInventory.inventoryTry(null);
//        int i = 1/0;
        return "success";
    }

    /*
    * 测试场景，单据占预留
    * */
    @GetMapping("/reserveStock")
    public String reserveStock(Integer goodId){
        localStockService.reserveStock();
        return "success";
    }
}
