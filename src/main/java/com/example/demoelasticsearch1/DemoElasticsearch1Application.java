package com.example.demoelasticsearch1;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class DemoElasticsearch1Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoElasticsearch1Application.class, args);
    }

}
