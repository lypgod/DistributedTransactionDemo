package com.lypgod.demo.dtx.notifymsg.recharge.service;

import com.lypgod.demo.dtx.notifymsg.recharge.model.entity.Recharge;
import com.lypgod.demo.dtx.notifymsg.recharge.model.repository.RechargeRepository;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@Service
public class RechargeService {
    @Resource
    private RechargeRepository rechargeRepository;
    @Resource
    private RocketMQTemplate rocketMqTemplate;

    /**
     * 插入充值记录
     * @param recharge Recharge
     * @return Recharge
     */
    public Recharge insertRecharge(Recharge recharge) {
        Recharge result = new Recharge()
                .setId(recharge.getId())
                .setAccountNo(recharge.getAccountNo())
                .setAmount(recharge.getAmount())
                .setResult("success");
        rechargeRepository.save(result);

        //发送通知,使用普通消息发送通知
        rocketMqTemplate.convertAndSend("topic_notifymsg", result);

        return result;
    }

    /**
     * 查询充值记录，接收通知方调用此方法来查询充值结果
     * @param txNo String
     * @return Recharge
     */
    public Recharge getRecharge(String txNo) {
        return rechargeRepository.findById(txNo).orElseThrow(() -> new RuntimeException("充值记录未找到！"));
    }
}
