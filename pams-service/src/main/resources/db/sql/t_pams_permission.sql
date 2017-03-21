DROP TABLE IF EXISTS `t_pams_permission` ;
CREATE TABLE `t_pams_permission` (
`permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`permission_name` varchar(32) NOT NULL DEFAULT '' COMMENT '权限名',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';
