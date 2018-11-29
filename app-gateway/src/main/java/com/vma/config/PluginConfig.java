package com.vma.config;

import com.google.common.util.concurrent.RateLimiter;
import com.vma.ratelimiter.aop.LimiterMap;
import org.springframework.context.annotation.Configuration;

/**
 * 插件配置
 *
 * @author huang
 * @create 2018-11-30 0:11
 **/
@Configuration
public class PluginConfig {

    /**
     *
     * 限流插件 引入redis后默认使用redis 推荐使用redis
     * 也提供内存的方式  下面两个例子
     * 使用方式在控制器上写注解 @VmaLimit
     *
     */

    /**
     * 限流插件 基于google令牌桶算法
     *
     * @return RateLimiter
     */
    //@Bean
    public RateLimiter rateLimiter() {
        return RateLimiter.create(0.5);
    }


    /**
     * 限流插件 基于内存
     *
     * @return RateLimiter
     */
    //@Bean
    public LimiterMap limiterMap() {
        //根据预估map中需要放的数据大小,选择合适的初始化
        return new LimiterMap(100);
    }

}
