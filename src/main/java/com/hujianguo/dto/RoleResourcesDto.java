/**
 * @Project Name:shiro-first
 * @File Name:RoleResourcesDto.java
 * @Package Name:com.hujianguo.dto
 * @Date: 2019-08-22 11:17:13
 * @author: 胡建国
 * Copyright (c) 2019, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.dto;

import lombok.*;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2019-08-22 11:17:13
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleResourcesDto {
    private Integer id;
    private Integer roleId;
    private String resourcesId;
}
