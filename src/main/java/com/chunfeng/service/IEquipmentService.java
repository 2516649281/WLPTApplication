package com.chunfeng.service;

import com.chunfeng.entity.Equipment;
import com.chunfeng.result.JsonRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备业务层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
public interface IEquipmentService {

    /**
     * 查看所有设备
     *
     * @return JSON
     */
    JsonRequest<List<Equipment>> lookAllEquipment();

    /**
     * 按名称查看所有设备
     *
     * @param name 设备名
     * @return JSON
     */
    JsonRequest<List<Equipment>> lookAllEquipmentByName(@Param("name") String name);

    /**
     * 添加一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    JsonRequest<Integer> insertOneEquipment(Equipment equipment);

    /**
     * 修改一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    JsonRequest<Integer> updateOneEquipment(Equipment equipment);

    /**
     * 删除设备信息
     *
     * @param ids 编号
     * @return JSON
     */
    JsonRequest<Integer> deleteEquipments(@Param("ids") Integer[] ids);
}
