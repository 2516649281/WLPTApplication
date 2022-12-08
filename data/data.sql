CREATE DATABASE wzpt;
USE wzpt;
CREATE TABLE `user`
(
    id          INT PRIMARY KEY auto_increment COMMENT '主键',
    name        VARCHAR(32) UNIQUE NOT NULL COMMENT '姓名',
    password    VARCHAR(256)       NOT NULL COMMENT '密码',
    status      INT DEFAULT 0      NOT NULL COMMENT '用户状态(0正常,1失效)',
    create_user INT DEFAULT 0      NOT NULL COMMENT '创建人',
    update_user INT COMMENT '修改人',
    create_time datetime           NOT NULL COMMENT '创建时间',
    update_time datetime COMMENT '修改时间'
) COMMENT '用户表';
CREATE TABLE `equipment`
(
    id          INT PRIMARY KEY auto_increment COMMENT '主键',
    uid         VARCHAR(32) NOT NULL COMMENT '设备编号',
    num         INT         NOT NULL COMMENT '通道号',
    name        VARCHAR(32) NOT NULL COMMENT '设备名称',
    create_user INT         NOT NULL COMMENT '创建人',
    update_user INT COMMENT '修改人',
    create_time datetime    NOT NULL COMMENT '创建时间',
    update_time datetime COMMENT '修改时间'
) COMMENT '设备表';
CREATE TABLE `sensor_config`
(
    id          INT PRIMARY KEY auto_increment COMMENT '主键',
    equip_id    INT               NOT NULL COMMENT '传感器ID',
    name        varchar(32)       not null comment '配置名',
    max_value   FLOAT DEFAULT 100 NOT NULL COMMENT '最大值',
    min_value   FLOAT DEFAULT 0   NOT NULL COMMENT '最小值',
    create_user INT               NOT NULL COMMENT '创建人',
    update_user INT COMMENT '修改人',
    create_time datetime          NOT NULL COMMENT '创建时间',
    update_time datetime COMMENT '修改时间'
) COMMENT '传感器配置';
CREATE TABLE actuator_config
(
    id          INT PRIMARY KEY auto_increment COMMENT '主键',
    equip_id    INT         NOT NULL COMMENT '执行器ID',
    name        varchar(32) not null comment '配置名',
    value_list  VARCHAR(64) NOT NULL COMMENT '数值集合',
    create_user INT         NOT NULL COMMENT '创建人',
    update_user INT COMMENT '修改人',
    create_time datetime    NOT NULL COMMENT '创建时间',
    update_time datetime COMMENT '修改时间'
) COMMENT '执行器配置';