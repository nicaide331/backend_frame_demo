package com.vma;


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
@MapperScan("com.vma.**.dao")
public class TaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskApplication.class, args);
    }
}
