package com.vma.aop;

import com.alibaba.fastjson.JSON;
import com.vma.assist.global.exception.BadRequestException;
import com.vma.assist.global.exception.BusinessException;
import com.vma.assist.global.exception.HttpNotAuthException;
import com.vma.assist.wraps.LogWrap;
import com.vma.assist.wraps.RequestWrap;
import com.vma.business.constants.IConstants;
import com.vma.core.annotion.IgnoreLogin;
import com.vma.core.annotion.Permission;
import com.vma.core.annotion.PermissionEnum;
import com.vma.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.MDC;
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

    /**
     * 拦截controller
     */
    @Pointcut("execution(* com.vma.*.controller..*.*(..))")
    public void webLog() {
    }


    /**
     * @param joinPoint 切点对象
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = RequestWrap.getRequest();
        MDC.put("requestId", String.valueOf(System.currentTimeMillis()));
        MDC.put("requestUrl", request.getRequestURI());
        loginHandler(joinPoint);
        permissionHandler(joinPoint);

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
        String mac = request.getHeader(IConstants.MAC_KEY);
        //UserDataUtil.updateAdmin(mac);
    }

    /**
     * @param ret 返回的对象
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void afterReturn(Object ret) {
        LOG.info("响应数据为:" + JSON.toJSONString(ret));
        MDC.clear();
    }

    /**
     * @param joinPoint 切点
     */
    private void loginHandler(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        IgnoreLogin ignoreLoginMethod = method.getAnnotation(IgnoreLogin.class);
        IgnoreLogin ignoreLoginClass = joinPoint.getTarget().getClass().getAnnotation(IgnoreLogin.class);
        if (ignoreLoginMethod != null || ignoreLoginClass != null) {
            HttpServletRequest request = RequestWrap.getRequest();
            String mac = request.getHeader(IConstants.MAC_KEY);
            if (StringUtils.isNotEmpty(mac)) {
                String adminId = RedisUtil.get(mac + "_user_id");
                if (adminId == null || "".equals(adminId)) {
                    throw new HttpNotAuthException();
                }
            }
        } else {
            //TODO
            System.out.println("未认证");
            //throw new HttpNotAuthException("未认证");
        }
    }

    /**
     * @param joinPoint 切点
     */
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
                    /*if (!UserDataUtil.isAllowedOperate(value, request)) {
                        throw new BadRequestException();
                    }*/
                    System.out.println(value);
                }
            } else {
                int count = 0;
                for (String value : permissionValues) {
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
