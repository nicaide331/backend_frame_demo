package com.vma.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author huang
 */
@Data
public class ReqPage {

    @ApiModelProperty(value = "当前分页数", required = true)
    private Integer current;
    @ApiModelProperty(value = "分页大小", required = true)
    private Integer size;
}
