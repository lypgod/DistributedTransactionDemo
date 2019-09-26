package com.lypgod.demo.dtx.hmily.bank1.feign;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lypgod
 */
@FeignClient(value = "tcc-demo-bank2", fallback = Bank2FeignClientFallback.class)
public interface Bank2FeignClient {
    /**
     * 远程调用李四的微服务
     *
     * @param amount Double
     * @return String
     */
    @GetMapping("/bank2/transfer")
    @Hmily
    Boolean transfer(@RequestParam("amount") Double amount);
}
