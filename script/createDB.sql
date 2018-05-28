一、调整MYSQL字符集
1、“my.ini”文件：
[client]

port=3306
default-character-set=utf8

[mysql]

default-character-set=utf8

[mysqld]
character-set-server=utf8
collation-server=utf8_general_ci
# The default storage engine that will be used when create new tables when
default-storage-engine=INNODB

2、SHOW VARIABLES LIKE '%char%'

3、建库建表
CREATE DATABASE mw_demo CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use mw_demo;
