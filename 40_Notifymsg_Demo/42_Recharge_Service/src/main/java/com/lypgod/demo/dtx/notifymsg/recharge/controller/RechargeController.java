package com.lypgod.demo.dtx.notifymsg.recharge.controller;

import com.lypgod.demo.dtx.notifymsg.recharge.model.entity.Recharge;
import com.lypgod.demo.dtx.notifymsg.recharge.service.RechargeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author lypgod
 */
@RestController
public class RechargeController {
    @Resource
    private RechargeService rechargeService;

    /**
     * 充值
     *
     * @param recharge Recharge
     * @return Recharge
     */
    @GetMapping(value = "/process")
    public Recharge pay(Recharge recharge) {
        //生成事务编号
        recharge.setId(UUID.randomUUID().toString());
        return rechargeService.insertRecharge(recharge);
    }

    /**
     * 查询充值结果
     *
     * @param txNo String
     * @return Recharge
     */
    @GetMapping(value = "/result/{txNo}")
    public Recharge result(@PathVariable("txNo") String txNo) {
        return rechargeService.getRecharge(txNo);
    }
}
