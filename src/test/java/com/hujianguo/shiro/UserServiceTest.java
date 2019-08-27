/**
 * @Project Name:shiro-first
 * @File Name:UserServiceTest.java
 * @Package Name:com.hujianguo.shiro
 * @Date: 2019-08-27 09:41:16
 * @author: 胡建国
 * Copyright (c) 2019, hujianguo@lidoo.com All Rights Reserved.
 */
package com.hujianguo.shiro;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hujianguo.ShiroFirstApplication;
import com.hujianguo.domain.QueryRequest;
import com.hujianguo.domain.User;
import com.hujianguo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 胡建国
 * @Date: 2019-08-27 09:41:16
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载测试需要的类，一定要加入 Spring Boot 的启动类，其次需要加入本类
@SpringBootTest(classes = {ShiroFirstApplication.class})
@ActiveProfiles(profiles = {"dev"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByName() {
        User user = userService.findByName("MrBird");
        System.out.println(user);
    }

    @Test
    public void findUserPage() {
        User user = User.builder().build();
        QueryRequest request = new QueryRequest();
        IPage<User> page = userService.findUserPage(user, request);
        System.out.println(page.getRecords());
    }

    @Test
    public void findUserDetail() {
        User user = userService.findUserDetail("MrBird");
        System.out.println(user);
    }

    @Test
    public void updateLoginTime() {
        userService.updateLoginTime("MrBird");
    }

    @Test
    public void createUser() {
        User user = User.builder().username("zhangsan").email("296351214@qq.com")
                .mobile("14751946428").roleId("1,2").build();
        userService.createUser(user);
    }

    @Test
    public void updateUser() {
        User user = User.builder().userId(9L).roleId("2,77").build();
        userService.updateUser(user);
    }

    @Test
    public void resetPassword() {
        userService.resetPassword("MrBird");
    }

    @Test
    public void register() {
        userService.register("lisi", User.DEFAULT_PASSWORD);
    }

    @Test
    public void updatePassword(){
        userService.updatePassword("MrBird",User.DEFAULT_PASSWORD);
    }

    @Test
    public void updateAvatar(){
        userService.updateAvatar("MrBird",User.DEFAULT_AVATAR);
    }

    @Test
    public void updateTheme(){
        userService.updateTheme("MrBird",User.THEME_BLACK,User.TAB_OPEN);
    }
}
