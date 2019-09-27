package com.lypgod.demo.dtx.notifymsg.account.model.repository;

import com.lypgod.demo.dtx.notifymsg.account.model.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {
    /**
     * findByAccountNo
     *
     * @param accountNo String
     * @return AccountInfo
     */
    AccountInfo findByAccountNo(String accountNo);
}
