package com.vma;


import com.vma.assist.wraps.LogWrap;
import com.vma.cloud.EnableVmaCloud;
import com.vma.cloud.server.EnableVmaCloudServer;
import com.vma.core.EnableVmaCore;
import com.vma.mybatis.EnableVmaMybatis;
import com.vma.redis.EnableVmaRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用类
 *
 * @author hhd
 */
@SpringBootApplication
@EnableVmaCore
@EnableVmaRedis
@EnableVmaMybatis
@MapperScan("com.vma.**.dao")
@EnableVmaCloud
@EnableVmaCloudServer
public class ApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationServer.class, args);
        LogWrap.getLogger(ApplicationServer.class).info("app-business start completion");
    }
}



