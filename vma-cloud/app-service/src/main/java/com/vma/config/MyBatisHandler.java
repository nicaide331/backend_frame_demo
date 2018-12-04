package com.vma.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 注入公共字段自动填充,任选注入方式即可
 */
@Component
public class MyBatisHandler extends MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        //"新增的时候干点不可描述的事情"
        Object createDate = getFieldValByName("createTime", metaObject);
        if (null == createDate) {
            setFieldValByName("createTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //"更新的时候干点不可描述的事情"
    }
}
