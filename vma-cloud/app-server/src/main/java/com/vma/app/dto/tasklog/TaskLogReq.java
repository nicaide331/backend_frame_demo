package com.vma.app.dto.tasklog;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author hhd
 * @since 2018-10-02
 */
@Data
public class TaskLogReq {

    /**
     *
     */
    @ApiModelProperty(required = true)
    private Integer id;
    /**
     *
     */
    @ApiModelProperty(required = true)
    private Date createTime;
    /**
     *
     */
    @ApiModelProperty(required = true)
    private String describe;
    /**
     *
     */
    @ApiModelProperty(required = true)
    private String cron;
    /**
     *
     */
    @ApiModelProperty(required = true)
    private Date endTime;
    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private Long spendTime;
    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private String exception;
    /**
     *
     */
    @ApiModelProperty(value = "", required = true)
    private String path;
}
