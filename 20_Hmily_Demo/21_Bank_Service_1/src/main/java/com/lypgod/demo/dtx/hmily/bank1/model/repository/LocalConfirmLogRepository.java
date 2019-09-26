package com.lypgod.demo.dtx.hmily.bank1.model.repository;

import com.lypgod.demo.dtx.hmily.bank1.model.entity.LocalConfirmLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface LocalConfirmLogRepository extends JpaRepository<LocalConfirmLog, String> {
    /**
     * existsLocalConfirmLogByTxNo
     *
     * @param txNo String
     * @return boolean
     */
    boolean existsLocalConfirmLogByTxNo(String txNo);
}
