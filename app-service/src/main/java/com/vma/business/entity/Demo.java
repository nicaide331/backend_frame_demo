package com.vma.business.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.vma.mybatis.inlay.VmaModel;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author hhd
 * @since 2018-09-29
 */
@Data
@TableName("other_info")
public class Demo extends VmaModel<Demo, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableField("other_key")
    private String otherKey;
    /**
     * v
     */
    @TableField("other_value")
    private String otherValue;
    /**
     * 分组数据
     */
    @TableField("other_group")
    private String otherGroup;
    /**
     * 备注
     */
    private String remarks;


    @Override
    public String toString() {
        return super.toString();
    }
}
