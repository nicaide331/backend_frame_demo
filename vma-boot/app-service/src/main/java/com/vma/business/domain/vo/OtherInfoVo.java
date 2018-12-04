package com.vma.business.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author hhd
 * @since 2018-11-29
 */
@Data
public class OtherInfoVo {

    /**
     * 
     */
    @ApiModelProperty(value = "")
    private Integer id;
    /**
     * key
     */
    @ApiModelProperty(value = "key")
    private String otherKey;
    /**
     * v
     */
    @ApiModelProperty(value = "v")
    private String otherValue;
    /**
     * 分组数据
     */
    @ApiModelProperty(value = "分组数据")
    private String otherGroup;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;
}
