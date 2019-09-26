package com.lypgod.demo.dtx.hmily.bank2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author lypgod
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableHystrix
@ComponentScan({"com.lypgod.demo.dtx.hmily.bank2", "org.dromara.hmily"})
public class HmilyBankService2Application {

    public static void main(String[] args) {
        SpringApplication.run(HmilyBankService2Application.class, args);
    }

}
