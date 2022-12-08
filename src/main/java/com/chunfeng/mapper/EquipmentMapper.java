package com.chunfeng.mapper;

import com.chunfeng.entity.Equipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备数据层接口
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
public interface EquipmentMapper {

    /**
     * 查询所有设备信息
     *
     * @return 设备信息列表
     */
    List<Equipment> selectAllEquip();

    /**
     * 根据ID查询设备
     *
     * @param id 编号
     * @return 设备信息
     */
    Equipment selectAllEquipById(@Param("id") Integer id);

    /**
     * 根据名称查询设备
     *
     * @param name 名称
     * @return 设备信息列表
     */
    List<Equipment> selectAllEquipByName(@Param("name") String name);

    /**
     * 添加一条设备
     *
     * @param sensorConfig 设备信息
     * @return 影响行数
     */
    Integer insertEquip(Equipment sensorConfig);

    /**
     * 修改一条设备
     *
     * @param sensorConfig 设备信息
     * @return 影响行数
     */
    Integer updateEquipById(Equipment sensorConfig);

    /**
     * 根据ID值删除设备
     *
     * @param ids 编号
     * @return 影响行数
     */
    Integer deleteEquipById(@Param("ids") Integer[] ids);

    /**
     * 根据ID获取数据库中的设备个数
     *
     * @param ids 设备ID
     * @return 个数
     */
    Integer countById(@Param("ids") Integer[] ids);
}
