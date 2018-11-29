package com.vma.app.dto.otherinfo;

import com.vma.app.dto.ReqPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * <p>
 * 分页继承reqpage对象  dto只用来接收参数 不能向service层传递
 * </p>
 * <p>
 * 对象要通过注解来校验是否合法 注解参考:https://blog.csdn.net/y550918116j/article/details/78258916
 * 控制层要写 @Valid 注解才会生效
 *
 * @author hhd
 * @since 2018-11-29
 */
@Data
public class OtherInfoDto extends ReqPage {

    /**
     * 通过注解实现数据校验
     */
    @ApiModelProperty(value = "")
    @NotNull(message = "id不能为空")
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
