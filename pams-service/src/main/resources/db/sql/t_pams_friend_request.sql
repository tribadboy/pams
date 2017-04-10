DROP TABLE IF EXISTS `t_pams_friend_request` ;
CREATE TABLE `t_pams_friend_request` (
`request_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`request_user_id` int(10) unsigned NOT NULL COMMENT '申请者id',
`request_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '申请者姓名',
`response_user_id` int(10) unsigned NOT NULL COMMENT '接收者id',
`response_user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '接收者姓名',
`status` int(8) unsigned NOT NULL COMMENT '请求状态（0：请求中， 1:已拒绝）',
`message` varchar(64) DEFAULT NULL COMMENT '备注',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友申请表';
