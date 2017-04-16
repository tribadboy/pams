DROP TABLE IF EXISTS `t_pams_stock_hold` ;
CREATE TABLE `t_pams_stock_hold` (
`hold_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`symbol_code` varchar(32) NOT NULL COMMENT '股票代码',
`symbol_type` int(8) unsigned NOT NULL COMMENT '股票类型（0：沪市， 1:深市）',
`quantity` int(12) unsigned NOT NULL COMMENT '持有股票数量',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`hold_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户持有股票数量记录表';
