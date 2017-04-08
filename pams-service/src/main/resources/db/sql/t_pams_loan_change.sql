DROP TABLE IF EXISTS `t_pams_loan_change` ;
CREATE TABLE `t_pams_loan_change` (
`change_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`loan_id` int(10) unsigned NOT NULL COMMENT '贷款账户id',
`change_type_id` int(8) unsigned NOT NULL COMMENT '贷款变更类型',
`change_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '还款数额',
`change_time` varchar(10) DEFAULT NULL COMMENT '还款日期',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`change_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户贷款账户变更表';
