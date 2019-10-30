/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50723
Source Host           : test.jxnewlife.com:13306
Source Database       : newlife_base

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2019-10-30 11:49:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member_auths
-- ----------------------------
DROP TABLE IF EXISTS `member_auths`;
CREATE TABLE `member_auths` (
  `Auths_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Auths_ID',
  `Member_ID` bigint(20) DEFAULT NULL COMMENT '平台会员ID',
  `Identity_Type` varchar(50) DEFAULT NULL COMMENT '第三方应用名称（微信 微博等）',
  `Identifier` varchar(50) DEFAULT NULL COMMENT '第三方应用的会员唯一标识，openid',
  `Credential` varchar(600) DEFAULT NULL COMMENT '第三方应用凭证，保存token',
  `UnionID` varchar(50) DEFAULT NULL COMMENT 'UnionID',
  `Subscribe` tinyint(4) DEFAULT '0' COMMENT '0:未订阅 1：订阅',
  PRIMARY KEY (`Auths_ID`),
  KEY `FK_Reference_87` (`Member_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='Member_Auths（平台会员第三方登录）';

-- ----------------------------
-- Records of member_auths
-- ----------------------------

-- ----------------------------
-- Table structure for member_card_rules
-- ----------------------------
DROP TABLE IF EXISTS `member_card_rules`;
CREATE TABLE `member_card_rules` (
  `Rules_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `Rules_Category_ID` tinyint(4) NOT NULL COMMENT '规则分类数组字典中获取:1代理人推广券;2:活动推广券',
  `Card_Type` tinyint(4) NOT NULL COMMENT '卡券类型，1：通用购车券2：专属抵扣券3：其它',
  `Rules_Name` varchar(50) NOT NULL COMMENT '规则名称',
  `Card_Desc` varchar(300) NOT NULL COMMENT '卡券描述',
  `Least_Cost` decimal(10,0) NOT NULL COMMENT '表示起用金额（单位为分）,如果无起用门槛则填0',
  `Reduce_Cost` decimal(10,0) NOT NULL COMMENT '表示减免金额。（单位为分）',
  `Expiry_Time` tinyint(4) NOT NULL COMMENT '有效期限（天），按领取时开始计算',
  `Status` tinyint(4) NOT NULL COMMENT '状态，0:不可用；1：可用',
  `Create_Time` datetime NOT NULL COMMENT '创建时间',
  `Create_User` bigint(20) NOT NULL COMMENT '创建人ID',
  `Modify_Time` datetime NOT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) NOT NULL COMMENT '修改人ID',
  `User_Type` tinyint(4) DEFAULT '0' COMMENT '使用方式：0：一次1张；1可叠加使用',
  `Max_Total_Count` tinyint(4) DEFAULT NULL COMMENT '可生成券总数',
  `Created_Count` tinyint(4) DEFAULT NULL COMMENT '已生成券数量,不能大于总数',
  PRIMARY KEY (`Rules_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of member_card_rules
-- ----------------------------

-- ----------------------------
-- Table structure for member_card_voucher
-- ----------------------------
DROP TABLE IF EXISTS `member_card_voucher`;
CREATE TABLE `member_card_voucher` (
  `Card_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `Member_ID` bigint(20) NOT NULL COMMENT '平台用户ID',
  `Form_Member_ID` bigint(20) NOT NULL COMMENT '来源会员ID',
  `Rules_ID` bigint(20) NOT NULL COMMENT '所属规则ID',
  `Card_No` varchar(32) NOT NULL COMMENT '卡券编号',
  `Card_Type` tinyint(4) NOT NULL COMMENT '卡券类型，1：通用购车券2：专属抵扣券3：其它',
  `Card_Title` varchar(50) NOT NULL COMMENT '卡券名称',
  `Card_Desc` varchar(300) NOT NULL COMMENT '卡券描述',
  `Least_Cost` decimal(10,0) NOT NULL COMMENT '表示起用金额（单位为分）,如果无起用门槛则填0',
  `Reduce_Cost` decimal(10,0) NOT NULL COMMENT '表示减免金额。（单位为分）',
  `Begin_Time` datetime NOT NULL COMMENT '领取后开始计算,生效时间',
  `End_Time` datetime NOT NULL COMMENT '领取后开始计算,失效时间',
  `Card_Status` tinyint(4) NOT NULL COMMENT '卡片使用状态：0：未使用,1:已使用,2已失效',
  `Create_Time` datetime NOT NULL COMMENT '操作时间',
  `Status` tinyint(255) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `is_readed` tinyint(4) DEFAULT '0' COMMENT '是否已查看:0未读;1:已读',
  `Org_Type` bigint(20) DEFAULT NULL COMMENT '券归属组织类型:0平台，1:店铺组织',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '券归属组织ID',
  `User_Type` tinyint(4) DEFAULT '0' COMMENT '使用方式：0：一次1张；1储存卡；2可叠加',
  `Remain_Cost` decimal(10,0) DEFAULT NULL COMMENT '剩余可用金额:只对使用方式为储存卡类型有效',
  PRIMARY KEY (`Card_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of member_card_voucher
-- ----------------------------

-- ----------------------------
-- Table structure for member_favorite_station
-- ----------------------------
DROP TABLE IF EXISTS `member_favorite_station`;
CREATE TABLE `member_favorite_station` (
  `member_favorite_station_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收藏站点id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员id',
  `charging_station_id` bigint(20) DEFAULT NULL COMMENT '站点id',
  `favorite_status` tinyint(4) DEFAULT NULL COMMENT '0:没有收藏 1:收藏',
  `description` varchar(180) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:正常 1:删除',
  PRIMARY KEY (`member_favorite_station_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='收藏站点';

-- ----------------------------
-- Records of member_favorite_station
-- ----------------------------

-- ----------------------------
-- Table structure for member_feedback
-- ----------------------------
DROP TABLE IF EXISTS `member_feedback`;
CREATE TABLE `member_feedback` (
  `member_feedback_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '会员Id',
  `feedback_content` varchar(500) DEFAULT NULL COMMENT '反馈内容',
  `description` varchar(180) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:正常 1:删除',
  PRIMARY KEY (`member_feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈';

-- ----------------------------
-- Records of member_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for member_member
-- ----------------------------
DROP TABLE IF EXISTS `member_member`;
CREATE TABLE `member_member` (
  `Member_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '平台会员ID',
  `Login_ID` varchar(50) DEFAULT NULL COMMENT '登录账号',
  `Mobile` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `Email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `Password` varchar(50) DEFAULT NULL COMMENT '密码',
  `Member_Name` varchar(100) DEFAULT NULL COMMENT '会员名称',
  `NickName` varchar(50) DEFAULT NULL COMMENT '昵称',
  `Avatar` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `Sex` int(11) DEFAULT NULL COMMENT '性别',
  `Birthday` datetime DEFAULT NULL COMMENT '生日',
  `Telphone` varchar(50) DEFAULT NULL COMMENT '固定电话',
  `Member_Status` int(11) DEFAULT '0' COMMENT '会员状态：0-正常，1-禁用',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Member_Source` tinyint(4) DEFAULT NULL COMMENT '平台会员来源',
  `Introducer` bigint(20) DEFAULT NULL COMMENT '介绍人(平台会员)',
  `PID` varchar(70) DEFAULT NULL COMMENT '新增字段，代理推广码，用于发展代理人和结算佣金依据。',
  `Sale_Qr_Code` varchar(256) DEFAULT NULL COMMENT '推广二维码图片地址',
  `Is_First` tinyint(4) DEFAULT '0' COMMENT '新增字段，是否首次访问,只有首次访问才能建立代理关系，1：是；0：否',
  `Agent_Status` tinyint(4) DEFAULT '0' COMMENT '新增字段，代理人状态，0：不是代理人；1：试用期代理人；2：正式代理人',
  `Agent_Time` datetime DEFAULT NULL COMMENT '新增字段，申请成为代理人的时间',
  `Account_Money` decimal(11,2) DEFAULT '0.00' COMMENT '新增字段，帐号余额',
  `Lock_Money` decimal(11,2) DEFAULT '0.00' COMMENT '新增字段，冻结余额',
  `Agent_Money` decimal(11,2) DEFAULT '0.00' COMMENT '新增字段，实时代理佣金',
  `Total_Agent_Money` decimal(11,2) DEFAULT '0.00' COMMENT '新增字段，累计代理佣金',
  `Lock_Agent_Money` decimal(11,2) DEFAULT '0.00' COMMENT '新增字段，冻结佣金金额',
  `Member_Source2` tinyint(4) DEFAULT NULL COMMENT '平台会员来源人物：\r\n             1APP[到店登记]销售二维码；2APP[我的]销售二维码；6售后顾问二维码；\r\n            7平台会员(代理人)二维码；\r\n           8.租车小程序 ',
  `Introducer2` bigint(20) DEFAULT NULL COMMENT '来源对象为人物ID',
  `blocking` tinyint(4) DEFAULT '0' COMMENT '冻结状态 0-不冻结 1-冻结',
  `member_card_code` varchar(13) DEFAULT '00000' COMMENT '用户卡号',
  PRIMARY KEY (`Member_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='Member_Member（平台会员）';

-- ----------------------------
-- Records of member_member
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dictdata
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictdata`;
CREATE TABLE `sys_dictdata` (
  `DictDataID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典表数据ID',
  `DictTypeCode` varchar(50) NOT NULL COMMENT '字典表类型编码',
  `DictDataCode` varchar(50) NOT NULL COMMENT '字典表数据编码',
  `DictDataText` varchar(500) NOT NULL COMMENT '字典表数据文本',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  PRIMARY KEY (`DictDataID`)
) ENGINE=InnoDB AUTO_INCREMENT=2375 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dictdata
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dicttype
-- ----------------------------
DROP TABLE IF EXISTS `sys_dicttype`;
CREATE TABLE `sys_dicttype` (
  `DictTypeID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典表类型ID',
  `DictTypeCode` varchar(50) NOT NULL COMMENT '字典表类型编码',
  `Parent_DictTypeID` bigint(20) DEFAULT NULL,
  `DictTypeText` varchar(50) NOT NULL COMMENT '字典表类型文本',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  `DictTypeID_Tree_Path` varchar(2000) DEFAULT NULL COMMENT '树形ID',
  `DictType_Level` int(11) DEFAULT NULL COMMENT '层级',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  PRIMARY KEY (`DictTypeID`),
  KEY `Parent_DictTypeID` (`Parent_DictTypeID`) USING BTREE,
  CONSTRAINT `sys_dicttype_ibfk_1` FOREIGN KEY (`Parent_DictTypeID`) REFERENCES `sys_dicttype` (`DictTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=641 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_dicttype
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `Menu_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '功能菜单ID',
  `Parent_Menu_ID` bigint(20) DEFAULT NULL COMMENT '父功能菜单ID',
  `Menu_Code` varchar(50) DEFAULT NULL COMMENT '功能菜单编码',
  `Menu_Name` varchar(100) DEFAULT NULL COMMENT '功能菜单名称',
  `Menu_FullName` varchar(500) DEFAULT NULL COMMENT '功能菜单全称',
  `Menu_ID_Tree_Path` varchar(300) DEFAULT NULL COMMENT '菜单树形ID',
  `Menu_Level` int(11) DEFAULT NULL COMMENT '层级',
  `Menu_URL` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `Is_Menu` int(11) DEFAULT NULL COMMENT '是否菜单：1是，0否',
  `Is_Check` int(11) DEFAULT NULL COMMENT '是否检验权限',
  `Is_Expand` int(11) DEFAULT NULL COMMENT '在菜单中是否展开',
  `Menu_Icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`Menu_ID`),
  KEY `FK_Reference_67` (`Parent_Menu_ID`) USING BTREE,
  CONSTRAINT `sys_menu_ibfk_1` FOREIGN KEY (`Parent_Menu_ID`) REFERENCES `sys_menu` (`Menu_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4 COMMENT='Sys_Menu（功能菜单）';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('8', null, 'system', '系统管理', '系统管理', '.8.', '0', '', null, null, null, '', null, '0', '2018-03-21 14:59:33', null, '2019-10-30 11:46:35', null);
INSERT INTO `sys_menu` VALUES ('10', '8', 'dict', '字典管理', '字典管理', '.8.10.', '1', '', null, null, null, '', null, '0', '2018-03-21 15:17:36', null, '2019-10-30 11:46:37', null);
INSERT INTO `sys_menu` VALUES ('11', '8', 'org', '组织权限', '组织权限', '.8.11.', '1', '', null, null, null, '', null, '0', '2018-03-21 15:20:43', null, '2019-10-30 11:46:39', null);
INSERT INTO `sys_menu` VALUES ('12', '11', 'organizations', '组织结构', '组织结构', '.8.11.12.', '2', '', null, null, null, '', null, '0', '2019-10-30 11:46:22', null, '2019-10-30 11:46:42', null);
INSERT INTO `sys_menu` VALUES ('13', '11', 'user', '用户管理', '用户管理', '.8.11.13.', '2', '', null, null, null, '', null, '0', '2019-10-30 11:46:24', null, '2019-10-30 11:46:46', null);
INSERT INTO `sys_menu` VALUES ('14', '11', 'role', '角色权限', '角色权限', '.8.11.14.', '2', '', null, null, null, '', null, '0', '2019-10-30 11:46:27', null, '2019-10-30 11:46:48', null);
INSERT INTO `sys_menu` VALUES ('15', '11', 'menu', '菜单管理', '菜单管理', '.8.11.15.', '2', '', null, null, null, '', null, '0', '2019-10-30 11:46:29', null, '2019-10-30 11:46:50', null);

-- ----------------------------
-- Table structure for sys_menu_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_operation`;
CREATE TABLE `sys_menu_operation` (
  `MenuOperation_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单操作ID',
  `Operation_ID` bigint(20) DEFAULT NULL COMMENT '操作ID',
  `Menu_ID` bigint(20) DEFAULT NULL COMMENT '功能菜单ID',
  PRIMARY KEY (`MenuOperation_ID`),
  KEY `FK_Reference_64` (`Operation_ID`) USING BTREE,
  KEY `FK_Reference_65` (`Menu_ID`) USING BTREE,
  CONSTRAINT `sys_menu_operation_ibfk_1` FOREIGN KEY (`Operation_ID`) REFERENCES `sys_operation` (`Operation_ID`),
  CONSTRAINT `sys_menu_operation_ibfk_2` FOREIGN KEY (`Menu_ID`) REFERENCES `sys_menu` (`Menu_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1180 DEFAULT CHARSET=utf8mb4 COMMENT='Sys_Menu_Operation（菜单操作）';

-- ----------------------------
-- Records of sys_menu_operation
-- ----------------------------
INSERT INTO `sys_menu_operation` VALUES ('14', '1', '12');
INSERT INTO `sys_menu_operation` VALUES ('15', '2', '12');
INSERT INTO `sys_menu_operation` VALUES ('16', '3', '12');
INSERT INTO `sys_menu_operation` VALUES ('17', '4', '12');
INSERT INTO `sys_menu_operation` VALUES ('18', '1', '10');
INSERT INTO `sys_menu_operation` VALUES ('19', '2', '10');
INSERT INTO `sys_menu_operation` VALUES ('20', '3', '10');
INSERT INTO `sys_menu_operation` VALUES ('21', '4', '10');
INSERT INTO `sys_menu_operation` VALUES ('22', '1', '13');
INSERT INTO `sys_menu_operation` VALUES ('23', '2', '13');
INSERT INTO `sys_menu_operation` VALUES ('24', '3', '13');
INSERT INTO `sys_menu_operation` VALUES ('25', '4', '13');
INSERT INTO `sys_menu_operation` VALUES ('26', '1', '14');
INSERT INTO `sys_menu_operation` VALUES ('27', '2', '14');
INSERT INTO `sys_menu_operation` VALUES ('28', '3', '14');
INSERT INTO `sys_menu_operation` VALUES ('29', '4', '14');
INSERT INTO `sys_menu_operation` VALUES ('30', '1', '15');
INSERT INTO `sys_menu_operation` VALUES ('31', '2', '15');
INSERT INTO `sys_menu_operation` VALUES ('32', '3', '15');
INSERT INTO `sys_menu_operation` VALUES ('33', '4', '15');

-- ----------------------------
-- Table structure for sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `Operation_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '操作ID',
  `Operation_Code` varchar(50) DEFAULT NULL COMMENT '操作编码',
  `Operation_Name` varchar(100) DEFAULT NULL COMMENT '操作名称',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  `Required_Permission` int(255) DEFAULT '0',
  PRIMARY KEY (`Operation_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='Sys_Operation（操作）';

-- ----------------------------
-- Records of sys_operation
-- ----------------------------
INSERT INTO `sys_operation` VALUES ('1', 'list', '列表', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, '1', '1');
INSERT INTO `sys_operation` VALUES ('2', 'add', '新增', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, '2', '0');
INSERT INTO `sys_operation` VALUES ('3', 'update', '修改', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, '3', '0');
INSERT INTO `sys_operation` VALUES ('4', 'delete', '删除', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, '4', '0');
INSERT INTO `sys_operation` VALUES ('5', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('6', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('7', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('8', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('9', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('10', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('11', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('12', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('13', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('14', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('15', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('16', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('17', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('18', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('19', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('20', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('21', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('22', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('23', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('24', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('25', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('26', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');
INSERT INTO `sys_operation` VALUES ('27', '', '', '0', '2019-10-30 11:48:20', null, '2019-10-30 11:48:20', null, null, '0');

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log` (
  `Sys_Operation_Log_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `User_Type` int(11) DEFAULT NULL COMMENT '类型 1.系统用户 2.客户',
  `Operation_Content` varchar(1000) DEFAULT NULL COMMENT '内容',
  `Operation_Date` datetime DEFAULT NULL,
  `Bussiness_Number` varchar(255) DEFAULT NULL COMMENT '业务单号 ',
  `Bussiness_Step` varchar(255) DEFAULT NULL COMMENT '业务步骤 如 缴租明细-交租',
  `Bussiness_Type` varchar(255) DEFAULT NULL COMMENT '业务类型 如: 租车模块',
  `User_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织ID',
  PRIMARY KEY (`Sys_Operation_Log_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `Org_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '组织机构ID',
  `Org_Code` varchar(36) DEFAULT NULL COMMENT '组织机构编码',
  `Parent_Org_ID` bigint(20) DEFAULT NULL COMMENT '上级组织机构ID',
  `Org_Name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Org_FullName` varchar(2000) DEFAULT NULL COMMENT '组织机构全称',
  `Org_ID_Tree_Path` varchar(2000) DEFAULT NULL COMMENT '组织机构树形ID',
  `Org_Level` int(11) DEFAULT NULL COMMENT '组织层级：0平台；1集团；',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  `Org_Name_Tree_Path` varchar(2000) DEFAULT NULL COMMENT '组织机构树形名称',
  `Org_Logo` varchar(200) DEFAULT NULL COMMENT '组织Logo',
  `Longitude` float(16,5) DEFAULT NULL COMMENT '经度',
  `Latitude` float(16,5) DEFAULT NULL COMMENT '纬度',
  `Address` varchar(200) DEFAULT NULL COMMENT '地址',
  `Telephone` varchar(50) DEFAULT NULL COMMENT '电话',
  `Org_QRCode` varchar(200) DEFAULT NULL COMMENT '组织二维码',
  `Telephone_AfterSale` varchar(50) DEFAULT NULL COMMENT '电话(售后)',
  `Telephone_RentCar` varchar(255) DEFAULT NULL COMMENT '租车咨询电话',
  `Landline_RentCar` varchar(255) DEFAULT NULL COMMENT '咨询固话(租车)',
  `Telephone_Complain` varchar(255) DEFAULT NULL COMMENT '投拆电话',
  PRIMARY KEY (`Org_ID`),
  KEY `FK_Reference_16` (`Parent_Org_ID`) USING BTREE,
  CONSTRAINT `sys_organization_ibfk_1` FOREIGN KEY (`Parent_Org_ID`) REFERENCES `sys_organization` (`Org_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COMMENT='组织机构';

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('86', 'root', null, '平台', '0', '2018-03-15 10:05:59', null, '2019-10-30 11:44:01', null, '平台', '.86.', '0', null, '平台>', 'http://static.jxnewlife.com/file/org/D32F0640DAB1EF3675EBD4F01EBBF691.png', '113.44737', '22.52947', '中山市火炬开发区江陵西路轻轨桥侧', '88555558', 'http://static.jxnewlife.com/file/qr/6CEFD25A27EC67BF15DCFE37001DA6D6.png', '8855555870', '14900028283', '875089897971', '8855555890');

-- ----------------------------
-- Table structure for sys_org_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_org_module`;
CREATE TABLE `sys_org_module` (
  `Org_Module_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Org_Module_ID',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  `Menu_ID` bigint(20) DEFAULT NULL COMMENT '功能菜单ID',
  PRIMARY KEY (`Org_Module_ID`),
  KEY `FK_Reference_68` (`Org_ID`) USING BTREE,
  KEY `FK_Reference_69` (`Menu_ID`) USING BTREE,
  CONSTRAINT `sys_org_module_ibfk_1` FOREIGN KEY (`Org_ID`) REFERENCES `sys_organization` (`Org_ID`),
  CONSTRAINT `sys_org_module_ibfk_2` FOREIGN KEY (`Menu_ID`) REFERENCES `sys_menu` (`Menu_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='集团允许使用哪些模块';

-- ----------------------------
-- Records of sys_org_module
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `Role_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `Role_Code` varchar(50) DEFAULT NULL COMMENT '角色编码',
  `Role_Name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  `Role_Level` int(11) DEFAULT NULL COMMENT '角色等级',
  PRIMARY KEY (`Role_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '1', '平台管理员', '0', '2019-10-30 11:44:34', null, '2019-10-30 11:44:39', null, '86', '100');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `RolePermission_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限ID',
  `Menu_ID` bigint(20) DEFAULT NULL COMMENT '功能菜单ID',
  `Role_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `Operation_ID` bigint(20) DEFAULT NULL COMMENT '操作ID',
  PRIMARY KEY (`RolePermission_ID`),
  KEY `FK_Reference_35` (`Menu_ID`) USING BTREE,
  KEY `FK_Reference_36` (`Operation_ID`) USING BTREE,
  KEY `FK_Reference_37` (`Role_ID`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`Menu_ID`) REFERENCES `sys_menu` (`Menu_ID`),
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`Operation_ID`) REFERENCES `sys_operation` (`Operation_ID`),
  CONSTRAINT `sys_role_permission_ibfk_3` FOREIGN KEY (`Role_ID`) REFERENCES `sys_role` (`Role_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COMMENT='Sys_Role_Permission（角色权限）';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `User_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户档案ID',
  `Org_ID` bigint(20) DEFAULT NULL COMMENT '组织机构ID',
  `User_Code` varchar(36) DEFAULT NULL COMMENT '用户编码',
  `Login_ID` varchar(50) DEFAULT NULL COMMENT '登录账号',
  `User_Name` varchar(100) DEFAULT NULL COMMENT '用户名称',
  `Mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `Password` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `Email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `Avatar` varchar(200) DEFAULT NULL COMMENT '用户头像',
  `NickName` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `Sex` int(11) DEFAULT NULL COMMENT '性别',
  `Birthday` datetime DEFAULT NULL COMMENT '生日{}',
  `Telphone` varchar(50) DEFAULT NULL COMMENT '电话',
  `QQ` varchar(100) DEFAULT NULL COMMENT 'QQ',
  `WX` varchar(100) DEFAULT NULL COMMENT '微信',
  `Status` int(11) DEFAULT '0' COMMENT '数据状态：0-正常，1-删除',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间{}',
  `Create_User` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `Modify_Time` datetime DEFAULT NULL COMMENT '修改时间{}',
  `Modify_User` bigint(20) DEFAULT NULL COMMENT '修改人ID',
  `RealName` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `UserStatus` int(11) DEFAULT '0' COMMENT '用户状态：0-正常，1-禁用',
  `Is_Cell_Manage` int(11) DEFAULT '0' COMMENT '是否行销管理员：0-否，1-是',
  `Is_Company` tinyint(4) DEFAULT '0' COMMENT '是否行公司用户：0-否，1-是',
  PRIMARY KEY (`User_ID`),
  KEY `FK_Reference_15` (`Org_ID`) USING BTREE,
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`Org_ID`) REFERENCES `sys_organization` (`Org_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=utf8mb4 COMMENT='用户档案';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '86', '400-9632345', '123456', 'admin', '', 'F19B8DC2029CF707939E886E4B164681', '', 'http://static.jxnewlife.com/file/user/C9B3E58E5535A3678C9187CA8D567056.jpg', '平台管理员', null, '2019-10-30 11:43:14', '', '', '', '0', '2019-10-30 11:43:22', null, '2018-12-10 10:34:11', null, '管理员', '0', '0', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `UserRole_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `Role_ID` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `User_ID` bigint(20) DEFAULT NULL COMMENT '用户档案ID',
  `Order_Number` int(11) DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`UserRole_ID`),
  KEY `FK_Reference_38` (`Role_ID`) USING BTREE,
  KEY `FK_Reference_39` (`User_ID`) USING BTREE,
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`Role_ID`) REFERENCES `sys_role` (`Role_ID`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`User_ID`) REFERENCES `sys_user` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', null);

-- ----------------------------
-- Table structure for sys_verification
-- ----------------------------
DROP TABLE IF EXISTS `sys_verification`;
CREATE TABLE `sys_verification` (
  `Verification_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '系统验证ID',
  `Member_ID` bigint(20) DEFAULT NULL COMMENT '会员ID',
  `User_ID` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `Verification_Type` int(11) DEFAULT NULL COMMENT '验证类型：1手机；2邮箱；',
  `Verification_Content` varchar(200) DEFAULT NULL COMMENT '验证内容',
  `Verification_Code` varchar(100) DEFAULT NULL COMMENT '验证码',
  `Business_Type` int(11) DEFAULT NULL COMMENT '业务类型：1手机号码真实性验证；2邮箱真实性验证；3手机登录；',
  `Business_State` int(11) DEFAULT NULL COMMENT '业务状态：0未使用；1已使用；',
  `Create_Time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`Verification_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9411 DEFAULT CHARSET=utf8mb4 COMMENT='系统验证：手机验证、邮箱验证';

-- ----------------------------
-- Records of sys_verification
-- ----------------------------
