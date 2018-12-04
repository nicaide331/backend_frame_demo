package com.vma.business.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.vma.business.dao.IOtherInfoDao;
import com.vma.business.domain.bo.otherinfo.OtherInfoBo;
import com.vma.business.domain.vo.OtherInfoVo;
import com.vma.business.entity.OtherInfo;
import com.vma.business.service.IOtherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
@Service
public class OtherInfoServiceImpl extends ServiceImpl<IOtherInfoDao, OtherInfo> implements IOtherInfoService {

    @Autowired
    private IOtherInfoDao otherInfoDao;

    @Override
    public OtherInfoBo getOtherInfoDao(OtherInfoVo otherInfoVo) {
        return new OtherInfoBo();
    }

    @Override
    public Page<OtherInfoBo> getOtherInfoPage(Page<OtherInfoBo> page, OtherInfoVo otherInfoVo) {
        page.setRecords(otherInfoDao.getList(page, otherInfoVo));
        return page;
    }
}
