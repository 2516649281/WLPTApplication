package com.chunfeng.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * 设备表
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/5
 */
@Data
@NoArgsConstructor
public class Equipment implements Serializable {
    /**
     * 编号
     */
    private Integer id;
    /**
     * 设备编号
     */
    private String uid;
    /**
     * 通道号
     */
    private Integer num;
    /**
     * 设备名
     */
    private String name;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改人
     */
    private Integer updateUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
