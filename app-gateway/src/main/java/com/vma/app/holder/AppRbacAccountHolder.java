package com.vma.app.holder;

import com.vma.boost.rbac.domain.bo.RbacAccountBo;
import com.vma.boost.rbac.permission.IRbacAccountHolder;

/**
 * DESCRIPTION
 *
 * @author: chennaihua
 * @version: 1.created by chennaihua on 2018/11/15.
 */
public class AppRbacAccountHolder implements IRbacAccountHolder {
    @Override
    public RbacAccountBo getCurrentRbacAccountBo() {
        return null;
    }
}
