/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lyytest

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-07-07 21:12:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) DEFAULT NULL,
  `teacher_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', '经济学', 'lyy', '123456');
INSERT INTO `course` VALUES ('2', '管理学', 'lyy', '管理的课程');
INSERT INTO `course` VALUES ('3', '信息管理', 'lyy', '信管基础课');
INSERT INTO `course` VALUES ('4', 'C语言', 'zzz', '计算学院基础课');

-- ----------------------------
-- Table structure for homework_teacher
-- ----------------------------
DROP TABLE IF EXISTS `homework_teacher`;
CREATE TABLE `homework_teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of homework_teacher
-- ----------------------------
INSERT INTO `homework_teacher` VALUES ('4', '经济学作业1', '86915831-ff00-4a95-9b3b-c2674bf4b7e1test.txt', 'F:\\lyy\\homework_teacher\\86915831-ff00-4a95-9b3b-c2674bf4b7e1test.txt', '1', '2020-07-01 12:00:00', '2020-07-03 12:00:00');
INSERT INTO `homework_teacher` VALUES ('5', '经济学作业2', 'c7c3360e-1f0e-4f5b-807b-7a59a5199173test.txt', 'F:\\lyy\\homework_teacher\\c7c3360e-1f0e-4f5b-807b-7a59a5199173test.txt', '1', '2020-07-05 12:00:00', '2020-07-12 12:00:00');
INSERT INTO `homework_teacher` VALUES ('6', '管理学作业1', 'e30a224c-9bb7-4ab0-9d63-adbf30f6d540test.txt', 'F:\\lyy\\homework_teacher\\e30a224c-9bb7-4ab0-9d63-adbf30f6d540test.txt', '2', '2020-07-04 12:00:00', '2020-07-11 12:00:00');
INSERT INTO `homework_teacher` VALUES ('7', 'zzz', '43193b4b-b441-44f6-9423-645f6ac9c5cbtest.txt', 'F:\\lyy\\homework_teacher\\43193b4b-b441-44f6-9423-645f6ac9c5cbtest.txt', '1', '2020-07-03 12:00:00', '2020-07-10 12:00:00');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', 'qq', 'd738ef3052fb1b3e13e42e75fb0d4fce', 'ed449743c15edf42');
INSERT INTO `student` VALUES ('2', 'lisa', '96db54429dfee317567064a744947731', '091e99ad7fff8368');
INSERT INTO `student` VALUES ('3', 'kk', 'e7ecb6396866cc938c83397af8574305', '9e54d16659db6cc4');
INSERT INTO `student` VALUES ('4', 'yy', 'b9fc8d2a8c5324e7b44d1a5c162f69ff', '2beabb71f79ac14e');

-- ----------------------------
-- Table structure for stu_course
-- ----------------------------
DROP TABLE IF EXISTS `stu_course`;
CREATE TABLE `stu_course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint DEFAULT NULL,
  `course_id` bigint DEFAULT NULL,
  `stauts` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of stu_course
-- ----------------------------
INSERT INTO `stu_course` VALUES ('1', '1', '1', '已通过');
INSERT INTO `stu_course` VALUES ('2', '1', '2', '已通过');
INSERT INTO `stu_course` VALUES ('3', '1', '4', '已通过');
INSERT INTO `stu_course` VALUES ('5', '2', '2', '申请中');
INSERT INTO `stu_course` VALUES ('6', '3', '2', '已通过');
INSERT INTO `stu_course` VALUES ('7', '4', '2', '已通过');
INSERT INTO `stu_course` VALUES ('8', '4', '1', '已通过');
INSERT INTO `stu_course` VALUES ('9', '4', '3', '已通过');
INSERT INTO `stu_course` VALUES ('10', '4', '4', '申请中');
INSERT INTO `stu_course` VALUES ('11', '1', '3', '已拒绝');

-- ----------------------------
-- Table structure for submit_student
-- ----------------------------
DROP TABLE IF EXISTS `submit_student`;
CREATE TABLE `submit_student` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `homework_id` bigint DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `grade` double(50,0) DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of submit_student
-- ----------------------------
INSERT INTO `submit_student` VALUES ('1', '6', 'qq', '9b4e9cdf-774d-411e-9cee-1cfde85e16c1qqq.txt', 'F:\\lyy\\homework_student\\9b4e9cdf-774d-411e-9cee-1cfde85e16c1qqq.txt', '已评分', '98', '2020-07-04 23:10:27');
INSERT INTO `submit_student` VALUES ('2', '7', 'qq', 'c94637af-018b-45e4-aff2-7a9002f80acatest.txt', 'F:\\lyy\\homework_student\\c94637af-018b-45e4-aff2-7a9002f80acatest.txt', '已评分', '60', '2020-07-06 11:07:30');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'lyy', '9f744eaf235d37444133ea2080982f5a', 'cfe617c4791bbad9');
INSERT INTO `teacher` VALUES ('2', 'zzz', '7ab63ac3361371007b859cda2bde2343', '5b3c10b11b957148');
INSERT INTO `teacher` VALUES ('3', 'xsk', '2fc9fbe3473ec95393a09a8adb435533', 'dd6058289c3d07fb');
INSERT INTO `teacher` VALUES ('4', 'qqq', '5be4c67fa00519bc95b269e28b35c76e', '499b1474b97e6595');
INSERT INTO `teacher` VALUES ('7', 'aaa', '96802ff1f2b4af33fd3cdbefe3fe01be', 'f5e149fa85046eba');
