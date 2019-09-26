package com.lypgod.demo.dtx.hmily.bank1.feign;

import org.springframework.stereotype.Component;

/**
 * @author lypgod
 */
@Component
public class Bank2FeignClientFallback implements Bank2FeignClient {
    @Override
    public Boolean transfer(Double amount) {
        return false;
    }
}