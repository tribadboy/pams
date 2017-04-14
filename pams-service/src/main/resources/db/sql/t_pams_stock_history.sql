DROP TABLE IF EXISTS `t_pams_stock_history` ;
CREATE TABLE `t_pams_stock_history` (
`symbol_date` varchar(10) NOT NULL COMMENT '股票历史日期',
`symbol_code` varchar(32) NOT NULL COMMENT '股票代码',
`symbol_type` int(8) unsigned NOT NULL COMMENT '股票类型（0：沪市， 1:深市）',
`open` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '开盘股价',
`close` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '收盘股价',
`high` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '当天最高股价',
`low` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '当天最低股价',
`rise_percent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '涨幅百分比',
`volume` int(12) unsigned NOT NULL COMMENT '成交股数',
PRIMARY KEY (`symbol_date`,`symbol_code`,`symbol_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票历史记录表';
