DROP TABLE IF EXISTS `t_pams_role` ;
CREATE TABLE `t_pams_role` (
`role_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';
