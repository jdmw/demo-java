/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : innodbdemo

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 30/09/2020 18:00:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t1
-- ----------------------------
DROP TABLE IF EXISTS `t1`;
CREATE TABLE `t1` (
  `v1` varchar(10) DEFAULT NULL,
  `v2` varchar(10) DEFAULT NULL,
  `c3` char(10) DEFAULT NULL,
  `v4` varchar(10) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t1
-- ----------------------------
BEGIN;
INSERT INTO `t1` VALUES ('row1-v1', 'row1-v2', 'row1-c3', 'row1-v4', 1);
INSERT INTO `t1` VALUES ('row2-v1', 'row2-v2', 'row2-c3', 'row2-v4', 2);
INSERT INTO `t1` VALUES ('row3-v1', 'row3-v2', 'row3-c3', 'row3-v4', 3);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
