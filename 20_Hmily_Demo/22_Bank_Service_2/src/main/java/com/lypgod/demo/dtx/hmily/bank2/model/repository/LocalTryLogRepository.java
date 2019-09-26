package com.lypgod.demo.dtx.hmily.bank2.model.repository;

import com.lypgod.demo.dtx.hmily.bank2.model.entity.LocalTryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface LocalTryLogRepository extends JpaRepository<LocalTryLog, String> {
    /**
     * existsLocalTryLogByTxNo
     *
     * @param txNo String
     * @return boolean
     */
    boolean existsLocalTryLogByTxNo(String txNo);
}
