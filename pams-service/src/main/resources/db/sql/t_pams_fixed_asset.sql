DROP TABLE IF EXISTS `t_pams_fixed_asset` ;
CREATE TABLE `t_pams_fixed_asset` (
`asset_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`record_name` varchar(32) DEFAULT NULL COMMENT '资产名称',
`record_value` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '资产价值',
`record_time` varchar(10) DEFAULT NULL COMMENT '记录日期',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`asset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户固定资产表';
