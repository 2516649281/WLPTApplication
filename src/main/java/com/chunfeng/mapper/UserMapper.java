package com.chunfeng.mapper;

import com.chunfeng.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生数据层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/5
 */
public interface UserMapper {
    /**
     * 查询所有用户
     *
     * @return 用户信息列表
     */
    List<User> selectAllUser();

    /**
     * 根据ID值查询用户
     *
     * @param id 用户编号
     * @return ID对应用户信息
     */
    User selectAllUserById(@Param("id") Integer id);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户信息
     */
    User selectAllUserByName(@Param("name") String name);

    /**
     * 添加一条用户信息
     *
     * @param user 用户信息
     * @return 影响行数
     */
    Integer insertUser(User user);

    /**
     * 根据ID值修改一条用户信息
     *
     * @param user 用户信息
     * @return 影响行数
     */
    Integer updateUserById(User user);

    /**
     * 根据ID值删除用户信息
     *
     * @param ids 用户编号
     * @return 影响行数
     */
    Integer deleteUserById(@Param("ids") Integer[] ids);

    /**
     * 根据ID获取数据库中的用户个数
     *
     * @param ids 用户ID
     * @return 个数
     */
    Integer countById(@Param("ids") Integer[] ids);
}
