package com.chunfeng.controller;

import com.chunfeng.entity.Equipment;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceController;
import com.chunfeng.service.IEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备Controller
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@RestController
@RequestMapping("/equ")
public class EquipmentController extends ServiceController {

    /**
     * 设备业务层注入
     */
    @Autowired
    private IEquipmentService equipmentService;

    /**
     * 查看所有设备
     *
     * @return JSON
     */
    @GetMapping
    public JsonRequest<List<Equipment>> lookAllEquipment() {
        return equipmentService.lookAllEquipment();
    }

    /**
     * 按名称查看所有设备
     *
     * @param name 设备名
     * @return JSON
     */
    @GetMapping("/{name}")
    public JsonRequest<List<Equipment>> lookAllEquipmentByName(@PathVariable String name) {
        return equipmentService.lookAllEquipmentByName(name);
    }

    /**
     * 添加一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    @PostMapping
    public JsonRequest<Integer> insertOneEquipment(@RequestBody Equipment equipment) {
        return equipmentService.insertOneEquipment(equipment);
    }

    /**
     * 修改一条设备信息
     *
     * @param equipment 设备信息
     * @return JSON
     */
    @PutMapping
    public JsonRequest<Integer> updateOneEquipment(@RequestBody Equipment equipment) {
        return equipmentService.updateOneEquipment(equipment);
    }

    /**
     * 删除设备信息
     *
     * @param ids 编号
     * @return JSON
     */
    @DeleteMapping
    public JsonRequest<Integer> deleteEquipments(@RequestBody Integer[] ids) {
        return equipmentService.deleteEquipments(ids);
    }
}
