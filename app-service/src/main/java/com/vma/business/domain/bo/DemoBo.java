package com.vma.business.domain.bo;

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
public class DemoBo {

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
