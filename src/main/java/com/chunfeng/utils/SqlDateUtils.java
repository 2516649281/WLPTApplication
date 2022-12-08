package com.chunfeng.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * 数据库自动填充时间工具类
 *
 * @author by 春风能解释
 * <p>
 * 2022/10/20
 */
@Component
public class SqlDateUtils {


    /**
     * 数据库时间
     */
    public static Date date = new Date(System.currentTimeMillis());
    /**
     * redis客户端注入
     */
    @Autowired
    private RedisClientsUtils redisClientsUtils;

    /**
     * 获取当前登录用户
     *
     * @return 登录用户ID
     */
    public Integer getCurrentUserId() {
        return Integer.valueOf(redisClientsUtils.get("user"));
    }
}
