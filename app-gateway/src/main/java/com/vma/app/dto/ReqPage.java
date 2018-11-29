package com.vma.app.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * @author huang
 */
@Data
public class ReqPage {

    @ApiModelProperty(value = "当前分页数", required = true)
    @NotNull(message = "")
    @Min(value = 0, message = "")
    @Max(value = 10)
    private Integer current;


    @ApiModelProperty(value = "分页大小", required = true)
    @NotNull
    private Integer size;
}
