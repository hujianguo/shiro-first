package com.hujianguo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hujianguo.domain.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    /**
     * 通过角色 id 删除
     *
     * @param roleIds 角色 id
     */
    void deleteUserRolesByRoleId(List<String> roleIds);

    /**
     * 通过用户 id 删除
     *
     * @param userIds 用户 id
     */
    void deleteUserRolesByUserId(List<String> userIds);
}
