DROP TABLE IF EXISTS `t_pams_notice` ;
CREATE TABLE `t_pams_notice` (
`notice_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`status` int(8) unsigned NOT NULL COMMENT '状态（0:有效，1:无效）',
`record_date` varchar(10) DEFAULT '' NOT NULL COMMENT '记录日期',
`message` varchar(64) DEFAULT '' NOT NULL COMMENT '公告内容',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告表';
