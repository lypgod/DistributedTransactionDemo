package com.lypgod.demo.dtx.seata.bank1.controller;

import com.lypgod.demo.dtx.seata.bank1.service.AccountInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@RestController
public class Bank1Controller {

    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 张三转账
     *
     * @param amount Double
     * @return String
     */
    @GetMapping("/transfer")
    public String transfer(Double amount) {
        accountInfoService.updateAccountBalance("1", amount);
        return "bank1 - " + amount;
    }
}