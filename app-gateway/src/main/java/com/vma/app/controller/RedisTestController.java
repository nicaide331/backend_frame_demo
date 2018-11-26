package com.vma.app.controller;


import cn.hutool.http.HttpUtil;
import com.vma.assist.wraps.LogWrap;
import com.vma.business.service.IDemoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by huangjb on 2018/2/23.
 */
@RestController
@RequestMapping("/v1.0/redis")
@Api(value = "RedisTestController", description = "redis模块测试", tags = {"RedisTestController"})
public class RedisTestController {
    private static final Logger LOG = LogWrap.getLogger(RedisTestController.class);

    @Autowired
    private IDemoService otherInfoService;

    /**
     * @return String
     */
    @GetMapping("/test")
    public String test() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "1");
        String result = HttpUtil.post("http://114.118.93.5:57001/handle/sendMsg", map);
        return result;
    }

}

