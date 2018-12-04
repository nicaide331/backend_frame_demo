package com.vma.app.controller;

import com.vma.app.cloud.IApiCloud;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * chennh
 *
 * @author: chennaihua
 * @version: 1.created by chennaihua on 2018/10/27.
 */
@Api(value = "Cloud 测试类")
@RestController
@RequestMapping(value = "cloud")
public class CloudController {

    @Autowired
    private IApiCloud apiCloud;

    /**
     * @param type     type
     * @param dateType dateType
     * @param appKey   appKey
     * @return
     */
    @ApiOperation(value = "测试feign")
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Object test(@RequestParam(value = "type", defaultValue = "1") Integer type,
                       @RequestParam(value = "date_type", defaultValue = "1") Integer dateType,
                       @RequestParam(value = "appKey", defaultValue = "app_key") String appKey) {
        return apiCloud.test(type, dateType, appKey);
    }
}
