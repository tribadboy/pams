DROP TABLE IF EXISTS `t_pams_admin_user` ;
CREATE TABLE `t_pams_admin_user` (
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员用户表';
