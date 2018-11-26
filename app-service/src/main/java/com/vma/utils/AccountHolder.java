package com.vma.utils;

import cn.hutool.json.JSONUtil;
import com.vma.assist.wraps.RequestWrap;
import com.vma.boost.rbac.domain.bo.RbacAccountBo;
import com.vma.boost.rbac.permission.IRbacAccountHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 账户
 *
 * @author huang
 * @create 2018-11-14 21:34
 **/
@Component
public class AccountHolder implements IRbacAccountHolder {

    @Override
    public RbacAccountBo getCurrentRbacAccountBo() {
        HttpServletRequest request = RequestWrap.getRequest();
        String accountJson = RedisUtil.get(request.getHeader("macKey"));
        return JSONUtil.toBean(accountJson, RbacAccountBo.class);
    }
}
