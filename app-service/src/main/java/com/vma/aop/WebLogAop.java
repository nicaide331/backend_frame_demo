package com.vma.aop;

import com.alibaba.fastjson.JSON;
import com.vma.assist.global.exception.BadRequestException;
import com.vma.assist.global.exception.BusinessException;
import com.vma.assist.wraps.LogWrap;
import com.vma.assist.wraps.RequestWrap;
import com.vma.authorization.service.IAuthorizationService;
import com.vma.core.annotion.IgnoreLogin;
import com.vma.core.annotion.Permission;
import com.vma.core.annotion.PermissionEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * 统一日志处理
 *
 * @author zhangshilin
 * @date 2017/9/7
 */
@Aspect
@Order(2)
@Component
public class WebLogAop {

    private static final Logger LOG = LogWrap.getLogger(WebLogAop.class);

    @Autowired
    private IAuthorizationService authorizationService;

    /**
     * 拦截控制器
     */
    @Pointcut("execution(* com.vma.*.controller..*.*(..))")
    public void webLog() {
    }


    /**
     * @param joinPoint 切点
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = RequestWrap.getRequest();
        MDC.put("requestId", String.valueOf(System.currentTimeMillis()));
        MDC.put("requestUrl", request.getRequestURI());
        //loginHandler(joinPoint);
        //permissionHandler(joinPoint);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg == null || arg instanceof HttpServletResponse || arg instanceof HttpServletRequest) {
                continue;
            }
            try {
                LOG.info("请求参数为:" + JSON.toJSONString(arg));
            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("请求参数转换json失败");
            }
        }
    }

    /**
     * @param ret 控制器返回对象
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterReturn(Object ret) {
        String requestId = MDC.get("requestId");
        String interval = "";
        if (StringUtils.isNotBlank(requestId)) {
            Long startTime = Long.valueOf(requestId);
            Long endTime = System.currentTimeMillis();
            interval = "处理时间:" + (endTime - startTime) + "ms";
        }
        LOG.info(interval + "   响应数据为:" + JSON.toJSONString(ret));
        MDC.clear();
    }

    /**
     * 登录处理
     *
     * @param joinPoint 切点对象
     */
    @Deprecated
    private void loginHandler(JoinPoint joinPoint) {
        HttpServletRequest request = RequestWrap.getRequest();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IgnoreLogin ignoreLoginMethod = method.getAnnotation(IgnoreLogin.class);
        IgnoreLogin ignoreLoginClass = joinPoint.getTarget().getClass().getAnnotation(IgnoreLogin.class);
        if (ignoreLoginMethod != null || ignoreLoginClass != null) {
            if (ignoreLoginMethod != null && !ignoreLoginMethod.value()) {
                authorizationService.verify(request);
            }
            if (ignoreLoginClass != null && !ignoreLoginClass.value()) {
                authorizationService.verify(request);
            }
        } else {
            authorizationService.verify(request);
        }
    }

    /**
     * @param joinPoint 切点对象
     */
    @Deprecated
    private void permissionHandler(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        if (permission != null) {
            String[] permissionValues = permission.value();
            if (permissionValues == null || permissionValues.length == 0) {
                throw new BusinessException("后端权限配置错误");
            }
            PermissionEnum permissionRelate = permission.relation();
            HttpServletRequest request = RequestWrap.getRequest();
            if (permissionRelate == PermissionEnum.AND) {
                for (String value : permissionValues) {
                    //TODO
                    /*if (!UserDataUtil.isAllowedOperate(value, request)) {
                        throw new BadRequestException();
                    }*/
                    System.out.println(value);
                }
            } else {
                int count = 0;
                for (String value : permissionValues) {
                    //TODO
                    /*if (!UserDataUtil.isAllowedOperate(value, request)) {
                        count++;
                    }*/
                    System.out.println(value);
                }
                if (count == permissionValues.length) {
                    throw new BadRequestException();
                }
            }
        }
    }


}
