package com.chunfeng.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务层异常枚举
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Getter
@AllArgsConstructor
public enum ServiceException {
    /**
     * 登录异常
     */
    LOGIN_ERROR(400, "登录失败!"),
    /**
     * 非法访问
     */
    UNAUTHORIZED(401, "非法访问!"),
    /**
     * 注册失败
     */
    REGISTER_ERROR(402, "注册失败!"),
    /**
     * 注册用户重复
     */
    REGISTER_EXISTS(403, "待注册的用户已存在!"),
    /**
     * 数据为空
     */
    NOT_FOUND(404, "请求的数据为空!"),
    /**
     * 查询异常
     */
    SELECT_ERROR(405, "查询数据时发生未知异常!"),
    /**
     * 添加异常
     */
    INSERT_ERROR(406, "添加数据时发生未知异常!"),
    /**
     * 修改异常
     */
    UPDATE_ERROR(407, "修改数据时发生未知异常!"),
    /**
     * 删除异常
     */
    DELETE_ERROR(408, "删除数据时发生未知异常!");

    /**
     * 状态值
     */
    private Integer status;
    /**
     * 消息
     */
    private String message;

}
