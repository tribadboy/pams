DROP TABLE IF EXISTS `t_pams_login_info` ;
CREATE TABLE `t_pams_login_info` (
`info_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`ip` varchar(32) NOT NULL DEFAULT '' COMMENT 'ip地址',
`login_time` varchar(16) NOT NULL DEFAULT '' COMMENT '登录时间',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录信息表';
