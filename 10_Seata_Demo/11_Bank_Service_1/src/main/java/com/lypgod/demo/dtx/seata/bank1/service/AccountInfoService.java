package com.lypgod.demo.dtx.seata.bank1.service;

import com.lypgod.demo.dtx.seata.bank1.feign.Bank2FeignClient;
import com.lypgod.demo.dtx.seata.bank1.model.entity.AccountInfo;
import com.lypgod.demo.dtx.seata.bank1.model.repository.AccountInfoRepository;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.lypgod.demo.dtx.seata.bank1.feign.Bank2FeignClient.FALLBACK_RESULT;

/**
 * @author lypgod
 */
@Service
@Log4j2
public class AccountInfoService {
    @Resource
    private AccountInfoRepository accountInfoRepository;
    @Resource
    private Bank2FeignClient bank2FeignClient;

    /**
     * updateAccountBalance
     *
     * @param accountNo String
     * @param amount    Double
     */
    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional
    public void updateAccountBalance(String accountNo, Double amount) {
        log.info("bank1 service begin, XID：{}", RootContext.getXID());

        //扣减张三的金额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() - amount);
        accountInfoRepository.save(accountInfo);

        //调用李四微服务，转账
        String transfer = bank2FeignClient.transfer(amount);

        if (FALLBACK_RESULT.equals(transfer)) {
            //调用李四微服务异常
            throw new RuntimeException("bank2 threw exception...");
        }

        if (amount == 2) {
            //人为制造异常
            throw new RuntimeException("bank1 threw exception...");
        }
    }
}
