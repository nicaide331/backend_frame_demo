package com.vma.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.vma.business.domain.bo.otherinfo.OtherInfoBo;
import com.vma.business.domain.vo.OtherInfoVo;
import com.vma.business.entity.OtherInfo;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
public interface IOtherInfoService extends IService<OtherInfo> {

    /**
     * 通过vo实体查询 返回bo
     * <p>
     * 缓存注解 设置过期时间 在value里用#,单位是秒 key表示键值 用el表达式
     *
     * @return
     */
    @Cacheable(value = "other#30", key = "#otherInfoVo.hashCode()")
    OtherInfoBo getOtherInfoDao(OtherInfoVo otherInfoVo);

    /**
     * 分页查询数据
     * 在控制层封装好 page传入service层
     *
     * @param page
     * @param otherInfoVo
     * @return
     */
    Page<OtherInfoBo> getOtherInfoPage(Page<OtherInfoBo> page, OtherInfoVo otherInfoVo);

}
