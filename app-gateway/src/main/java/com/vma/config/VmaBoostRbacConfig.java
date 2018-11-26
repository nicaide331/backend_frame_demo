package com.vma.config;

import com.vma.app.holder.AppRbacAccountHolder;
import com.vma.boost.rbac.permission.IRbacAccountHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DESCRIPTION
 *
 * @author: chennaihua
 * @version: 1.created by chennaihua on 2018/11/15.
 */
@Configuration
public class VmaBoostRbacConfig {

    /**
     * 自定义IRbacAccountHolder
     *
     * @return
     */
    @Bean
    public IRbacAccountHolder accountHolder() {
        return new AppRbacAccountHolder();
    }
}
