package com.vma.app.controller;


import com.vma.app.dto.otherinfo.OtherInfoReq;
import com.vma.assist.global.exception.BusinessException;
import com.vma.assist.wraps.LogWrap;
import com.vma.business.entity.Demo;
import com.vma.business.service.IDemoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by huangjb on 2018/2/23.
 */
@RestController
@RequestMapping("/v1.0/exception")
@Api(value = "ExceptionTestController", description = "异常模块测试", tags = {"ExceptionTestController"})
public class ExceptionTestController {
    private static final Logger LOG = LogWrap.getLogger(ExceptionTestController.class);

    @Autowired
    private IDemoService otherInfoService;

    /**
     * @return String
     */
    @GetMapping("/test")
    public String test() {
        Demo otherInfo = new Demo();
        otherInfo.setOtherKey("3333");
        otherInfoService.save(otherInfo);
        otherInfo.setOtherKey("4444");
        otherInfoService.updateById(otherInfo);
        Demo oi = otherInfoService.get(otherInfo.getId());

        return oi.getOtherKey();
    }


    /**
     * @return String
     */
    @GetMapping("/test1")
    public String test1() {
        throw new BusinessException("业务异常");
    }


    /**
     * @param date data
     * @return string
     */
    @GetMapping("/testDate")
    public Date testDate(OtherInfoReq date) {
        return date.getDate();
    }

}

