package com.ivanuil.scalabledistributedsystems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ScalableDistributedSystemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScalableDistributedSystemsApplication.class, args);
    }

}
