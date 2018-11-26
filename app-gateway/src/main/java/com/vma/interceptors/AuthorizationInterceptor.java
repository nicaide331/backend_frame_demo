package com.vma.interceptors;

import com.vma.authorization.service.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ${DESCRIPTION}
 *
 * @author: chennaihua
 * @version: 1.created by chennaihua on 2018/10/23.
 */
//@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Autowired
    private IAuthorizationService authorizationService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //authorizationService.verify(request);
        return true;
    }
}
