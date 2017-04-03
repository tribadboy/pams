DROP TABLE IF EXISTS `t_pams_account` ;
CREATE TABLE `t_pams_account` (
`account_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`consumption_id` int(8) unsigned NOT NULL COMMENT '消费分类id',
`cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '消费金额',
`spend_time` varchar(10) DEFAULT NULL COMMENT '消费日期',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费账目表';
