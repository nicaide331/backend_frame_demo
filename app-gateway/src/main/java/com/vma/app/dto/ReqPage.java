package com.vma.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;


/**
 * @author huang
 */
@Data
public class ReqPage {

    @ApiModelProperty(value = "当前分页数", required = true)
    @Min(value = 0, message = "当前分页数不能小于0")
    private Integer current;


    @ApiModelProperty(value = "分页大小", required = true)
    @Min(value = 0, message = "分页大小不能小于0")
    private Integer size;
}
