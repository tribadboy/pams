DROP TABLE IF EXISTS `t_pams_friend` ;
CREATE TABLE `t_pams_friend` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`friend_id` int(10) unsigned NOT NULL COMMENT '好友id',
`friend_name` varchar(32) NOT NULL DEFAULT '' COMMENT '好友姓名',
`friend_message` varchar(32) NOT NULL DEFAULT '' COMMENT '好友备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户好友表';
