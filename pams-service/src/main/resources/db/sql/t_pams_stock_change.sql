DROP TABLE IF EXISTS `t_pams_stock_change` ;
CREATE TABLE `t_pams_stock_change` (
`change_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`change_type_id` int(8) unsigned NOT NULL COMMENT '类型（0：买入，1:卖出，2:转入，3:转出）',
`symbol_code` varchar(32) NOT NULL COMMENT '股票代码',
`symbol_type` int(8) unsigned NOT NULL COMMENT '股票类型（0：沪市， 1:深市）',
`price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '每股价格',
`quantity` int(12) unsigned NOT NULL COMMENT '买入或卖出股票数量',
`fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '手续费',
`total` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '总价',
`trade_time` varchar(20) DEFAULT NULL COMMENT '交易或转入转出时间',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`change_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户股票变更记录表';
