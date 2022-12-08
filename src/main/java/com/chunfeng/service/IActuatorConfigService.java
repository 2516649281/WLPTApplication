package com.chunfeng.service;

import com.chunfeng.entity.ActuatorConfig;
import com.chunfeng.result.JsonRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 执行器配置业务层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
public interface IActuatorConfigService {

    /**
     * 查看所有执行器
     *
     * @return JSON
     */
    JsonRequest<List<ActuatorConfig>> lookAllActuator();

    /**
     * 按名称查看所有执行器
     *
     * @param name 执行器名
     * @return JSON
     */
    JsonRequest<List<ActuatorConfig>> lookAllActuatorByName(@Param("name") String name);

    /**
     * 添加一条执行器信息
     *
     * @param actuatorConfig 执行器信息
     * @return JSON
     */
    JsonRequest<Integer> insertOneActuator(ActuatorConfig actuatorConfig);

    /**
     * 修改一条执行器信息
     *
     * @param actuatorConfig 执行器信息
     * @return JSON
     */
    JsonRequest<Integer> updateOneActuator(ActuatorConfig actuatorConfig);

    /**
     * 删除执行器信息
     *
     * @param ids 编号
     * @return JSON
     */
    JsonRequest<Integer> deleteActuators(@Param("ids") Integer[] ids);
}
