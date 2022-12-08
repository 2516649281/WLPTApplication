package com.chunfeng.service;

import com.chunfeng.entity.Node;
import com.chunfeng.result.JsonRequest;

import java.util.List;

/**
 * 设备数据生成
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
public interface INodeValueService {

    /**
     * 设备传感器数据生成
     *
     * @param uid 设备ID
     * @return JSON
     */
    JsonRequest<List<Node<Float>>> getSensorValue(String uid);


    /**
     * 设备执行器数据生成
     *
     * @param uid   设备ID
     * @param index 传感器值
     * @return JSON
     */
    JsonRequest<Node<Integer>> setActuatorStatus(Integer uid, Integer index);
}
