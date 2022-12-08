package com.chunfeng.service.impl;

import com.chunfeng.entity.ActuatorConfig;
import com.chunfeng.mapper.ActuatorConfigMapper;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.service.IActuatorConfigService;
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
 * 执行器配置业务层实现类
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Service
@Transactional
@Slf4j
public class ActuatorConfigServiceImpl implements IActuatorConfigService {

    /**
     * 获取当前时间
     */
    private final Date date = SqlDateUtils.date;
    /**
     * 执行器配置数据层注入
     */
    @Autowired
    private ActuatorConfigMapper actuatorMapper;
    /**
     * 注入用户Id
     */
    @Autowired
    private SqlDateUtils sqlDateUtils;

    /**
     * 查看所有执行器配置
     *
     * @return JSON
     */
    @Override
    @Cacheable(value = "actuator")
    public JsonRequest<List<ActuatorConfig>> lookAllActuator() {
        List<ActuatorConfig> actuator = actuatorMapper.selectAllActuator();
        if (actuator.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条执行器配置信息", actuator.size());
        return JsonRequest.success(actuator);
    }

    /**
     * 按名称查看所有执行器配置
     *
     * @param name 执行器配置名
     * @return JSON
     */
    @Override
    @Cacheable(value = "actuator", key = "#name")
    public JsonRequest<List<ActuatorConfig>> lookAllActuatorByName(String name) {
        List<ActuatorConfig> actuator = actuatorMapper.selectAllActuatorByName(name);
        if (actuator.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条执行器配置信息", actuator.size());
        return JsonRequest.success(actuator);
    }

    /**
     * 添加一条执行器配置信息
     *
     * @param actuator 执行器配置信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "actuator", allEntries = true)
    public JsonRequest<Integer> insertOneActuator(ActuatorConfig actuator) {
        ActuatorConfig actuatorConfig = actuatorMapper.selectAllActuatorByEquipId(actuator.getEquipId());
        if (Objects.nonNull(actuatorConfig)) {
            log.error("名为{}的执行器配置添加失败!", actuator.getName());
            return JsonRequest.error(ServiceException.INSERT_ERROR);
        }
        actuator.setCreateUser(sqlDateUtils.getCurrentUserId());
        actuator.setCreateTime(date);
        Integer column = actuatorMapper.insertActuator(actuator);
        if (column < 1) {
            log.error("名为{}的执行器配置添加失败!", actuator.getName());
            return JsonRequest.error(ServiceException.INSERT_ERROR);
        }
        log.info("名为{}的执行器配置添加成功!", actuator.getName());
        return JsonRequest.success(column);
    }

    /**
     * 修改一条执行器配置信息
     *
     * @param actuator 执行器配置信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "actuator", allEntries = true)
    public JsonRequest<Integer> updateOneActuator(ActuatorConfig actuator) {
        ActuatorConfig actuator1 = actuatorMapper.selectAllActuatorById(actuator.getId());
        if (Objects.isNull(actuator1)) {
            log.warn("没有查询到Id为{}的执行器配置!", actuator.getId());
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        actuator.setUpdateUser(sqlDateUtils.getCurrentUserId());
        actuator.setUpdateTime(date);
        Integer column = actuatorMapper.updateActuatorById(actuator);
        if (column < 1) {
            log.error("Id为{}的执行器配置修改失败!返回值{}", actuator.getId(), column);
            return JsonRequest.error(ServiceException.UPDATE_ERROR);
        }
        log.info("Id为{}的执行器配置修改成功!返回值{}", actuator.getId(), column);
        return JsonRequest.success(column);
    }

    /**
     * 删除执行器配置信息
     *
     * @param ids 编号
     * @return JSON
     */
    @Override
    @CacheEvict(value = "actuator", allEntries = true)
    public JsonRequest<Integer> deleteActuators(Integer[] ids) {
        //判断执行器配置信息是否完整
        Integer count = actuatorMapper.countById(ids);
        if (count < ids.length) {
            log.warn("待删除的执行器配置个数与数据库已存在的执行器配置个数不符!数据库实际存在{}个,需要{}个", count, ids.length);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //删除逻辑
        Integer column = actuatorMapper.deleteActuatorById(ids);
        if (column < 1) {
            log.error("删除执行器配置遇到错误!返回值{}", column);
            return JsonRequest.error(ServiceException.DELETE_ERROR);
        }
        log.info("已删除{}条执行器配置信息!返回值{}", count, column);
        return JsonRequest.success(column);
    }
}
