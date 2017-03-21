DROP TABLE IF EXISTS `t_pams_role_permission` ;
CREATE TABLE `t_pams_role_permission` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`role_id` int(10) unsigned NOT NULL COMMENT '角色id',
`permission_id` int(10) unsigned NOT NULL COMMENT '权限id',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';
