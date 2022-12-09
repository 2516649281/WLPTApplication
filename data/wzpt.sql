/*
 Navicat Premium Data Transfer

 Source Server         : MySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : wzpt

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 09/12/2022 11:31:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for actuator_config
-- ----------------------------
DROP TABLE IF EXISTS `actuator_config`;
CREATE TABLE `actuator_config`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `equip_id`    int(0)                                                       NOT NULL COMMENT '执行器ID',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名',
    `value_list`  varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数值集合',
    `create_user` int(0)                                                       NOT NULL COMMENT '创建人',
    `update_user` int(0)                                                       NULL DEFAULT NULL COMMENT '修改人',
    `create_time` datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '执行器配置'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of actuator_config
-- ----------------------------
INSERT INTO `actuator_config`
VALUES (1, 30011, '人体红外', '[1,2]', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `actuator_config`
VALUES (2, 30012, '风扇', '[1,2]', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `actuator_config`
VALUES (3, 30013, 'ID卡', '[12345678,87654321]', 1, 1, '2022-12-07 00:00:00', '2022-12-07 00:00:00');
INSERT INTO `actuator_config`
VALUES (4, 30015, '电灯', '[1,2]', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `actuator_config`
VALUES (5, 30016, '报警', '[1,2]', 1, 1, '2022-12-08 00:00:00', '2022-12-08 00:00:00');

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `uid`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编号',
    `num`         int(0)                                                       NOT NULL COMMENT '通道号',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
    `create_user` int(0)                                                       NOT NULL COMMENT '创建人',
    `update_user` int(0)                                                       NULL DEFAULT NULL COMMENT '修改人',
    `create_time` datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                  NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment`
VALUES (1, '30008', 1, '温度传感器', 1, 1, '2022-12-07 00:00:00', '2022-12-08 00:00:00');
INSERT INTO `equipment`
VALUES (2, '30008', 2, '湿度传感器', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (3, '30009', 1, '光照传感器', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (4, '30010', 1, '可燃气体传感器', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (5, '30011', 1, '人体红外', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (6, '30012', 1, '风扇', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (7, '30013', 1, 'ID卡', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (8, '30014', 1, '门禁', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (9, '30015', 1, '电灯', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (10, '30016', 1, '报警', 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `equipment`
VALUES (12, '30010', 2, '噪声传感器', 1, NULL, '2022-12-07 00:00:00', NULL);

-- ----------------------------
-- Table structure for sensor_config
-- ----------------------------
DROP TABLE IF EXISTS `sensor_config`;
CREATE TABLE `sensor_config`
(
    `id`          int(0)                                                       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `equip_id`    int(0)                                                       NOT NULL COMMENT '传感器ID',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名',
    `max_value`   float                                                        NOT NULL DEFAULT 100 COMMENT '最大值',
    `min_value`   float                                                        NOT NULL DEFAULT 0 COMMENT '最小值',
    `create_user` int(0)                                                       NOT NULL COMMENT '创建人',
    `update_user` int(0)                                                       NULL     DEFAULT NULL COMMENT '修改人',
    `create_time` datetime(0)                                                  NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                  NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '传感器配置'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sensor_config
-- ----------------------------
INSERT INTO `sensor_config`
VALUES (1, 30008, '温湿度传感器', 100, 0, 1, 1, '2022-12-07 00:00:00', '2022-12-07 00:00:00');
INSERT INTO `sensor_config`
VALUES (2, 30008, '湿度传感器', 100, 0, 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `sensor_config`
VALUES (3, 30009, '光照度传感器', 200, 0, 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `sensor_config`
VALUES (4, 30010, '可燃气体', 100, 0, 1, NULL, '2022-12-07 00:00:00', NULL);
INSERT INTO `sensor_config`
VALUES (5, 30010, '噪声', 100, 0, 1, 1, '2022-12-07 00:00:00', '2022-12-07 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int(0)                                                        NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NOT NULL COMMENT '姓名',
    `password`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `status`      int(0)                                                        NOT NULL DEFAULT 0 COMMENT '用户状态(0正常,1失效)',
    `create_user` int(0)                                                        NOT NULL DEFAULT 0 COMMENT '创建人',
    `update_user` int(0)                                                        NULL     DEFAULT NULL COMMENT '修改人',
    `create_time` datetime(0)                                                   NOT NULL COMMENT '创建时间',
    `update_time` datetime(0)                                                   NULL     DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `name` (`name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, 'root', '9B7948D1-4FC9-4C92-8DC5-9D1D8E6A23DA$89776A18141293425CF322717291EC16', 0, 0, 1,
        '2022-12-07 00:00:00', '2022-12-07 00:00:00');

SET FOREIGN_KEY_CHECKS = 1;
