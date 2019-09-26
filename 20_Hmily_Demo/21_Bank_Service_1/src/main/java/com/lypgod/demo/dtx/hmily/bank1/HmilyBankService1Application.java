package com.lypgod.demo.dtx.hmily.bank1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lypgod
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableHystrix
@EnableFeignClients
@ComponentScan({"com.lypgod.demo.dtx.hmily.bank1", "org.dromara.hmily"})
public class HmilyBankService1Application {

    public static void main(String[] args) {
        SpringApplication.run(HmilyBankService1Application.class, args);
    }

}
