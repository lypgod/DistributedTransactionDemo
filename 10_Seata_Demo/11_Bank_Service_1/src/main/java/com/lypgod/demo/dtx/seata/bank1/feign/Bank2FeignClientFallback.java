package com.lypgod.demo.dtx.seata.bank1.feign;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class Bank2FeignClientFallback implements Bank2FeignClient {
    @Override
    public String transfer(Double amount) {
        return "fallback";
    }
}