package com.vma.business.domain.vo;

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
public class DemoVo {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * key
     */
    private String otherKey;
    /**
     * v
     */
    private String otherValue;
    /**
     * 分组数据
     */
    private String otherGroup;
    /**
     * 备注
     */
    private String remarks;


}
