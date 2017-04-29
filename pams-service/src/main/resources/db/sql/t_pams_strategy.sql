DROP TABLE IF EXISTS `t_pams_strategy` ;
CREATE TABLE `t_pams_strategy` (
`strategy_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`strategy_name` varchar(32) NOT NULL DEFAULT '' COMMENT '策略名',
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
`status` int(8) unsigned NOT NULL COMMENT '策略的状态',
`strategy_type` int(8) unsigned NOT NULL COMMENT '策略类型',
`start_date` varchar(10) DEFAULT NULL COMMENT '策略开始日期',
`end_date1` varchar(10) DEFAULT NULL COMMENT '策略结束日期1',
`end_date2` varchar(10) DEFAULT NULL COMMENT '策略结束日期2',
`avg_profit` decimal(10,2) DEFAULT NULL COMMENT '平均收益率',
`message` varchar(64) DEFAULT '' NOT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户股票投资策略表';
