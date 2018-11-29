package com.vma.app.dto.otherinfo;

import com.vma.app.dto.ReqPage;
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
public class OtherInfoDto extends ReqPage {

    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private Integer id;
    /**
     * key
     */
    @ApiModelProperty(value = "key", required = true)
    private String otherKey;
    /**
     * v
     */
    @ApiModelProperty(value = "v", required = true)
    private String otherValue;
    /**
     * 分组数据
     */
    @ApiModelProperty(value = "分组数据", required = true)
    private String otherGroup;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", required = true)
    private String remarks;
}
