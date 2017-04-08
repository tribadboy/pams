DROP TABLE IF EXISTS `t_pams_regular_income` ;
CREATE TABLE `t_pams_regular_income` (
`income_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`record_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '收入数额',
`record_time` varchar(10) DEFAULT NULL COMMENT '记录日期',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`income_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收入记录表';
