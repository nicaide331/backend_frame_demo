package com.vma.business.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.vma.business.domain.bo.otherinfo.OtherInfoBo;
import com.vma.business.domain.vo.OtherInfoVo;
import com.vma.business.entity.OtherInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
public interface IOtherInfoDao extends BaseMapper<OtherInfo> {

    /**
     * 查询列表
     *
     * @param otherInfoVo
     * @return
     */
    List<OtherInfoBo> getList(Pagination page, OtherInfoVo otherInfoVo);

}
