package com.chunfeng.service;

import com.chunfeng.entity.SensorConfig;
import com.chunfeng.result.JsonRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 传感器配置业务层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
public interface ISensorService {

    /**
     * 查看所有传感器
     *
     * @return JSON
     */
    JsonRequest<List<SensorConfig>> lookAllSensor();

    /**
     * 按名称查看所有传感器
     *
     * @param name 传感器名
     * @return JSON
     */
    JsonRequest<List<SensorConfig>> lookAllSensorByName(@Param("name") String name);

    /**
     * 添加一条传感器信息
     *
     * @param sensorConfig 传感器信息
     * @return JSON
     */
    JsonRequest<Integer> insertOneSensor(SensorConfig sensorConfig);

    /**
     * 修改一条传感器信息
     *
     * @param sensorConfig 传感器信息
     * @return JSON
     */
    JsonRequest<Integer> updateOneSensor(SensorConfig sensorConfig);

    /**
     * 删除传感器信息
     *
     * @param ids 编号
     * @return JSON
     */
    JsonRequest<Integer> deleteSensors(@Param("ids") Integer[] ids);
}
