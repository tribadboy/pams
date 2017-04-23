DROP TABLE IF EXISTS `t_pams_financial_news` ;
CREATE TABLE `t_pams_financial_news` (
`news_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
`title` varchar(32) DEFAULT '' NOT NULL COMMENT '新闻标题',
`origin` varchar(32) DEFAULT '' NOT NULL COMMENT '新闻来源',
`record_date` varchar(10) DEFAULT '' NOT NULL COMMENT '记录日期',
`picture_name` varchar(64) DEFAULT '' NOT NULL COMMENT '附图名称',
`content` varchar(8192) DEFAULT '' NOT NULL COMMENT '新闻内容',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次修改时间',
PRIMARY KEY (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻内容表';
