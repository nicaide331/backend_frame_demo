package com.vma.app.controller;


import com.vma.assist.wraps.LogWrap;
import com.vma.boost.rbac.domain.bo.RbacAccountRelateBo;
import com.vma.boost.rbac.entity.RbacAccount;
import com.vma.boost.rbac.provider.IRbacAccountProvider;
import com.vma.boost.rbac.service.IRbacAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 账户表 控制器
 * </p>
 *
 * @author hhd
 * @since 2018-11-07
 */
@RestController
@RequestMapping("/v1.0/rbacAccount")
@Api(value = "RbacAccountController", description = "账户表相关", tags = {"RbacAccountController"})
public class RbacAccountController {
    private static final Logger LOG = LogWrap.getLogger(RbacAccountController.class);

    @Autowired
    private IRbacAccountService rbacAccountService;

    @Autowired
    private IRbacAccountProvider rbacAccountProvider;


    /**
     * 获取账户表列表
     *
     * @return
     */
    @ApiOperation(value = "获取账户表列表", notes = "获取账户表列表")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public RbacAccount getPage(RbacAccount rbacAccount) {
        LOG.info("获取账户表列表");
        return rbacAccountService.login(rbacAccount.getUsername(), rbacAccount.getPassword());
    }


    /**
     * 新增账户表
     *
     * @return
     */
    @ApiOperation(value = "新增账户表", notes = "新增账户表")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addRbacAccount(@RequestBody RbacAccount rbacAccount) {
        LOG.info("新增账户表");
        rbacAccountService.register(rbacAccount.getUsername(), rbacAccount.getPassword());
    }


    /**
     * 编辑账户表
     *
     * @return
     */
    @ApiOperation(value = "编辑账户表", notes = "编辑账户表")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateRbacAccount(@RequestBody RbacAccount rbacAccount) {
        LOG.info("编辑账户表");

    }

    /**
     * 删除账户表信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除账户表信息", notes = "删除账户表信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteRbacAccount(@ApiParam(required = true, name = "id", value = "系统编号") @PathVariable("id") String id) {
        LOG.info("删除账户表信息");
        rbacAccountService.delete(id);
    }


    /**
     * 获取账户表详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取账户表详情", notes = "获取账户表详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RbacAccount getDetail(@ApiParam(required = true, name = "id", value = "系统编号") @PathVariable("id") String id) {
        LOG.info("获取账户表详情");
        List<RbacAccountRelateBo> list = rbacAccountProvider.listAccountRelate("8", "user");
        System.out.println(list);
        return null;
    }


}

