package com.lypgod.demo.dtx.txmsg.bank1.util.message;

import com.lypgod.demo.dtx.txmsg.bank1.model.repository.DeDuplicationRepository;
import com.lypgod.demo.dtx.txmsg.bank1.service.AccountInfoService;
import com.lypgod.demo.dtx.txmsg.bank1.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lypgod
 */
@Component
@Log4j2
@RocketMQTransactionListener(txProducerGroup = "producer_group_txmsg_bank1")
public class ProducerTxmsgListener implements RocketMQLocalTransactionListener {
    @Resource
    private AccountInfoService accountInfoService;
    @Resource
    private DeDuplicationRepository duplicationRepository;
    @Resource
    private JsonUtils jsonUtils;

    /**
     * 事务消息发送后的回调方法，当消息发送给mq成功，此方法被回调
     *
     * @param message Message
     * @param object  Object
     * @return RocketMQLocalTransactionState
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object object) {
        try {
            // 解析message，转成AccountChangeEvent
            AccountChangeEvent accountChangeEvent = jsonUtils.parseAccountChangeEvent(new String((byte[]) message.getPayload()));

            // 执行本地事务，扣减金额
            accountInfoService.doUpdateAccountBalance(accountChangeEvent);

            // 当返回RocketMQLocalTransactionState.COMMIT，自动向mq发送commit消息，mq将消息的状态改为可消费
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 事务状态回查，查询是否扣减金额
     *
     * @param message Message
     * @return RocketMQLocalTransactionState
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        AccountChangeEvent accountChangeEvent = jsonUtils.parseAccountChangeEvent(new String((byte[]) message.getPayload()));

        return duplicationRepository.existsDeDuplicationByTxNo(accountChangeEvent.getTxNo()) ?
                RocketMQLocalTransactionState.COMMIT : RocketMQLocalTransactionState.UNKNOWN;
    }
}