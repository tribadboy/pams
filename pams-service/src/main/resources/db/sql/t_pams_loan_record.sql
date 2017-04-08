DROP TABLE IF EXISTS `t_pams_loan_record` ;
CREATE TABLE `t_pams_loan_record` (
`loan_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`status` int(8) unsigned NOT NULL COMMENT '贷款状态（0：有效， 1:无效）',
`direction` int(8) unsigned NOT NULL COMMENT '贷款方向（0：贷入， 1:贷出）',
`except_repay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '预期还款数额',
`except_keep_time` int(8) unsigned NOT NULL COMMENT '预期还款时长',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`loan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户贷款记录表';
