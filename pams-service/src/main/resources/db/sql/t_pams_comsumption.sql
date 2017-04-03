DROP TABLE IF EXISTS `t_pams_comsumption` ;
CREATE TABLE `t_pams_comsumption` (
`comsumption_id` int(8) unsigned NOT NULL AUTO_INCREMENT,
`comsumption_name` varchar(64) NOT NULL DEFAULT '' COMMENT '消费分类名',
PRIMARY KEY (`comsumption_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消费分类表';

INSERT INTO `t_pams_comsumption` VALUES 
(1,"饮食消费"),
(2,"服装消费"),
(3,"住房消费"),
(4,"交通消费"),
(5,"电话消费"),
(6,"日用品消费"),
(7,"书籍消费"),
(8,"旅行消费"),
(9,"生活消费(水电煤)"),
(10,"其他消费");
