package com.lypgod.demo.dtx.txmsg.bank1.controller;

import com.lypgod.demo.dtx.txmsg.bank1.service.AccountInfoService;
import com.lypgod.demo.dtx.txmsg.bank1.util.message.AccountChangeEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author lypgod
 */
@RestController
public class AccountInfoController {
    @Resource
    private AccountInfoService accountInfoService;

    @GetMapping(value = "/transfer")
    public String transfer(@RequestParam("accountNo")String accountNo, @RequestParam("amount") Double amount){
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent()
                .setAccountNo(accountNo)
                .setAmount(amount)
                .setTxNo(UUID.randomUUID().toString());

        //发送消息
        accountInfoService.sendUpdateAccountBalance(accountChangeEvent);

        return "OK";
    }
}