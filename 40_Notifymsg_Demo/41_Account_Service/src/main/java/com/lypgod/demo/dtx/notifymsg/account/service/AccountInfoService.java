package com.lypgod.demo.dtx.notifymsg.account.service;

import com.lypgod.demo.dtx.notifymsg.account.feign.RechargeFeignClient;
import com.lypgod.demo.dtx.notifymsg.account.model.entity.AccountInfo;
import com.lypgod.demo.dtx.notifymsg.account.model.entity.DeDuplication;
import com.lypgod.demo.dtx.notifymsg.account.model.entity.Recharge;
import com.lypgod.demo.dtx.notifymsg.account.model.repository.AccountInfoRepository;
import com.lypgod.demo.dtx.notifymsg.account.model.repository.DeDuplicationRepository;
import com.lypgod.demo.dtx.notifymsg.account.util.message.AccountChangeEvent;
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
    @Resource
    private RechargeFeignClient rechargeFeignClient;

    /**
     * 远程调用查询充值结果
     * @param txNo String
     * @return Recharge
     */
    public Recharge queryRechargeResult(String txNo) {
        // 远程调用
        Recharge result = rechargeFeignClient.result(txNo);

        if("success".equals(result.getResult())){
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent()
                    .setAccountNo(result.getAccountNo())
                    .setAmount(result.getAmount())
                    .setTxNo(result.getId());
            updateAccountBalance(accountChangeEvent);
        }

        return result;
    }

    /**
     * 更新账户金额
     * @param accountChange AccountChangeEvent
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountBalance(AccountChangeEvent accountChange) {
        // 幂等校验
        if (duplicationRepository.existsDeDuplicationByTxNo(accountChange.getTxNo())) {
            return;
        }

        // 更新账户余额
        AccountInfo accountInfo = accountInfoRepository.findByAccountNo(accountChange.getAccountNo());
        accountInfo.setAccountBalance(accountInfo.getAccountBalance() + accountChange.getAmount());
        accountInfoRepository.save(accountInfo);

        //插入事务记录，用于幂等控制
        duplicationRepository.save(new DeDuplication().setTxNo(accountChange.getTxNo()));
    }
}
