DROP TABLE IF EXISTS `t_pams_stock_capital` ;
CREATE TABLE `t_pams_stock_capital` (
`user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '股票资金',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户股票资金表';
