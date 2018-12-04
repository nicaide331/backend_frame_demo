package com.vma.business.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
@Data
@TableName("other_info")
public class OtherInfo extends Model<OtherInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Integer id;
    /**
     * key
     */
    @TableField("other_key")
    @ApiModelProperty(value = "key")
    private String otherKey;
    /**
     * v
     */
    @TableField("other_value")
    @ApiModelProperty(value = "v")
    private String otherValue;
    /**
     * 分组数据
     */
    @TableField("other_group")
    @ApiModelProperty(value = "分组数据")
    private String otherGroup;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
