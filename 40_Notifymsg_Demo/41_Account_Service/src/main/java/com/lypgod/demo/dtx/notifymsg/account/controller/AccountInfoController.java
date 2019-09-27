package com.lypgod.demo.dtx.notifymsg.account.controller;

import com.lypgod.demo.dtx.notifymsg.account.model.entity.Recharge;
import com.lypgod.demo.dtx.notifymsg.account.service.AccountInfoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@RestController
@Log4j2
public class AccountInfoController {
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 主动查询充值结果
     * @param txNo String
     * @return Recharge
     */
    @GetMapping(value = "/result/{txNo}")
    public Recharge result(@PathVariable("txNo") String txNo){
        return accountInfoService.queryRechargeResult(txNo);
    }
}