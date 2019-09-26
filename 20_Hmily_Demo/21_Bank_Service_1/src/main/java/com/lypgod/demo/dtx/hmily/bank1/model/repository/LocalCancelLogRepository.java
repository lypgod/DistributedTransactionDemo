package com.lypgod.demo.dtx.hmily.bank1.model.repository;

import com.lypgod.demo.dtx.hmily.bank1.model.entity.LocalCancelLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface LocalCancelLogRepository extends JpaRepository<LocalCancelLog, String> {
    /**
     * existsLocalCancelLogByTxNo
     *
     * @param txNo String
     * @return boolean
     */
    boolean existsLocalCancelLogByTxNo(String txNo);
}
