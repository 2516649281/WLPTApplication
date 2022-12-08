package com.chunfeng.controller;

import com.chunfeng.entity.User;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceController;
import com.chunfeng.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 *
 * @author by 春风能解释
 * <p>
 * 2022/10/20
 */
@RestController
@RequestMapping("/user")
public class UserController extends ServiceController {

    /**
     * 用户业务层注入
     */
    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @PostMapping("/login")
    public JsonRequest<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return JSON
     */
    @PostMapping("/register")
    public JsonRequest<Integer> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.register(username, password);
    }

    /**
     * 查看所有用户
     *
     * @return JSON
     */
    @GetMapping
    public JsonRequest<List<User>> lookAllUSer() {
        return userService.lookAllUSer();
    }

    /**
     * 查看当前用户
     *
     * @param userId 用户Id
     * @return JSON
     */
    @GetMapping("/{userId}")
    public JsonRequest<User> lookCurrentUser(@PathVariable Integer userId) {
        return userService.lookCurrentUser(userId);
    }

    /**
     * 修改当前用户信息
     *
     * @param user 用户信息
     * @return JSON
     */
    @PutMapping
    public JsonRequest<Integer> updateCurrentUser(@RequestBody User user) {
        return userService.updateCurrentUser(user);
    }

    /**
     * 批量删除用户
     *
     * @param userIds 用户Id
     * @return JSON
     */
    @DeleteMapping
    public JsonRequest<Integer> deleteUsers(@RequestBody Integer[] userIds) {
        return userService.deleteUsers(userIds);
    }
}
