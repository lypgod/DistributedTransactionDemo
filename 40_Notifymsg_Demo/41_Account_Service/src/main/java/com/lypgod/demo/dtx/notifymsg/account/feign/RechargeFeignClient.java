package com.lypgod.demo.dtx.notifymsg.account.feign;

import com.lypgod.demo.dtx.notifymsg.account.model.entity.Recharge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lypgod
 */
@FeignClient(value = "notifymsg-demo-recharge", fallback = RechargeFeignClientFallBack.class)
public interface RechargeFeignClient {
    /**
     * 远程调用充值系统的接口查询充值结果
     *
     * @param txNo String
     * @return Recharge
     */
    @GetMapping(value = "/recharge/result/{txNo}")
    Recharge result(@PathVariable("txNo") String txNo);
}