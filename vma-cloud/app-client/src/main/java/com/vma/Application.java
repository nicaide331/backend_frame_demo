package com.vma;


import com.vma.cloud.EnableVmaCloud;
import com.vma.cloud.client.EnableVmaCloudClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用类
 *
 * @author hhd
 */
@SpringBootApplication
@EnableVmaCloud
@EnableVmaCloudClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}



