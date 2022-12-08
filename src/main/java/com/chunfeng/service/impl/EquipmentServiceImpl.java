package com.chunfeng.service.impl;

import com.chunfeng.entity.Equipment;
import com.chunfeng.mapper.EquipmentMapper;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.service.IEquipmentService;
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
 * 设备业务层实现类
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@Service
@Transactional
@Slf4j
public class EquipmentServiceImpl implements IEquipmentService {

    /**
     * 获取当前时间
     */
    private final Date date = SqlDateUtils.date;
    /**
     * 设备数据层注入
     */
    @Autowired
    private EquipmentMapper equipmentMapper;
    /**
     * 注入用户Id
     */
    @Autowired
    private SqlDateUtils sqlDateUtils;

    /**
     * 查看所有设备
     *
     * @return JSON
     */
    @Override
    @Cacheable(value = "equip")
    public JsonRequest<List<Equipment>> lookAllEquipment() {
        List<Equipment> equipment = equipmentMapper.selectAllEquip();
        if (equipment.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条设备信息", equipment.size());
        return JsonRequest.success(equipment);
    }

    /**
     * 按名称查看所有设备
     *
     * @param name 设备名
     * @return JSON
     */
    @Override
    @Cacheable(value = "equip", key = "#name")
    public JsonRequest<List<Equipment>> lookAllEquipmentByName(String name) {
        List<Equipment> equipment = equipmentMapper.selectAllEquipByName(name);
        if (equipment.isEmpty()) {
            log.warn("没有找到任何数据!");
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        log.info("已查询到{}条设备信息", equipment.size());
        return JsonRequest.success(equipment);
    }

    /**
     * 添加一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "equip", allEntries = true)
    public JsonRequest<Integer> insertOneEquipment(Equipment equipment) {
        equipment.setCreateUser(sqlDateUtils.getCurrentUserId());
        equipment.setCreateTime(date);
        Integer column = equipmentMapper.insertEquip(equipment);
        if (column < 1) {
            log.error("名为{}的设备添加失败!", equipment.getName());
            return JsonRequest.error(ServiceException.INSERT_ERROR);
        }
        log.info("名为{}的设备添加成功!", equipment.getName());
        return JsonRequest.success(column);
    }

    /**
     * 修改一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    @Override
    @CacheEvict(value = "equip", allEntries = true)
    public JsonRequest<Integer> updateOneEquipment(Equipment equipment) {
        Equipment equipment1 = equipmentMapper.selectAllEquipById(equipment.getId());
        if (Objects.isNull(equipment1)) {
            log.warn("没有查询到Id为{}的设备!", equipment.getId());
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        equipment.setUpdateUser(sqlDateUtils.getCurrentUserId());
        equipment.setUpdateTime(date);
        Integer column = equipmentMapper.updateEquipById(equipment);
        if (column < 1) {
            log.error("Id为{}的设备修改失败!返回值{}", equipment.getId(), column);
            return JsonRequest.error(ServiceException.UPDATE_ERROR);
        }
        log.info("Id为{}的设备修改成功!返回值{}", equipment.getId(), column);
        return JsonRequest.success(column);
    }

    /**
     * 删除设备信息
     *
     * @param ids 编号
     * @return JSON
     */
    @Override
    @CacheEvict(value = "equip", allEntries = true)
    public JsonRequest<Integer> deleteEquipments(Integer[] ids) {
        //判断设备信息是否完整
        Integer count = equipmentMapper.countById(ids);
        if (count < ids.length) {
            log.warn("待删除的设备个数与数据库已存在的设备个数不符!数据库实际存在{}个,需要{}个", count, ids.length);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //删除逻辑
        Integer column = equipmentMapper.deleteEquipById(ids);
        if (column < 1) {
            log.error("删除设备遇到错误!返回值{}", column);
            return JsonRequest.error(ServiceException.DELETE_ERROR);
        }
        log.info("已删除{}条设备信息!返回值{}", count, column);
        return JsonRequest.success(column);
    }
}
