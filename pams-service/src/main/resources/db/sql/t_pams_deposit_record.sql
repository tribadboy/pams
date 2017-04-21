DROP TABLE IF EXISTS `t_pams_deposit_record` ;
CREATE TABLE `t_pams_deposit_record` (
`deposit_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`deposit_name` varchar(32) DEFAULT NULL COMMENT '存款名称',
`status` int(8) unsigned NOT NULL COMMENT '存款状态（0：有效， 1:无效）',
`deposit_time_id` int(8) unsigned NOT NULL COMMENT '存储期限',
`current_profit_percent` float unsigned NOT NULL COMMENT '活期利率',
`fixed_profit_percent` float unsigned NOT NULL COMMENT '定期利率',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`deposit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户存储账户表';
