package com.lypgod.demo.dtx.seata.bank2.service;

import com.lypgod.demo.dtx.seata.bank2.model.entity.AccountInfo;
import com.lypgod.demo.dtx.seata.bank2.model.repository.AccountInfoRepository;
import io.seata.core.context.RootContext;
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

    /**
     * updateAccountBalance
     *
     * @param accountNo String
     * @param amount    Double
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(String accountNo, Double amount) {
        log.info("bank2 service begin, XID：{}", RootContext.getXID());

        //李四增加金额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() + amount);
        accountInfoRepository.save(accountInfo);

        if (amount == 3) {
            //人为制造异常
            throw new RuntimeException("bank2 threw exception..");
        }
    }
}
