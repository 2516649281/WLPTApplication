package com.chunfeng.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 业务层Controller异常捕获类
 *
 * @author by 春风能解释
 * <p>
 * 2022/10/20
 */
@Slf4j
public class ServiceController {

    /**
     * 异常捕获方法
     *
     * @param e 全局异常
     * @return JSON
     */
    @ExceptionHandler
    public JsonRequest<Void> getException(Exception e) {
        log.warn("全局异常拦截器捕获到异常:{}", e.getLocalizedMessage());
        return new JsonRequest<>(500, e.getLocalizedMessage(), false);
    }
}
