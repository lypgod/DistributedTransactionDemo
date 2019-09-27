package com.lypgod.demo.dtx.txmsg.bank1.service;

import com.lypgod.demo.dtx.txmsg.bank1.model.entity.AccountInfo;
import com.lypgod.demo.dtx.txmsg.bank1.model.entity.DeDuplication;
import com.lypgod.demo.dtx.txmsg.bank1.model.repository.AccountInfoRepository;
import com.lypgod.demo.dtx.txmsg.bank1.model.repository.DeDuplicationRepository;
import com.lypgod.demo.dtx.txmsg.bank1.util.JsonUtils;
import com.lypgod.demo.dtx.txmsg.bank1.util.message.AccountChangeEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@Service
@Log4j2
public class AccountInfoService {
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Resource
    private DeDuplicationRepository duplicationRepository;
    @Resource
    private RocketMQTemplate rocketMqTemplate;
    @Resource
    private JsonUtils jsonUtils;

    /**
     * 向mq发送转账消息
     *
     * @param accountChangeEvent AccountChangeEvent
     */
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {

        // 将accountChangeEvent转成json
        String accountChangeString = jsonUtils.toJsonString(accountChangeEvent);
        // 生成message类型
        Message<String> message = MessageBuilder.withPayload(accountChangeString).build();
        /*
         * 发送一条事务消息
         * String txProducerGroup 生产组
         * String destination topic，
         * Message<?> message, 消息内容
         * Object arg 参数
         */
        rocketMqTemplate.sendMessageInTransaction("producer_group_txmsg_bank1", "topic_txmsg", message, null);
    }

    /**
     * 更新账户，扣减金额
     *
     * @param accountChangeEvent AccountChangeEvent
     */
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {
        // 幂等判断
        if (duplicationRepository.existsDeDuplicationByTxNo(accountChangeEvent.getTxNo())) {
            return;
        }

        // 扣减金额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountChangeEvent.getAccountNo());
        if (accountInfo.getAccountBalance() < accountChangeEvent.getAmount()) {
            throw new RuntimeException("bank1余额不足！");
        }
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() - accountChangeEvent.getAmount());
        accountInfoRepository.save(accountInfo);

        // 添加事务日志
        duplicationRepository.save(new DeDuplication().setTxNo(accountChangeEvent.getTxNo()));

        // 人为制造异常
        if (accountChangeEvent.getAmount() == 3) {
            throw new RuntimeException("人为制造异常");
        }
    }
}