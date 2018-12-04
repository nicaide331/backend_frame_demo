package com.vma.app.cloud;

import com.vma.cloud.entity.CloudResponseReceiver;
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
@FeignClient(name = "cloud", url = "${custom.cloud.api}")
public interface IApiCloud {

    /**
     * http调用demo
     *
     * @param type     type
     * @param dateType dateType
     * @param appKey   appKey
     * @return CloudResponseReceiver
     */
    @RequestMapping(value = "statistics/list", method = RequestMethod.POST)
    CloudResponseReceiver getStatis(@RequestParam(value = "type") Integer type,
                                    @RequestParam(value = "date_type") Integer dateType,
                                    @RequestParam(value = "app_key", required = false) String appKey);
}
