package com.chunfeng.controller;

import com.chunfeng.entity.ActuatorConfig;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceController;
import com.chunfeng.service.IActuatorConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 执行器配置Controller
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/6
 */
@RestController
@RequestMapping("/act")
public class ActuatorConfigController extends ServiceController {

    /**
     * 执行器业务层注入
     */
    @Autowired
    private IActuatorConfigService actuatorConfigService;

    /**
     * 查看所有执行器配置
     *
     * @return JSON
     */
    @GetMapping
    public JsonRequest<List<ActuatorConfig>> lookAllActuator() {
        return actuatorConfigService.lookAllActuator();
    }

    /**
     * 按名称查看所有执行器配置
     *
     * @param name 执行器配置名
     * @return JSON
     */
    @GetMapping("/{name}")
    public JsonRequest<List<ActuatorConfig>> lookAllActuatorByName(@PathVariable String name) {
        return actuatorConfigService.lookAllActuatorByName(name);
    }

    /**
     * 添加一条执行器配置信息
     *
     * @param actuator 执行器配置信息
     * @return JSON
     */
    @PostMapping
    public JsonRequest<Integer> insertOneActuator(@RequestBody ActuatorConfig actuator) {
        return actuatorConfigService.insertOneActuator(actuator);
    }

    /**
     * 修改一条执行器配置信息
     *
     * @param actuator 执行器配置信息
     * @return JSON
     */
    @PutMapping
    public JsonRequest<Integer> updateOneActuator(@RequestBody ActuatorConfig actuator) {
        return actuatorConfigService.updateOneActuator(actuator);
    }

    /**
     * 删除执行器配置信息
     *
     * @param ids 编号
     * @return JSON
     */
    @DeleteMapping
    public JsonRequest<Integer> deleteActuators(@RequestBody Integer[] ids) {
        return actuatorConfigService.deleteActuators(ids);
    }
}
