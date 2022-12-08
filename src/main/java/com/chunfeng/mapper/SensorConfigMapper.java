package com.chunfeng.mapper;

import com.chunfeng.entity.SensorConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 传感器配置数据层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/5
 */
public interface SensorConfigMapper {
    /**
     * 查询所有传感器配置信息
     *
     * @return 传感器配置信息列表
     */
    List<SensorConfig> selectAllSensor();

    /**
     * 根据ID查询传感器配置
     *
     * @param id 编号
     * @return 传感器配置信息
     */
    SensorConfig selectAllSensorById(@Param("id") Integer id);

    /**
     * 根据名称查询传感器配置
     *
     * @param name 名称
     * @return 传感器配置信息列表
     */
    List<SensorConfig> selectAllSensorByName(@Param("name") String name);

    /**
     * 根据设备ID查询传感器配置
     *
     * @param uid 设备ID
     * @return 传感器配置信息列表
     */
    List<SensorConfig> selectAllSensorByEquipId(@Param("uid") String uid);

    /**
     * 添加一条传感器配置
     *
     * @param sensorConfig 传感器信息
     * @return 影响行数
     */
    Integer insertSensor(SensorConfig sensorConfig);

    /**
     * 修改一条传感器配置
     *
     * @param sensorConfig 传感器信息
     * @return 影响行数
     */
    Integer updateSensorById(SensorConfig sensorConfig);

    /**
     * 根据ID值删除传感器配置
     *
     * @param ids 编号
     * @return 影响行数
     */
    Integer deleteSensorById(@Param("ids") Integer[] ids);

    /**
     * 根据ID获取数据库中的传感器个数
     *
     * @param ids 传感器ID
     * @return 个数
     */
    Integer countById(@Param("ids") Integer[] ids);
}
