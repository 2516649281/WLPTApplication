package com.chunfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 设备数据
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node<T> implements Serializable {
    /**
     * ID值
     */
    private Integer id;
    /**
     * 设备ID
     */
    private Integer equipId;
    /**
     * 设备名
     */
    private String name;
    /**
     * 字符
     */
    private String str;
    /**
     * 值
     */
    private T value;
    /**
     * 当前时间戳
     */
    private Long time = System.currentTimeMillis();
}
