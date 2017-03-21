DROP TABLE IF EXISTS `t_pams_user_role` ;
CREATE TABLE `t_pams_user_role` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`role_id` int(10) unsigned NOT NULL COMMENT '角色id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';
