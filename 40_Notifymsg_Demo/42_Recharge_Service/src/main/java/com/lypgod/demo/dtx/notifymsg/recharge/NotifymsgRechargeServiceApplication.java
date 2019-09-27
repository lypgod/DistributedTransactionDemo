package com.lypgod.demo.dtx.notifymsg.recharge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * @author lypgod
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class NotifymsgRechargeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifymsgRechargeServiceApplication.class, args);
    }

}
