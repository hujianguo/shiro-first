/**
 * @Project Name:shiro-first
 * @File Name:UserRoleDto.java
 * @Package Name:com.hujianguo.dto
 * @Date: 2019-08-22 10:27:03
 * @author: 胡建国
 * Copyright (c) 2019, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2019-08-22 10:27:03
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleDto {

    private Integer userId;
    private String roleId; // 逗号分隔
}
