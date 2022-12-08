package com.chunfeng.service.impl;

import com.chunfeng.entity.SensorConfig;
import com.chunfeng.mapper.SensorConfigMapper;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.service.ISensorService;
import com.chunfeng.utils.SqlDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * 传感器配置业务层实现类
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Service
@Transactional
@Slf4j
public class SensorServiceImpl implements ISensorService {

    /**
     * 获取当前时间
     */
    private final Date date = SqlDateUtils.date;
    /**
     * 传感器配置数据层注入
     */
    @Autowired
    private SensorConfigMapper sensorMapper;
    /**
     * 注入用户Id
     */
    @Autowired
    private SqlDateUtils sqlDateUtils;

    /**
     * 查看所有传感器配置
     *
     * @return JSON
     */
    @Override
    @Cacheable(value = "sensor")
    public JsonRequest<List<SensorConfig>> lookAllSensor() {
        List<SensorConfig> sensor = sensorMapper.selectAllSensor();
        if (sensor.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条传感器配置信息", sensor.size());
        return JsonRequest.success(sensor);
    }

    /**
     * 按名称查看所有传感器配置
     *
     * @param name 传感器配置名
     * @return JSON
     */
    @Override
    @Cacheable(value = "sensor", key = "#name")
    public JsonRequest<List<SensorConfig>> lookAllSensorByName(String name) {
        List<SensorConfig> sensor = sensorMapper.selectAllSensorByName(name);
        if (sensor.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条传感器配置信息", sensor.size());
        return JsonRequest.success(sensor);
    }

    /**
     * 添加一条传感器配置信息
     *
     * @param sensor 传感器配置信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "sensor", allEntries = true)
    public JsonRequest<Integer> insertOneSensor(SensorConfig sensor) {
        sensor.setCreateUser(sqlDateUtils.getCurrentUserId());
        sensor.setCreateTime(date);
        Integer column = sensorMapper.insertSensor(sensor);
        if (column < 1) {
            log.error("名为{}的传感器配置添加失败!", sensor.getName());
            return JsonRequest.error(ServiceException.INSERT_ERROR);
        }
        log.info("名为{}的传感器配置添加成功!", sensor.getName());
        return JsonRequest.success(column);
    }

    /**
     * 修改一条传感器配置信息
     *
     * @param sensor 传感器配置信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "sensor", allEntries = true)
    public JsonRequest<Integer> updateOneSensor(SensorConfig sensor) {
        SensorConfig sensor1 = sensorMapper.selectAllSensorById(sensor.getId());
        if (Objects.isNull(sensor1)) {
            log.warn("没有查询到Id为{}的传感器配置!", sensor.getId());
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        sensor.setUpdateUser(sqlDateUtils.getCurrentUserId());
        sensor.setUpdateTime(date);
        Integer column = sensorMapper.updateSensorById(sensor);
        if (column < 1) {
            log.error("Id为{}的传感器配置修改失败!返回值{}", sensor.getId(), column);
            return JsonRequest.error(ServiceException.UPDATE_ERROR);
        }
        log.info("Id为{}的传感器配置修改成功!返回值{}", sensor.getId(), column);
        return JsonRequest.success(column);
    }

    /**
     * 删除传感器配置信息
     *
     * @param ids 编号
     * @return JSON
     */
    @Override
    @CacheEvict(value = "sensor", allEntries = true)
    public JsonRequest<Integer> deleteSensors(Integer[] ids) {
        //判断传感器配置信息是否完整
        Integer count = sensorMapper.countById(ids);
        if (count < ids.length) {
            log.warn("待删除的传感器配置个数与数据库已存在的传感器配置个数不符!数据库实际存在{}个,需要{}个", count, ids.length);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //删除逻辑
        Integer column = sensorMapper.deleteSensorById(ids);
        if (column < 1) {
            log.error("删除传感器配置遇到错误!返回值{}", column);
            return JsonRequest.error(ServiceException.DELETE_ERROR);
        }
        log.info("已删除{}条传感器配置信息!返回值{}", count, column);
        return JsonRequest.success(column);
    }
}
