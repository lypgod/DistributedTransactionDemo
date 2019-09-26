package com.lypgod.demo.dtx.hmily.bank2.service;

import com.lypgod.demo.dtx.hmily.bank2.model.entity.AccountInfo;
import com.lypgod.demo.dtx.hmily.bank2.model.entity.LocalConfirmLog;
import com.lypgod.demo.dtx.hmily.bank2.model.repository.AccountInfoRepository;
import com.lypgod.demo.dtx.hmily.bank2.model.repository.LocalCancelLogRepository;
import com.lypgod.demo.dtx.hmily.bank2.model.repository.LocalConfirmLogRepository;
import com.lypgod.demo.dtx.hmily.bank2.model.repository.LocalTryLogRepository;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
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
    private LocalTryLogRepository tryLogRepository;
    @Resource
    private LocalConfirmLogRepository confirmLogRepository;
    @Resource
    private LocalCancelLogRepository cancelLogRepository;

    @Hmily(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public void updateAccountBalance(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 try begin 开始执行...xid:{}", transId);
    }

    /**
     * confirm方法
     * confirm幂等校验
     * 正式增加金额
     *
     * @param accountNo String
     * @param amount    Double
     */
    @Transactional(rollbackFor = Exception.class)
    public void confirmMethod(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 confirm begin 开始执行...xid:{}", transId);

        if (confirmLogRepository.existsLocalConfirmLogByTxNo(transId)) {
            log.info("bank2 confirm 已经执行，无需重复执行...xid:{}", transId);
            return;
        } else {
            //增加金额
            AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountNo);
            accountInfo.setAccountBalance(accountInfo.getAccountBalance() + amount);
            accountInfoRepository.save(accountInfo);

            //增加一条confirm日志，用于幂等
            LocalConfirmLog confirmLog = new LocalConfirmLog();
            confirmLog.setTxNo(transId);
            confirmLogRepository.save(confirmLog);

            log.info("bank2 confirm end 结束执行...xid:{}", transId);
        }
    }


    /**
     * @param accountNo String
     * @param amount    Double
     */
    public void cancelMethod(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank2 cancel begin 开始执行...xid:{}", transId);
    }
}