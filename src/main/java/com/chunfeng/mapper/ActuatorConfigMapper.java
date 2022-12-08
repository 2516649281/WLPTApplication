package com.chunfeng.mapper;

import com.chunfeng.entity.ActuatorConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 执行器配置数据层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/5
 */
public interface ActuatorConfigMapper {
    /**
     * 查询所有执行器配置信息
     *
     * @return 执行器配置信息列表
     */
    List<ActuatorConfig> selectAllActuator();

    /**
     * 根据ID查询执行器配置
     *
     * @param id 编号
     * @return 执行器配置信息
     */
    ActuatorConfig selectAllActuatorById(@Param("id") Integer id);

    /**
     * 根据名称查询执行器配置
     *
     * @param name 名称
     * @return 执行器配置信息列表
     */
    List<ActuatorConfig> selectAllActuatorByName(@Param("name") String name);

    /**
     * 根据设备ID查询执行器配置
     *
     * @param uid 设备ID
     * @return 执行器配置信息列表
     */
    ActuatorConfig selectAllActuatorByEquipId(@Param("uid") Integer uid);

    /**
     * 添加一条执行器配置
     *
     * @param actuatorConfig 执行器信息
     * @return 影响行数
     */
    Integer insertActuator(ActuatorConfig actuatorConfig);

    /**
     * 修改一条执行器配置
     *
     * @param actuatorConfig 执行器信息
     * @return 影响行数
     */
    Integer updateActuatorById(ActuatorConfig actuatorConfig);

    /**
     * 根据ID值删除执行器配置
     *
     * @param ids 编号
     * @return 影响行数
     */
    Integer deleteActuatorById(@Param("ids") Integer[] ids);

    /**
     * 根据ID获取数据库中的执行器个数
     *
     * @param ids 执行器ID
     * @return 个数
     */
    Integer countById(@Param("ids") Integer[] ids);
}
