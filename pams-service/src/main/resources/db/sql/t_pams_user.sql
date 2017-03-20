DROP TABLE IF EXISTS `t_pams_user` ;
CREATE TABLE `t_pams_user` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名',
`password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
`phone` varchar(16) NOT NULL DEFAULT '' COMMENT '电话',
`mail` varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
`status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0：有效，1:无效',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`id`),
KEY `idx_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
