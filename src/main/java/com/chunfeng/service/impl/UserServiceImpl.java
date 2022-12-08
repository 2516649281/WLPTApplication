package com.chunfeng.service.impl;

import com.chunfeng.entity.User;
import com.chunfeng.mapper.UserMapper;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.service.IUserService;
import com.chunfeng.utils.PasswordEncoderUtils;
import com.chunfeng.utils.RedisClientsUtils;
import com.chunfeng.utils.SqlDateUtils;
import com.chunfeng.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户业务层实现类
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements IUserService {
    /**
     * 获取当前时间
     */
    private final Date date = SqlDateUtils.date;
    /**
     * 用户mapper注入
     */
    @Autowired
    private UserMapper userMapper;
    /**
     * redis客户端注入
     */
    @Autowired
    private RedisClientsUtils redisClientsUtils;

    /**
     * 注入用户Id
     */
    @Autowired
    private SqlDateUtils sqlDateUtils;
    /**
     * 密码加密注入
     */
    @Autowired
    private PasswordEncoderUtils passwordEncoderUtils;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @Override
    public JsonRequest<String> login(String username, String password) {
        //判断用户信息是否存在
        User user = userMapper.selectAllUserByName(username);
        if (Objects.isNull(user) || user.getStatus() == 1) {
            log.warn("名为{}的用户不存在!", username);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //加密密码并检验
        if (!passwordEncoderUtils.check(password, user.getPassword())) {
            log.error("用户密码错误!");
            return JsonRequest.error(ServiceException.LOGIN_ERROR);
        }
        //将用户Id存入Redis
        redisClientsUtils.set("user", user.getId().toString());
        //创建token
        String token = TokenUtils.createToken(user.getId());
        log.info("名为{}的用户登录成功!", username);
        return JsonRequest.success(token);
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public JsonRequest<Integer> register(String username, String password) {
        User userByName = userMapper.selectAllUserByName(username);
        //判断用户是否已经存在
        if (Objects.nonNull(userByName)) {
            log.warn("名为{}的用户已在数据库中找到!", username);
            return JsonRequest.error(ServiceException.REGISTER_ERROR);
        }
        User user = new User();
        user.setName(username);
        user.setPassword(passwordEncoderUtils.encode(password));
        //自动插入
        user.setCreateUser(0);
        user.setCreateTime(date);
        //添加逻辑
        Integer column = userMapper.insertUser(user);
        if (column < 1) {
            log.error("名为{}的用户注册失败!", username);
            return JsonRequest.error(ServiceException.REGISTER_ERROR);
        }
        log.info("名为{}的用户注册成功!", username);
        return JsonRequest.success(column);
    }


    /**
     * 查看所有用户
     *
     * @return JSON
     */
    @Override
    @Cacheable(value = "user")
    public JsonRequest<List<User>> lookAllUSer() {
        List<User> users = userMapper.selectAllUser();
        if (users.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条用户信息", users.size());
        return JsonRequest.success(users);
    }

    /**
     * 查看当前用户
     *
     * @param userId 用户Id
     * @return JSON
     */
    @Override
    @Cacheable(value = "user", key = "#userId")
    public JsonRequest<User> lookCurrentUser(Integer userId) {
        User user = userMapper.selectAllUserById(userId);
        if (Objects.isNull(user)) {
            log.warn("Id为{}的用户不存在!", userId);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到Id为{}的用户!", userId);
        return JsonRequest.success(user);
    }

    /**
     * 修改当前用户信息
     *
     * @param user 用户信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public JsonRequest<Integer> updateCurrentUser(User user) {
        User selectUserById = userMapper.selectAllUserById(user.getId());
        if (Objects.isNull(selectUserById)) {
            log.warn("没有查询到Id为{}的用户!", user.getId());
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //填充
        user.setUpdateUser(sqlDateUtils.getCurrentUserId());
        user.setUpdateTime(date);
        Integer column = userMapper.updateUserById(user);
        if (column < 1) {
            log.error("Id为{}的用户修改失败!返回值{}", user.getId(), column);
            return JsonRequest.error(ServiceException.UPDATE_ERROR);
        }
        log.info("Id为{}的用户修改成功!返回值{}", user.getId(), column);
        return JsonRequest.success(column);
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户Id
     * @return JSON
     */
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public JsonRequest<Integer> deleteUsers(Integer[] userIds) {
        //判断用户信息是否完整
        Integer count = userMapper.countById(userIds);
        if (count < userIds.length) {
            log.warn("待删除的用户个数与数据库已存在的用户个数不符!数据库实际存在{}个,需要{}个", count, userIds.length);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //获取redis中的用户信息
        String user = redisClientsUtils.get("user");
        //判断待删除的用户与已登陆用户是否一致
        for (Integer userId : userIds) {
            //判断该用户是否已登录
            if (userId.toString().equals(user)) {
                log.warn("Id为{}的用户已登陆!", user);
                return JsonRequest.error(ServiceException.DELETE_ERROR);
            }
        }
        //删除逻辑
        Integer column = userMapper.deleteUserById(userIds);
        if (column < 1) {
            log.error("删除用户遇到错误!返回值{}", column);
            return JsonRequest.error(ServiceException.DELETE_ERROR);
        }
        log.info("已删除{}条用户信息!返回值{}", count, column);
        return JsonRequest.success(column);
    }
}
