package com.chunfeng.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * 执行器配置
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@Data
@NoArgsConstructor
public class ActuatorConfig implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 设备ID
     */
    private Integer equipId;
    /**
     * 配置名
     */
    private String name;
    /**
     * 数值集合
     */
    private String valueList;
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
