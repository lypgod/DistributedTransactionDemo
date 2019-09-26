package com.lypgod.demo.dtx.seata.bank1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator.
 *
 * @author lypgod
 */
@FeignClient(value = "seata-demo-bank2", fallback = Bank2FeignClientFallback.class)
public interface Bank2FeignClient {
    String FALLBACK_RESULT = "fallback";

    /**
     * 远程调用李四的微服务
     *
     * @param amount Double
     * @return String
     */
    @GetMapping("/bank2/transfer")
    String transfer(@RequestParam("amount") Double amount);
}
