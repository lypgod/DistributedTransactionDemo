package com.lypgod.demo.dtx.notifymsg.recharge.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author lypgod
 */
@Entity
@Data
@Accessors(chain = true)
public class Recharge {
	/**
	 *  事务号
	 */
	@Id
	private String id;
	/**
	 * 账号
	 */
	private String accountNo;
	/**
	 * 充值金额
	 */
	private double amount;
	/**
	 * 充值结果
	 */
	private String result;
}