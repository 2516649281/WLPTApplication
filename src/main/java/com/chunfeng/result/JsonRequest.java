package com.chunfeng.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JSON请求类
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Data
@NoArgsConstructor
public class JsonRequest<T> implements Serializable {
    /**
     * 状态值
     */
    private Integer status = 200;
    /**
     * 消息
     */
    private String message = "请求已完成!";
    /**
     * 成功请求
     */
    private Boolean success = true;

    private T data = null;

    public JsonRequest(T data) {
        this.data = data;
    }

    public JsonRequest(Integer status, String message, Boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    /**
     * 成功请求
     *
     * @param data 数据
     * @param <T>  类型
     * @return JSON
     */
    public static <T> JsonRequest<T> success(T data) {
        return new JsonRequest<>(data);
    }

    /**
     * 错误请求
     *
     * @param e   异常枚举
     * @param <T> 类型
     * @return JSON
     */
    public static <T> JsonRequest<T> error(ServiceException e) {
        return new JsonRequest<>(e.getStatus(), e.getMessage(), false);
    }
}
