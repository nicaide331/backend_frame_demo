package com.vma.app.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * http调用Demo
 *
 * @author: chennaihua
 * @version: 1.created by chennaihua on 2018/10/27.
 */
@FeignClient(name = "${feign.server-name}")
public interface IApiCloud {

    /**
     * http调用demo
     *
     * @param type     type
     * @param dateType dateType
     * @param appKey   appKey
     * @return Object
     */
    @RequestMapping(value = "cloud/test", method = RequestMethod.GET)
    Object test(@RequestParam(value = "type") Integer type,
                @RequestParam(value = "date_type") Integer dateType,
                @RequestParam(value = "app_key", required = false) String appKey);
}
