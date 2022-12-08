package com.chunfeng.controller;

import com.chunfeng.entity.SensorConfig;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceController;
import com.chunfeng.service.ISensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 传感器配置Controller
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@RestController
@RequestMapping("/sen")
public class SensorConfigController extends ServiceController {

    /**
     * 传感器配置注入
     */
    @Autowired
    private ISensorService sensorService;

    /**
     * 查看所有传感器配置
     *
     * @return JSON
     */
    @GetMapping
    public JsonRequest<List<SensorConfig>> lookAllSensor() {
        return sensorService.lookAllSensor();
    }

    /**
     * 按名称查看所有传感器配置
     *
     * @param name 传感器配置名
     * @return JSON
     */
    @GetMapping("/{name}")
    public JsonRequest<List<SensorConfig>> lookAllSensorByName(@PathVariable String name) {
        return sensorService.lookAllSensorByName(name);
    }

    /**
     * 添加一条传感器配置信息
     *
     * @param sensor 传感器配置信息
     * @return JSON
     */
    @PostMapping
    public JsonRequest<Integer> insertOneSensor(@RequestBody SensorConfig sensor) {
        return sensorService.insertOneSensor(sensor);
    }

    /**
     * 修改一条传感器配置信息
     *
     * @param sensor 传感器配置信息
     * @return JSON
     */
    @PutMapping
    public JsonRequest<Integer> updateOneSensor(@RequestBody SensorConfig sensor) {
        return sensorService.updateOneSensor(sensor);
    }

    /**
     * 删除传感器配置信息
     *
     * @param ids 编号
     * @return JSON
     */
    @DeleteMapping
    public JsonRequest<Integer> deleteSensors(@RequestBody Integer[] ids) {
        return sensorService.deleteSensors(ids);
    }
}
