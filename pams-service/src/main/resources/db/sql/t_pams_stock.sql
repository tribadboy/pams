DROP TABLE IF EXISTS `t_pams_stock` ;
CREATE TABLE `t_pams_stock` (
`symbol_code` varchar(32) NOT NULL DEFAULT '' COMMENT '股票代码',
`symbol_type` int(8) unsigned NOT NULL COMMENT '股票类型（0：沪市， 1:深市）',
`symbol_name` varchar(32) NOT NULL DEFAULT '' COMMENT '股票名称',
`status` int(8) unsigned NOT NULL COMMENT '股票状态（0：有效， 1:无效）',
PRIMARY KEY (`symbol_code`,`symbol_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='股票名称表';
