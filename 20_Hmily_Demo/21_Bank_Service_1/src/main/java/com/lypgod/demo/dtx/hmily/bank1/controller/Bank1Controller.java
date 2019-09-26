package com.lypgod.demo.dtx.hmily.bank1.controller;

import com.lypgod.demo.dtx.hmily.bank1.service.AccountInfoService;
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

    @GetMapping("/transfer")
    public Boolean transfer(Double amount) {
        this.accountInfoService.updateAccountBalance("1", amount);
        return true;
    }
}