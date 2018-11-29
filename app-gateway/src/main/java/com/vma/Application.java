package com.vma;


import com.vma.assist.wraps.LogWrap;
import com.vma.boost.rbac.EnableVmaBoostRbac;
import com.vma.core.EnableVmaCore;
import com.vma.mybatis.EnableVmaMybatis;
import com.vma.redis.EnableVmaRedis;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用类
 *
 * @author hhd
 */
@SpringBootApplication
@EnableVmaBoostRbac
@EnableVmaCore
@EnableVmaRedis
@EnableVmaMybatis
@MapperScan("com.vma.**.dao")
public class Application {

    private static Logger logger = LogWrap.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("app-business start completion");
    }

}



