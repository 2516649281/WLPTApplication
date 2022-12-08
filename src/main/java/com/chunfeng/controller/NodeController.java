package com.chunfeng.controller;

import com.chunfeng.entity.Node;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceController;
import com.chunfeng.service.INodeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 节点Controller
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@RestController
@RequestMapping("/node")
public class NodeController extends ServiceController {

    /**
     * 节点业务层接口
     */
    @Autowired
    private INodeValueService nodeValueService;


    /**
     * 设备传感器数据生成
     *
     * @param uid 设备ID
     * @return JSON
     */
    @GetMapping("/{uid}")
    public JsonRequest<List<Node<Float>>> getSensorValue(@PathVariable String uid) {
        return nodeValueService.getSensorValue(uid);
    }

    /**
     * 设备执行器数据生成
     *
     * @param uid   设备ID
     * @param index 传感器值
     * @return JSON
     */
    @GetMapping
    public JsonRequest<Node<Integer>> setActuatorStatus(@RequestParam("uid") Integer uid, @RequestParam("index") Integer index) {
        return nodeValueService.setActuatorStatus(uid, index);
    }
}
