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
CREATE DATABASE rf_ffp CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use rf_ffp;

create table taskexe (
	id integer not null auto_increment,
	taskno varchar(30),
	taskname varchar(50),
	requestmap varchar(100),
	execlass varchar(100),
	exetimes integer,
	exelastdatetime datetime,
	primary key(id)
);

create table tasklog (
	id integer not null auto_increment,
	taskid integer,
	exedatetime datetime,
	timeconsume decimal(10,3),
	result varchar(100),
	log varchar(500),
	primary key(id)
);
