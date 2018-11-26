package com.vma;


import com.vma.boost.rbac.EnableVmaBoostRbac;
import com.vma.core.EnableVmaCore;
import com.vma.mybatis.EnableVmaMybatis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot应用类
 *
 * @author hhd
 */
@SpringBootApplication
@EnableScheduling
@EnableVmaCore
@EnableVmaMybatis
@EnableVmaBoostRbac
@MapperScan("com.vma.**.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
