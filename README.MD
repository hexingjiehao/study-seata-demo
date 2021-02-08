#这是一个学习seata分布式事务的项目。SpringBoot+eureka+mysql+mybatis+seata
1. inventory服务和order服务引入了springCloud的pom文件。

2. 服务调用使用RestTemplate

3. 项目启动步骤：
    3.1 安装seata服务器; 
    3.2 执行项目中的多个init.sql; 
    3.3 启动微服务。
    
4. **全局事务失败的接口举例：order服务的/stock-reserve。库存和订单的数据都插入数据库失败**     

---
## 1. 项目结构如下：
1. eureka: 微服务注册中心。
 
2. inventory: 库存中心。提供库存服务的业务中心。能够进行eureka注册和服务发现

3. order: 订单中心。提供订单服务的业务中心。需要调用库存中心服务。能够进行eureka注册和服务发现

---
## 2. seata服务器的安装步骤：    
```   
    1.seata服务器地址：https://github.com/seata/seata/releases
    2.需要创建事务日志表undo_log：
        CREATE TABLE `undo_log` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `branch_id` bigint(20) NOT NULL,
          `xid` varchar(100) NOT NULL,
          `context` varchar(128) NOT NULL,
          `rollback_info` longblob NOT NULL,
          `log_status` int(11) NOT NULL,
          `log_created` datetime NOT NULL,
          `log_modified` datetime NOT NULL,
          `ext` varchar(100) DEFAULT NULL,
          PRIMARY KEY (`id`),
          UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
        ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
    3.seata服务器的启动：
        windows的双击seata-server.bat
        linux的启动：sh seata-server.sh -p 8091 -h 127.0.0.1 -m file
            日志存储模式：默认文件，也可以数据库
            --storeMode, -m
              log store mode : file、db
              Default: file
```  

---    
## 3. seata两阶段提交事务过程： 
```
  1.两阶段提交的执行过程：
      代码注解配置：
          最外层调用方法增加注解：@GlobalTransactional(rollbackFor = Exception.class)
          内层接口调用类增加注解：@LocalTCC
          内层接口调用方法增加注解：@TwoPhaseBusinessAction(name = "inventoryTccAction" , commitMethod = "inventoryCommit" ,rollbackMethod = "inventoryRollback")
          内层接口增加确认提交接口：public boolean inventoryCommit(BusinessActionContext businessActionContext); 实现方法需要增加@Transactional
          内层接口增加回滚提交接口：public boolean inventoryRollback(BusinessActionContext businessActionContext); 实现方法需要增加@Transactional
  
      1.正常场景：
          先执行@TwoPhaseBusinessAction的方法
          再执行确认提交方法
      2.异常场景：{两个场景的执行步骤都是一样的！！！}
          2.1 在@TwoPhaseBusinessAction方法之后抛出异常：
                  先执行@TwoPhaseBusinessAction的方法
                  再执行回滚提交方法，这个类似于在发现异常后做一些收尾处理
                  最后才是抛出异常
          2.2 在@TwoPhaseBusinessAction方法内部抛出异常：
                  先执行@TwoPhaseBusinessAction的方法
                  再执行回滚提交方法，这个类似于在发现异常后做一些收尾处理
                  最后才是抛出异常
```


---
## 4. seata全局事务案例：
```
    1.seata全局事务操作：
        1.调用方法增加注解：
            @GlobalTransactional(rollbackFor = Exception.class)
            @Transactional(rollbackFor = Exception.class)
        2.执行本地的sql事务。比如插入insert
        3.然后远程调用rest服务，当rest服务报错
        4.本地的insert的插入事务也没有成功，大功告成！！！
    
    2.订单全局两阶段事务 调用 库存全局两阶段事务
        代码异常流转步骤如下：
            1.订单中心：启动全局两阶段事务
            2.订单中心：调用库存中心服务
            3.库存中心：启动全局两阶段事务
            4.库存中心：执行业务代码，把结果反馈给订单中心
            5.订单中心：执行自己的业务逻辑，执行异常逻辑
            6.订单中心：执行回滚提交事务，并通知库存中心回滚
            7.库存中心：执行回滚提交事务
    
        代码正常流转步骤如下：
            1.订单中心：启动全局两阶段事务
            2.订单中心：调用库存中心服务
            3.库存中心：启动全局两阶段事务
            4.库存中心：执行业务代码，把结果反馈给订单中心
            5.订单中心：执行自己的业务逻辑，执行【正常】逻辑
            6.订单中心：执行确认提交事务，并通知库存中心确认
            7.库存中心：执行确认提交事务
```
    

---
## 5. 细节梳理
1. 简单seata全局事务: 在调用端方法注解：@GlobalTransactional(rollbackFor = Exception.class)
2. 复杂seata全局事务{二阶段服务调用二阶段服务}： 
    调用端接口处：@GlobalTransactional(rollbackFor = Exception.class)
    被调用端接口处：@GlobalTransactional(rollbackFor = Exception.class)
3. 需要修改seata的服务器和配置时，可以参看目录：项目启动准备/*    


 
     
