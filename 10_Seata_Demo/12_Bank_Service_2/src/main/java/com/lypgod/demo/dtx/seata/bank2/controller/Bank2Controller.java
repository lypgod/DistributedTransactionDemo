package com.lypgod.demo.dtx.seata.bank2.controller;

import com.lypgod.demo.dtx.seata.bank2.service.AccountInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@RestController
public class Bank2Controller {
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 接收张三的转账
     *
     * @param amount Double
     * @return String
     */
    @GetMapping("/transfer")
    public String transfer(Double amount) {
        //李四增加金额
        accountInfoService.updateAccountBalance("2", amount);
        return "bank2 - " + amount;
    }
}