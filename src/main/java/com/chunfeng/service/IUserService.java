package com.chunfeng.service;

import com.chunfeng.entity.User;
import com.chunfeng.result.JsonRequest;

import java.util.List;

/**
 * 用户业务层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
public interface IUserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    JsonRequest<String> login(String username, String password);

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    JsonRequest<Integer> register(String username, String password);

    /**
     * 查看所有用户
     *
     * @return JSON
     */
    JsonRequest<List<User>> lookAllUSer();

    /**
     * 查看当前用户
     *
     * @param userId 待查看的用户Id
     * @return JSON
     */
    JsonRequest<User> lookCurrentUser(Integer userId);

    /**
     * 修改当前用户信息
     *
     * @param user 待修改的用户信息
     * @return JSON
     */
    JsonRequest<Integer> updateCurrentUser(User user);

    /**
     * 批量删除用户
     *
     * @param userIds 待删除的用户Id
     * @return JSON
     */
    JsonRequest<Integer> deleteUsers(Integer[] userIds);
}
