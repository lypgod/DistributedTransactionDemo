package com.lypgod.demo.dtx.txmsg.bank2.util.message;

import com.lypgod.demo.dtx.txmsg.bank2.service.AccountInfoService;
import com.lypgod.demo.dtx.txmsg.bank2.util.JsonUtils;
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
@RocketMQMessageListener(consumerGroup = "consumer_group_txmsg_bank2", topic = "topic_txmsg")
public class TxmsgConsumer implements RocketMQListener<String> {
    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private JsonUtils jsonUtils;

    /**
     * 接收消息
     *
     * @param message String
     */
    @Override
    public void onMessage(String message) {
        log.info("开始消费消息:{}", message);
        // 解析消息
        AccountChangeEvent accountChangeEvent = jsonUtils.parseAccountChangeEvent(message);

        // 设置账号为李四的
        accountChangeEvent.setAccountNo("2");

        // 更新本地账户，增加金额
        accountInfoService.addAccountInfoBalance(accountChangeEvent);
    }
}