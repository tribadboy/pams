DROP TABLE IF EXISTS `t_pams_strategy_element` ;
CREATE TABLE `t_pams_strategy_element` (
`element_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`strategy_id` int(10) unsigned NOT NULL COMMENT '策略id',
`symbol_code` varchar(32) NOT NULL COMMENT '股票代码',
`symbol_type` int(8) unsigned NOT NULL COMMENT '股票类型（0：沪市， 1:深市）',
`percent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资占比（%）',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='策略股票信息表';
