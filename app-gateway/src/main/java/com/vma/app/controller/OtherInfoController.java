package com.vma.app.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.vma.app.dto.otherinfo.OtherInfoDto;
import com.vma.assist.wraps.LogWrap;
import com.vma.business.entity.OtherInfo;
import com.vma.business.service.IOtherInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取列表
     *
     * @return
     */
    @ApiOperation(value = "获取列表", notes = "获取列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page getPage(OtherInfoDto otherInfoDto) {
        LOG.info("获取列表");
        Page<OtherInfo> page = new Page<>(otherInfoDto.getCurrent(), otherInfoDto.getSize());
        OtherInfo otherInfo = new OtherInfo();
        EntityWrapper<OtherInfo> entityWrapper = new EntityWrapper<>(otherInfo);
        return otherInfoService.selectPage(page, entityWrapper);
    }


    /**
     * 新增
     *
     * @return
     */
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addOtherInfo(@RequestBody OtherInfo otherInfo) {
        LOG.info("新增");
        otherInfoService.insert(otherInfo);
    }


    /**
     * 编辑
     *
     * @return
     */
    @ApiOperation(value = "编辑", notes = "编辑")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateOtherInfo(@RequestBody OtherInfo otherInfo) {
        LOG.info("编辑");
        otherInfoService.updateById(otherInfo);
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
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取详情", notes = "获取详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OtherInfo getDetail(@ApiParam(required = true, name = "id", value = "系统编号") @PathVariable("id") Integer id) {
        LOG.info("获取详情");
        return otherInfoService.selectById(id);
    }


}

