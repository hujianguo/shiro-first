package com.hujianguo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hujianguo.constanst.Constant;
import com.hujianguo.domain.QueryRequest;
import com.hujianguo.domain.User;
import com.hujianguo.domain.UserRole;
import com.hujianguo.mapper.UserMapper;
import com.hujianguo.mapper.UserRoleMapper;
import com.hujianguo.service.UserRoleService;
import com.hujianguo.service.UserService;
import com.hujianguo.util.FebsUtil;
import com.hujianguo.util.PasswordHelper;
import com.hujianguo.util.SortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User findByName(String username) {
        return this.baseMapper.findByName(username);
    }

    @Override
    public IPage<User> findUserPage(User user, QueryRequest request) {
        Page<User> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "userId", Constant.ORDER_ASC, false);
        return this.baseMapper.findUserPage(page, user);
    }

    @Override
    public User findUserDetail(String username) {
        User param = new User();
        param.setUsername(username);
        List<User> users = this.baseMapper.findUserDetail(param);
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    @Transactional
    public void updateLoginTime(String username) {
        User user = User.builder().lastLoginTime(new Date()).build();
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void createUser(User user) {
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_OPEN);
        user.setPassword(PasswordHelper.encryptPassword(user.getUsername(), User.DEFAULT_PASSWORD));
        save(user);
        // 保存用户角色
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String[] userIds) {
        List<String> list = Arrays.asList(userIds);
        // 删除用户
        this.removeByIds(list);
        // 删除关联角色
        this.userRoleService.deleteUserRolesByUserId(list);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        // 更新用户
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        updateById(user);
        String[] roles = user.getRoleId().split(StringPool.COMMA);
        setUserRoles(user, roles);

//        User currentUser = FebsUtil.getCurrentUser();
//        if (StringUtils.equalsIgnoreCase(currentUser.getUsername(), user.getUsername())) {
//            shiroRealm.clearCache();
//        }
    }

    @Override
    @Transactional
    public void resetPassword(String... usernames) {
        Arrays.stream(usernames).forEach(username -> {
            User user = new User();
            user.setPassword(PasswordHelper.encryptPassword(username, User.DEFAULT_PASSWORD));
            user.setModifyTime(new Date());
            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        });
    }

    @Override
    @Transactional
    public void register(String username, String password) {
        User user = new User();
        user.setPassword(PasswordHelper.encryptPassword(username, password));
        user.setUsername(username);
        user.setCreateTime(new Date());
        user.setStatus(User.STATUS_VALID);
        user.setSex(User.SEX_UNKNOW);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setTheme(User.THEME_BLACK);
        user.setIsTab(User.TAB_OPEN);
        user.setDescription("注册用户");
        save(user);

        String[] roles = {"2"};

        setUserRoles(user,roles);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        User user = new User();
        user.setPassword(PasswordHelper.encryptPassword(username, password));
        user.setModifyTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateAvatar(String username, String avatar) {
        User user = new User();
        user.setAvatar(avatar);
        user.setModifyTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateTheme(String username, String theme, String isTab) {
        User user = new User();
        user.setTheme(theme);
        user.setIsTab(isTab);
        user.setModifyTime(new Date());
        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void updateProfile(User user) {
        user.setUsername(null);
        user.setRoleId(null);
        user.setPassword(null);
        updateById(user);
    }

    private void setUserRoles(User user, String[] roles) {
        // 根据用户id先删除关系
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getUserId()));

        List<UserRole> userRoles = new ArrayList<>();
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(Long.valueOf(roleId));
            userRoles.add(ur);
        });
        userRoleService.saveBatch(userRoles);
    }

}
