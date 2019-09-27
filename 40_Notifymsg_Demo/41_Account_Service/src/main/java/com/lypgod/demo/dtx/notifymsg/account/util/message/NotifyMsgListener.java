package com.lypgod.demo.dtx.notifymsg.account.util.message;

import com.alibaba.fastjson.JSON;
import com.lypgod.demo.dtx.notifymsg.account.model.entity.Recharge;
import com.lypgod.demo.dtx.notifymsg.account.service.AccountInfoService;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@Component
@Log4j2
@RocketMQMessageListener(topic = "topic_notifymsg", consumerGroup = "consumer_group_notifymsg_account")
public class NotifyMsgListener implements RocketMQListener<Recharge> {
    @Resource
    private AccountInfoService accountInfoService;

    /**
     * 接收消息
     *
     * @param recharge Recharge
     */
    @Override
    public void onMessage(Recharge recharge) {
        log.info("接收到消息：{}", JSON.toJSONString(recharge));

        if ("success".equals(recharge.getResult())) {
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent()
                    .setAccountNo(recharge.getAccountNo())
                    .setAmount(recharge.getAmount())
                    .setTxNo(recharge.getId());
            accountInfoService.updateAccountBalance(accountChangeEvent);
        }

        log.info("处理消息完成：{}", JSON.toJSONString(recharge));
    }
}