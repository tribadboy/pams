DROP TABLE IF EXISTS `t_pams_feedback` ;
CREATE TABLE `t_pams_feedback` (
`back_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
`record_title` varchar(32) NOT NULL DEFAULT '' COMMENT '反馈标题',
`record_date` varchar(10) DEFAULT NULL COMMENT '反馈日期',
`feed_type_str` varchar(32) DEFAULT NULL DEFAULT '' COMMENT '反馈原因',
`status` int(8) unsigned NOT NULL COMMENT '处理状态（0：未查看， 1:已查看）',
`message` varchar(256) DEFAULT NULL COMMENT '反馈内容',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`back_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈记录表';
