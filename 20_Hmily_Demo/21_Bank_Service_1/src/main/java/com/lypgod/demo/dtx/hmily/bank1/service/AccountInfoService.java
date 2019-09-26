package com.lypgod.demo.dtx.hmily.bank1.service;

import com.lypgod.demo.dtx.hmily.bank1.feign.Bank2FeignClient;
import com.lypgod.demo.dtx.hmily.bank1.model.entity.AccountInfo;
import com.lypgod.demo.dtx.hmily.bank1.model.entity.LocalCancelLog;
import com.lypgod.demo.dtx.hmily.bank1.model.entity.LocalTryLog;
import com.lypgod.demo.dtx.hmily.bank1.model.repository.AccountInfoRepository;
import com.lypgod.demo.dtx.hmily.bank1.model.repository.LocalCancelLogRepository;
import com.lypgod.demo.dtx.hmily.bank1.model.repository.LocalConfirmLogRepository;
import com.lypgod.demo.dtx.hmily.bank1.model.repository.LocalTryLogRepository;
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
    @Resource
    private Bank2FeignClient bank2FeignClient;

    /**
     * try幂等校验
     * try悬挂处理
     * 检查余额是够扣减金额
     * 扣减金额
     * 账户扣款，就是tcc的try方法
     * 只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法的名字
     *
     * @param accountNo String
     * @param amount Double
     */
    @Transactional(rollbackFor = Exception.class)
    @Hmily(confirmMethod = "commit", cancelMethod = "rollback")
    public void updateAccountBalance(String accountNo, Double amount) {
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 try begin 开始执行... xid:{}", transId);

        // 幂等判断 判断local_try_log表中是否有try日志记录，如果有则不再执行
        if (tryLogRepository.existsLocalTryLogByTxNo(transId)) {
            log.info("bank1 try 已经执行，无需重复执行, xid:{}", transId);
            return;
        }

        // try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if (confirmLogRepository.existsLocalConfirmLogByTxNo(transId) || cancelLogRepository.existsLocalCancelLogByTxNo(transId)) {
            log.info("bank1 try悬挂处理  cancel或confirm已经执行，不允许执行try, xid:{}", transId);
            return;
        }

        // 扣减金额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() - amount);
        accountInfoRepository.save(accountInfo);

        // 插入try执行记录,用于幂等判断
        LocalTryLog tryLog = new LocalTryLog();
        tryLog.setTxNo(transId);
        tryLogRepository.save(tryLog);

        // 远程调用李四，转账
        if (!bank2FeignClient.transfer(amount)) {
            throw new RuntimeException("bank1 远程调用李四微服务失败, xid:{}" + transId);
        }

        // 人为制造异常
        if (amount == 2) {
            throw new RuntimeException("人为制造异常, xid:{}" + transId);
        }

        log.info("bank1 try end 结束执行... xid:{}", transId);
    }

    /**
     * confirm方法
     *
     * @param accountNo String
     * @param amount Double
     */
    @Transactional(rollbackFor = Exception.class)
    public void commit(String accountNo, Double amount) {
        //获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 confirm begin 开始执行... xid:{}, accountNo:{}, amount:{}", transId, accountNo, amount);
    }

    /**
     * cancel方法
     * cancel幂等校验
     * cancel空回滚处理
     * 增加可用余额
     *
     * @param accountNo String
     * @param amount Double
     */
    @Transactional(rollbackFor = Exception.class)
    public void rollback(String accountNo, Double amount) {
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 cancel begin 开始执行... xid:{}", transId);

        // cancel幂等校验
        if (cancelLogRepository.existsLocalCancelLogByTxNo(transId)) {
            log.info("bank1 cancel 已经执行，无需重复执行, xid:{}", transId);
            return;
        }

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if (!tryLogRepository.existsLocalTryLogByTxNo(transId)) {
            log.info("bank1 空回滚处理，try没有执行，不允许cancel执行, xid:{}", transId);
            return;
        }

        //	增加可用余额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountNo);
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() + amount);
        accountInfoRepository.save(accountInfo);

        //插入一条cancel的执行记录
        LocalCancelLog cancelLog = new LocalCancelLog();
        cancelLog.setTxNo(transId);
        cancelLogRepository.save(cancelLog);

        log.info("bank1 cancel end 结束执行...xid:{}", transId);
    }
}