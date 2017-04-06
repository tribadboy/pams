DROP TABLE IF EXISTS `t_pams_account_month` ;
CREATE TABLE `t_pams_account_month` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`consumption_id` int(8) unsigned NOT NULL COMMENT '消费分类id',
`cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '月消费金额总和',
`spend_month` varchar(7) DEFAULT NULL COMMENT '消费月份',
`number_of_account` int(8) unsigned NOT NULL COMMENT '账目数量',
`days_of_month` int(8) unsigned NOT NULL COMMENT '该月份总天数',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='月消费账目总和表';
