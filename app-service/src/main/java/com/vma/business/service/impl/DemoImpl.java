package com.vma.business.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.vma.business.dao.DemoDao;
import com.vma.business.entity.Demo;
import com.vma.business.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hhd
 * @since 2018-09-29
 */
@Service
public class DemoImpl extends ServiceImpl<DemoDao, Demo> implements IDemoService {
    @Autowired
    private DemoDao otherInfoDao;

    @Override
    public Demo save(Demo otherInfo) {
        otherInfoDao.insert(otherInfo);
        return otherInfo;
    }

    @Override
    public Demo get(Integer id) {
        return otherInfoDao.selectById(id);
    }
}
