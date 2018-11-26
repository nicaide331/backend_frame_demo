package com.vma.business.service;


import com.baomidou.mybatisplus.service.IService;
import com.vma.business.entity.Demo;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hhd
 * @since 2018-09-29
 */
public interface IDemoService extends IService<Demo> {

    /**
     * @param otherInfo 对象
     * @return 对象
     */
    @CachePut(value = "otherinfo", key = "#p0.id")
    Demo save(Demo otherInfo);

    /**
     * @param id id
     * @return 对象
     */
    @Cacheable(value = "otherinfo#30", key = "#id")
    Demo get(Integer id);

}
