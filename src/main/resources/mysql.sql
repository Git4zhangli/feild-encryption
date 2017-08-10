CREATE DATABASE IF NOT EXISTS `demo`  DEFAULT CHARACTER SET utf8 ;
USE demo;
CREATE TABLE IF NOT EXISTS `ENCRYPTION_EXAMPLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MOBILE` varchar(128) DEFAULT NULL COMMENT '手机号',
  `WECHAT` varchar(128) DEFAULT NULL COMMENT '微信号',
  `QQ` varchar(128) DEFAULT NULL COMMENT 'QQ号',
  `EMAIL` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime NOT NULL COMMENT '修改时间',
  `DESC` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加密示例表';