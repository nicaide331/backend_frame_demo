package com.vma.app.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.vma.app.dto.otherinfo.OtherInfoDto;
import com.vma.assist.wraps.BeanWrap;
import com.vma.assist.wraps.LogWrap;
import com.vma.business.domain.bo.otherinfo.OtherInfoBo;
import com.vma.business.domain.vo.OtherInfoVo;
import com.vma.business.entity.OtherInfo;
import com.vma.business.service.IOtherInfoService;
import com.vma.ratelimiter.annotation.VmaLimit;
import com.vma.ratelimiter.aop.LimitType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 控制器
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
@RestController
@RequestMapping("/app/v1.0/otherInfo")
@Api(value = "OtherInfoController", description = "相关", tags = {"OtherInfoController"})
public class OtherInfoController {
    private static final Logger LOG = LogWrap.getLogger(OtherInfoController.class);

    @Autowired
    private IOtherInfoService otherInfoService;


    /**
     * 分页查询
     * 分页实体继承reqpage  返回的对象统一用Page
     *
     * @return
     */
    @ApiOperation(value = "获取列表", notes = "获取列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<OtherInfoBo> getPage(@Valid OtherInfoDto otherInfoDto) {
        LOG.info("获取列表");
        Page<OtherInfoBo> page = new Page<>(otherInfoDto.getCurrent(), otherInfoDto.getSize());
        OtherInfoVo otherInfo = new OtherInfoVo();
        return otherInfoService.getOtherInfoPage(page, otherInfo);
    }


    /**
     * 新增
     *
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @VmaLimit(name = "", key = "", period = 60, count = 60, limitType = LimitType.API)
    public void addOtherInfo(@RequestBody OtherInfo otherInfo) {
        LOG.info("新增");
        //单个插入
        otherInfoService.insert(otherInfo);
        //批量插入
        List<OtherInfo> arrayList = new ArrayList<>();
        otherInfoService.insertBatch(arrayList);
    }


    /**
     * 编辑
     * 接受dto 转为entity 在业务层进行操作
     *
     * @return
     */
    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateOtherInfo(@RequestBody OtherInfoDto otherInfoDto) {
        LOG.info("编辑");
        OtherInfo otherInfo = new OtherInfo();
        BeanWrap.copyProperties(otherInfoDto, otherInfo);
        //更新实体 为空的字段不更新到数据库
        otherInfoService.updateById(otherInfo);
        //更新实体  为空的字段到数据库
        otherInfoService.updateAllColumnById(otherInfo);
    }

    /**
     * 删除信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除信息", notes = "删除信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteOtherInfo(@ApiParam(required = true, name = "id", value = "系统编号") @PathVariable("id") Integer id) {
        LOG.info("删除信息");
        otherInfoService.deleteById(id);
    }


    /**
     * 获取详情
     * 通过dto接受参数 返回bo
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详情", notes = "获取详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OtherInfoBo getDetail(@ApiParam(required = true, name = "id", value = "系统编号") @PathVariable("id") Integer id) {
        LOG.info("获取详情");
        OtherInfoDto otherInfoDto = new OtherInfoDto();
        OtherInfoVo otherInfoVo = new OtherInfoVo();
        BeanWrap.copyProperties(otherInfoDto, otherInfoDto);
        return otherInfoService.getOtherInfoDao(otherInfoVo);
    }


}

