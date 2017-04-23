DROP TABLE IF EXISTS `t_pams_user_photo` ;
CREATE TABLE `t_pams_user_photo` (
`user_id` int(10) unsigned NOT NULL COMMENT '用户id',
`photo_name` varchar(64) NOT NULL DEFAULT '' COMMENT '图片名称',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户图片表';
