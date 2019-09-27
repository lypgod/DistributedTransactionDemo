package com.lypgod.demo.dtx.notifymsg.account.feign;

import com.lypgod.demo.dtx.notifymsg.account.model.entity.Recharge;
import org.springframework.stereotype.Component;

/**
 * @author lypgod
 */
@Component
public class RechargeFeignClientFallBack implements RechargeFeignClient {
    @Override
    public Recharge result(String txNo) {
        return new Recharge().setResult("fail");
    }
}
