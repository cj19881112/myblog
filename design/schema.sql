/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : myblog_test

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-02-28 13:26:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `app_id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '小程序ID',
  `app_name_` varchar(255) NOT NULL COMMENT '小程序的名字',
  `app_icon_url_` varchar(255) DEFAULT NULL COMMENT '小程序图标url地址',
  `app_created_at_` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `app_repo_url_` varchar(255) DEFAULT NULL COMMENT '程序代码仓库URL',
  `app_entry_file_` varchar(255) DEFAULT NULL COMMENT '程序文件路径',
  `app_entry_` varchar(255) DEFAULT NULL COMMENT '程序入口文件名称',
  PRIMARY KEY (`app_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app
-- ----------------------------

-- ----------------------------
-- Table structure for t_artical
-- ----------------------------
DROP TABLE IF EXISTS `t_artical`;
CREATE TABLE `t_artical` (
  `art_id_` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `art_title_` varchar(255) NOT NULL COMMENT '文档标题',
  `art_img_url_` varchar(255) DEFAULT NULL COMMENT '文章图片url',
  `art_brief_` varchar(255) NOT NULL COMMENT '文章简述',
  `art_content_` text NOT NULL COMMENT '文章内容',
  `art_read_cnt_` int(11) NOT NULL DEFAULT '0' COMMENT '文章有几个人阅读',
  `art_tags_` varchar(255) NOT NULL COMMENT '文章的tag，用英文逗号拼接',
  `art_created_at_` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '文章创建时间',
  `art_is_del_` char(1) NOT NULL DEFAULT '0' COMMENT '是否删除(0-否 1-是)',
  PRIMARY KEY (`art_id_`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_artical
-- ----------------------------
INSERT INTO `t_artical` VALUES ('1', 'hello', '/abc', 'hello', 'hello world', '1', 'hi', '2018-01-01 00:00:00', '1');
INSERT INTO `t_artical` VALUES ('2', 'hello', '/abc', 'hello', 'hello world', '1', 'hi', '2018-01-01 00:00:00', '0');
INSERT INTO `t_artical` VALUES ('3', 'world', '/abc', 'hello', 'hello world', '1', 'hi', '2018-01-01 00:00:01', '0');
INSERT INTO `t_artical` VALUES ('4', 'world', '/abc', 'hello', 'hello world', '1', 'nice,job', '2018-01-01 00:00:02', '0');

-- ----------------------------
-- Table structure for t_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_conf`;
CREATE TABLE `t_conf` (
  `conf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `conf_name` varchar(255) DEFAULT NULL COMMENT '配置名',
  `conf_val` varchar(255) DEFAULT NULL COMMENT '配置值',
  PRIMARY KEY (`conf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_conf
-- ----------------------------
INSERT INTO `t_conf` VALUES ('1', 'hello', 'world');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
  `tag_id_` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name_` varchar(255) NOT NULL,
  `tag_sort_` int(11) DEFAULT '0',
  PRIMARY KEY (`tag_id_`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES ('1', 'hi', '0');
INSERT INTO `t_tag` VALUES ('2', 'hello', '1');
