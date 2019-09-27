package com.lypgod.demo.dtx.txmsg.bank2.model.repository;

import com.lypgod.demo.dtx.txmsg.bank2.model.entity.DeDuplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface DeDuplicationRepository extends JpaRepository<DeDuplication, String> {
    /**
     * existsDeDuplicationByTxNo
     *
     * @param txNo String
     * @return boolean
     */
    boolean existsDeDuplicationByTxNo(String txNo);
}
