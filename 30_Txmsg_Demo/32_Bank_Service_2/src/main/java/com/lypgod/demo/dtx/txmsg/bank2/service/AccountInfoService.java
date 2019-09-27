package com.lypgod.demo.dtx.txmsg.bank2.service;

import com.lypgod.demo.dtx.txmsg.bank2.model.entity.AccountInfo;
import com.lypgod.demo.dtx.txmsg.bank2.model.entity.DeDuplication;
import com.lypgod.demo.dtx.txmsg.bank2.model.repository.AccountInfoRepository;
import com.lypgod.demo.dtx.txmsg.bank2.model.repository.DeDuplicationRepository;
import com.lypgod.demo.dtx.txmsg.bank2.util.message.AccountChangeEvent;
import lombok.extern.log4j.Log4j2;
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

    /**
     * 更新账户，增加金额
     *
     * @param accountChangeEvent AccountChangeEvent
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAccountInfoBalance(AccountChangeEvent accountChangeEvent) {
        log.info("bank2更新本地账号，账号：{}, 金额：{}", accountChangeEvent.getAccountNo(), accountChangeEvent.getAmount());

        // 校验幂等
        if (duplicationRepository.existsDeDuplicationByTxNo(accountChangeEvent.getTxNo())) {
            return;
        }

        // 增加金额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountChangeEvent.getAccountNo());
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() + accountChangeEvent.getAmount());
        accountInfoRepository.save(accountInfo);

        // 添加事务记录，用于幂等
        duplicationRepository.save(new DeDuplication().setTxNo(accountChangeEvent.getTxNo()));

        // 测试异常
        if (accountChangeEvent.getAmount() == 4) {
            throw new RuntimeException("人为制造异常");
        }
    }
}