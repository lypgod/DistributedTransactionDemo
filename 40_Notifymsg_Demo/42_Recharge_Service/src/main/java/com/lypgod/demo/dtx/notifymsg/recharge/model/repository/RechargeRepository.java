package com.lypgod.demo.dtx.notifymsg.recharge.model.repository;

import com.lypgod.demo.dtx.notifymsg.recharge.model.entity.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lypgod
 */
@Repository
public interface RechargeRepository extends JpaRepository<Recharge, String> {
}
