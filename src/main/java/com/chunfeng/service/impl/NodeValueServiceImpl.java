package com.chunfeng.service.impl;

import com.alibaba.fastjson.JSON;
import com.chunfeng.entity.ActuatorConfig;
import com.chunfeng.entity.Node;
import com.chunfeng.entity.SensorConfig;
import com.chunfeng.mapper.ActuatorConfigMapper;
import com.chunfeng.mapper.SensorConfigMapper;
import com.chunfeng.result.JsonRequest;
import com.chunfeng.result.ServiceException;
import com.chunfeng.service.INodeValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 设备数据生成
 *
 * @author by 春风能解释
 * <p>
 * 2022/12/7
 */
@Service
@Transactional
@Slf4j
public class NodeValueServiceImpl implements INodeValueService {

    /**
     * 传感器数据层注入
     */
    @Autowired
    private SensorConfigMapper sensorConfigMapper;

    /**
     * 执行器数据层注入
     */
    @Autowired
    private ActuatorConfigMapper actuatorConfigMapper;

    /**
     * 执行器状态
     */
    private Map<Integer, Integer> status = new HashMap<>();

    /**
     * 设备传感器数据生成
     *
     * @param uid 设备ID
     * @return JSON
     */
    @Override
    public JsonRequest<List<Node<Float>>> getSensorValue(String uid) {
        List<SensorConfig> sensorConfigs = sensorConfigMapper.selectAllSensorByEquipId(uid);
        if (sensorConfigs.isEmpty()) {
            log.warn("设备UID为{}的设备在数据库未找到!", uid);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        //包装数据并输出
        List<Node<Float>> nodes = sensorConfigs.stream().map((item) -> {
            Node<Float> node = new Node<>();
            BeanUtils.copyProperties(item, node);
            node.setValue(makeRandom(item.getMaxValue(), item.getMinValue(), 1));
            return node;
        }).collect(Collectors.toList());
        log.info("已找到{}条设备信息!", sensorConfigs.size());
        return JsonRequest.success(nodes);
    }

    /**
     * 设备执行器数据生成
     *
     * @param uid   设备ID
     * @param index 传感器值
     * @return JSON
     */
    @Override
    public JsonRequest<Node<Integer>> setActuatorStatus(Integer uid, Integer index) {
        ActuatorConfig actuatorConfig = actuatorConfigMapper.selectAllActuatorByEquipId(uid);
        if (Objects.isNull(actuatorConfig)) {
            log.warn("设备UID为{}的设备在数据库未找到!", uid);
            return JsonRequest.error(ServiceException.NOT_FOUND);
        }
        Node<Integer> node = new Node<>();
        BeanUtils.copyProperties(actuatorConfig, node);
        //如果查询
        if (status.get(uid) != null & index == 0) {
            log.info("设备查询成功!当前值:{}", status.get(uid));
            node.setValue(status.get(uid));
            return JsonRequest.success(node);
        }
        //重新生成逻辑
        String valueList = actuatorConfig.getValueList();
        Integer[] integers = JSON.parseObject(valueList, Integer[].class);
        for (Integer integer : integers) {
            if (integer.equals(index)) {
                node.setValue(index);
                //存储值以便下一次使用
                status.put(uid, integer);
                log.info("设备控制成功!当前值:{}", status);
                return JsonRequest.success(node);
            }
        }
        log.warn("请求参数错误!正确参数组合:uid={},index={},实际参数组合:uuid={},index={}", uid, status.get(uid), uid, index);
        return JsonRequest.error(ServiceException.NOT_FOUND);
    }

    /**
     * 生成指定范围的小数
     *
     * @param max   最大值
     * @param min   最小值
     * @param scale 小数位数
     * @return 小数
     */
    public Float makeRandom(float max, float min, int scale) {
        BigDecimal cha = new BigDecimal(Math.random() * (max - min) + min);
        String string = String.valueOf(cha.setScale(scale, BigDecimal.ROUND_HALF_UP));//保留 scale 位小数，并四舍五入
        log.info("已成功生成随机数:{}", string);
        return Float.valueOf(string);
    }
}