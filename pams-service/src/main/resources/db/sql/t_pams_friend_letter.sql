DROP TABLE IF EXISTS `t_pams_friend_letter` ;
CREATE TABLE `t_pams_friend_letter` (
`letter_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`send_user_id` int(10) unsigned NOT NULL COMMENT '发送者id',
`send_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '发送者姓名',
`receive_user_id` int(10) unsigned NOT NULL COMMENT '接收者id',
`receive_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '接收者姓名',
`status` int(8) unsigned NOT NULL COMMENT '私信状态（0：未读， 1:已读）',
`content` varchar(256) DEFAULT NULL COMMENT '私信内容',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`letter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友私信表';
