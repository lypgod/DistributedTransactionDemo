package com.lypgod.demo.dtx.txmsg.bank2.util.message;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lypgod
 */
@Data
@Accessors(chain = true)
public class AccountChangeEvent implements Serializable {
    /**
     * 账号
     */
    private String accountNo;
    /**
     * 变动金额
     */
    private double amount;
    /**
     * 事务号
     */
    private String txNo;
}