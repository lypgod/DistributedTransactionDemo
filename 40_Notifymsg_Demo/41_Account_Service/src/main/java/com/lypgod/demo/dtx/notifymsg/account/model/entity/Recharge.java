package com.lypgod.demo.dtx.notifymsg.account.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lypgod
 */
@Data
@Accessors(chain = true)
public class Recharge {
	/**
	 *  事务号
	 */
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