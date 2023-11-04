# build database for short video business service
DROP DATABASE IF EXISTS `db_short_video_business_service`;
CREATE DATABASE `db_short_video_business_service`;

USE `db_short_video_business_service`;

# create table for user
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
    `pk_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    `nickname` VARCHAR(18) NOT NULL COMMENT '昵称',
    `phone` VARCHAR(12) NOT NULL COMMENT '手机号',
    `password` VARCHAR(200) NOT NULL COMMENT '密码',
    `sex` TINYINT(2) NOT NULL COMMENT '性别 (0:未知, 1:男, 2:女)',
    `is_banned` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否被封禁',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_user_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for avatar of user
DROP TABLE IF EXISTS `tb_avatar`;
CREATE TABLE `tb_avatar` (
    `pk_avatar_id` BIGINT(20) NOT NULL COMMENT '头像id',
    `fk_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    `fk_file_id` BIGINT(20) NOT NULL COMMENT '文件id',
    `url` VARCHAR(200) NOT NULL COMMENT '头像链接',
    `len` INT(8) NOT NULL COMMENT '链接长度',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_avatar_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for user's favourite [user <-> post]
DROP TABLE IF EXISTS `tb_user_favourite`;
CREATE TABLE `tb_user_favourite` (
    `pk_favourite_id` BIGINT(20) NOT NULL COMMENT '收藏id',
    `fk_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    `fk_post_id` BIGINT(20) NOT NULL COMMENT '帖子id',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_favourite_id`),
    INDEX (`fk_user_id`, `fk_post_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for user's like [user <-> post]
DROP TABLE IF EXISTS `tb_user_like`;
CREATE TABLE `tb_user_like` (
    `pk_like_id` BIGINT(20) NOT NULL COMMENT '赞赏id',
    `fk_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
    `fk_post_id` BIGINT(20) NOT NULL COMMENT '帖子id',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_like_id`),
    INDEX (`fk_user_id`, fk_post_id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for post
DROP TABLE IF EXISTS `tb_post`;
CREATE TABLE `tb_post` (
    `pk_post_id` BIGINT(20) NOT NULL COMMENT '帖子id',
    `post_by` BIGINT(20) NOT NULL COMMENT '发布人',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_post_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for tag of post
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
    `pk_tag_id` BIGINT(20) NOT NULL COMMENT '标签id',
    `name` VARCHAR(20) NOT NULL COMMENT '标签名',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_tag_id`),
    INDEX (`name`, `is_deleted`, `is_deletable`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;

# create table for relationship of post and tag
DROP TABLE IF EXISTS `tb_post_tag`;
CREATE TABLE `tb_post_tag` (
    `pk_merge_id` BIGINT(20) NOT NULL COMMENT '关系id',
    `fk_post_id` BIGINT(20) NOT NULL COMMENT '帖子id',
    `fk_tag_id` BIGINT(20) NOT NULL COMMENT '标签id',
    ### common ###
    `is_deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已删除',
    `is_deletable` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否可删除',
    `gmt_create` DATETIME NOT NULL COMMENT '创建时间',
    `gmt_update` DATETIME NULL COMMENT '更新时间',
    `gmt_delete` DATETIME NULL COMMENT '删除时间',
    PRIMARY KEY (`pk_merge_id`),
    INDEX (`fk_post_id`, `fk_tag_id`, `is_deleted`, `is_deletable`),
    INDEX (`fk_tag_id`, `fk_post_id`, `is_deleted`, `is_deletable`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4;