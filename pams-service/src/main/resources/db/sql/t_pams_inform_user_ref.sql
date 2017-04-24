DROP TABLE IF EXISTS `t_pams_inform_user_ref` ;
CREATE TABLE `t_pams_inform_user_ref` (
`ref_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`inform_id` int(10) unsigned NOT NULL COMMENT '通知id',
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
PRIMARY KEY (`ref_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知用户关联表';
