-- create dataBase db_community
use db_community;

drop table if exists tb_admin;
drop table if exists tb_stop;
drop table if exists tb_fee_type;
drop table if exists tb_house;
drop table if exists tb_user;
drop table if exists tb_house;
drop table if exists tb_stop;
drop table if exists tb_fee_type;
drop table if exists tb_fee;
drop table if exists tb_repair;
drop table if exists tb_repair_type;
drop table if exists tb_complain;
drop table if exists tb_current_car;
drop table if exists tb_user_car;

-- 管理员表
create table tb_admin(
    id bigint not null primary key auto_increment comment '主键id',
    username varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
    password varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
    phone varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '管理员表';

insert into tb_admin values (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '18879569671');


-- 用户表
create table tb_user(
    id bigint not null primary key auto_increment comment '主键id',
    username varchar(32) COLLATE utf8_bin DEFAULT '未注册' COMMENT '用户名',
    password varchar(64) COLLATE utf8_bin DEFAULT '未注册' COMMENT '密码',
    status tinyint DEFAULT 0 comment '0禁用1启用',
    name varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
    sex tinyint NOT NULL COMMENT '0女1男',
    phone varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '用户表';

insert into tb_user values(1, 'ao666', 'e10adc3949ba59abbe56e057f20f883e', '1','test', '1', '18879569671');

-- 房屋表，
create table tb_house(
    id bigint not null primary key auto_increment comment '主键id',
    user_id bigint DEFAULT 0 comment '所属用户的id',
    building tinyint not null comment '楼栋号',
    cell tinyint not null comment '单元',
    floor tinyint not null comment '楼层',
    doorplate varchar(20) COLLATE utf8_bin comment '门牌号',
    status tinyint not null comment '0未出售1已出售',
    create_time datetime DEFAULT NULL COMMENT '创建时间',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    create_user varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
    update_user varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '修改人'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '房屋表';

insert into tb_house values(1, 1, 1, 1, 1, '101', 1, now(), now(), '开发者', '开发者');

-- 车位表
create table tb_stop(
    id bigint not null primary key auto_increment comment '主键id',
    place varchar(20) COLLATE utf8_bin not null comment '所属区域位置',
    status tinyint DEFAULT 0 comment '0未出租1已出租',
	user_id bigint DEFAULT 0 comment '所属用户的id，0表示公共车位',
    car_license varchar(10) COLLATE utf8_bin DEFAULT 'xxxx' comment '私有的车牌', 
    rent_month decimal(10,2) DEFAULT 500 comment '月租金',
    create_time datetime DEFAULT now() COMMENT '创建时间',
    update_time datetime DEFAULT now() COMMENT '更新时间',
    create_user varchar(10) COLLATE utf8_bin DEFAULT "开发者" COMMENT '创建人',
    update_user varchar(10) COLLATE utf8_bin DEFAULT "开发者" COMMENT '修改人'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '车位表';

insert into tb_stop values(1, 'A区01号', 1, 1, "赣A8888", 700, now(), now(), "开发者", "开发者");


-- 费用类型表
create table tb_fee_type(
    id bigint not null primary key auto_increment comment '主键id',
    name varchar(20) COLLATE utf8_bin not null comment '费用名称'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '费用名称表';

insert into tb_fee_type values (1, '电费');
insert into tb_fee_type values (2, '水费');
insert into tb_fee_type values (3, '临时停车费');
insert into tb_fee_type values (4, '停车位费');

-- 用户物业费表
create table tb_fee(
    id bigint not null primary key auto_increment comment '主键id',
    user_id bigint not null comment '用户id',
    type_id bigint not null comment '费用类型id',
    house_id bigint DEFAULT 0 comment '水电费对应楼栋id',
    stop_id bigint DEFAULT 0 comment '停车费对应车位id',
    amount decimal(10,2) not null comment '费用',
    status tinyint not null DEFAULT 0 comment '0未缴费1已缴费',
    create_time datetime DEFAULT now() COMMENT '创建时间',
    update_time datetime DEFAULT now() COMMENT '更新时间',
    create_user varchar(10) COLLATE utf8_bin DEFAULT "开发者" COMMENT '创建人',
    update_user varchar(10) COLLATE utf8_bin DEFAULT "开发者" COMMENT '修改人'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '用户物业费表';
insert into tb_fee values(1, 1, 1, 1, 0, 300, 0, now(), now(), '开发者', '开发者');
insert into tb_fee values(2, 1, 2, 1, 0, 200, 1, now(), now(), '开发者', '开发者');
insert into tb_fee values(3, 1, 3, 0, 1, 20, 0, now(), now(), '开发者', '开发者');
insert into tb_fee values(4, 1, 4, 0, 1, 500, 1, now(), now(), '开发者', '开发者');

-- 报修类型表
create table tb_repair_type(
    id bigint not null primary key auto_increment comment '主键id',
    name varchar(20) not null comment '报修类型名称'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '报修类型表';

insert into tb_repair_type values(1, '电设施维修');
insert into tb_repair_type values(2, '水设施维修');
insert into tb_repair_type values(3, '墙体路面维修');
insert into tb_repair_type values(4, '电梯维修');
insert into tb_repair_type values(5, '消防设施维修');
insert into tb_repair_type values(6, '其它维修');

-- 报修信息表
create table tb_repair(
    id bigint not null primary key auto_increment comment '主键id',
    user_id bigint not null comment '发起报修的用户id',
    type_id bigint not null comment '维修类型id',
    house_id bigint not null comment '待维修的房屋id',
    status tinyint not null comment '0未维修 1已维修',
    image varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图片',
    details varchar(200) DEFAULT '用户未提供详细描述' comment '详细描述'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '报修信息表';
insert into tb_repair values(1, 1, 1, 1, 0, 'https://web-ao666.oss-cn-guangzhou.aliyuncs.com/dd.jpg', '楼道电灯坏了');
insert into tb_repair values(2, 1, 2, 1, 0, 'https://web-ao666.oss-cn-guangzhou.aliyuncs.com/sg.jpg', '水管坏了');
insert into tb_repair values(3, 1, 3, 1, 1, 'https://web-ao666.oss-cn-guangzhou.aliyuncs.com/lj.jpg', '楼道垃圾堆积');
insert into tb_repair values(4, 1, 4, 1, 1, 'https://web-ao666.oss-cn-guangzhou.aliyuncs.com/dt.jpg', '电梯坏了');
insert into tb_repair values(5, 1, 5, 1, 0, 'https://web-ao666.oss-cn-guangzhou.aliyuncs.com/xxs.jpg', '消防栓松了');

-- 投诉信息表
create table tb_complain(
    id bigint not null primary key auto_increment comment '主键id',
    user_id bigint not null comment '发起投诉的用户id',
    details varchar(200) COLLATE utf8_bin not null comment '投诉内容',
    status tinyint not null comment '0未解决，1已解决'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '投诉表';
insert into tb_complain values(1, 1, '1栋前绿化未修剪', 0);
insert into tb_complain values(2, 1, '1栋前马路上有杂物堆积', 1);

-- 小区内实时车辆表
create table tb_current_car(
    id bigint not null primary key auto_increment comment '主键id',
    car_license varchar(10) COLLATE utf8_bin not null comment '车牌号',
    user_id bigint not null DEFAULT 0 comment '所属用户的id，为0表示外来车辆',
    entry_time datetime not null comment '进入小区的时间'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '临时车辆表';

-- 住户车辆表
create table tb_user_car(
    id bigint not null primary key auto_increment comment '主键id',
    car_license varchar(10) COLLATE utf8_bin not null comment '车牌号',
    user_id bigint not null comment '所属用户的id'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin comment '住户登记的车辆表';

-- 初始房屋信息
insert into 
tb_house (building, cell, floor, doorplate, status, create_time, update_time, create_user, update_user) 
VALUES 
(1, 1, 1, '102', 0, now(), now(), '开发者', '开发者'),
(1, 1, 2, '201', 0, now(), now(), '开发者', '开发者'),
(1, 1, 2, '202', 0, now(), now(), '开发者', '开发者'),
(1, 1, 3, '301', 0, now(), now(), '开发者', '开发者'),
(1, 1, 3, '302', 0, now(), now(), '开发者', '开发者'),
(1, 1, 4, '401', 0, now(), now(), '开发者', '开发者'),
(1, 1, 4, '402', 0, now(), now(), '开发者', '开发者'),
(1, 1, 5, '501', 0, now(), now(), '开发者', '开发者'),
(1, 1, 5, '502', 0, now(), now(), '开发者', '开发者'),
(1, 1, 6, '601', 0, now(), now(), '开发者', '开发者'),
(1, 1, 6, '602', 0, now(), now(), '开发者', '开发者'),
(1, 1, 7, '701', 0, now(), now(), '开发者', '开发者'),
(1, 1, 7, '702', 0, now(), now(), '开发者', '开发者'),
(1, 1, 8, '801', 0, now(), now(), '开发者', '开发者'),
(1, 1, 8, '802', 0, now(), now(), '开发者', '开发者'),
(1, 2, 1, '101', 0, now(), now(), '开发者', '开发者'),
(1, 2, 1, '102', 0, now(), now(), '开发者', '开发者'),
(1, 2, 2, '201', 0, now(), now(), '开发者', '开发者'),
(1, 2, 2, '202', 0, now(), now(), '开发者', '开发者'),
(1, 2, 3, '301', 0, now(), now(), '开发者', '开发者'),
(1, 2, 3, '302', 0, now(), now(), '开发者', '开发者'),
(1, 2, 4, '401', 0, now(), now(), '开发者', '开发者'),
(1, 2, 4, '402', 0, now(), now(), '开发者', '开发者'),
(1, 2, 5, '501', 0, now(), now(), '开发者', '开发者'),
(1, 2, 5, '502', 0, now(), now(), '开发者', '开发者'),
(1, 2, 6, '601', 0, now(), now(), '开发者', '开发者'),
(1, 2, 6, '602', 0, now(), now(), '开发者', '开发者'),
(1, 2, 7, '701', 0, now(), now(), '开发者', '开发者'),
(1, 2, 7, '702', 0, now(), now(), '开发者', '开发者'),
(1, 2, 8, '801', 0, now(), now(), '开发者', '开发者'),
(1, 2, 8, '802', 0, now(), now(), '开发者', '开发者'),
(1, 3, 1, '101', 0, now(), now(), '开发者', '开发者'),
(1, 3, 1, '102', 0, now(), now(), '开发者', '开发者'),
(1, 3, 2, '201', 0, now(), now(), '开发者', '开发者'),
(1, 3, 2, '202', 0, now(), now(), '开发者', '开发者'),
(1, 3, 3, '301', 0, now(), now(), '开发者', '开发者'),
(1, 3, 3, '302', 0, now(), now(), '开发者', '开发者'),
(1, 3, 4, '401', 0, now(), now(), '开发者', '开发者'),
(1, 3, 4, '402', 0, now(), now(), '开发者', '开发者'),
(1, 3, 5, '501', 0, now(), now(), '开发者', '开发者'),
(1, 3, 5, '502', 0, now(), now(), '开发者', '开发者'),
(1, 3, 6, '601', 0, now(), now(), '开发者', '开发者'),
(1, 3, 6, '602', 0, now(), now(), '开发者', '开发者'),
(1, 3, 7, '701', 0, now(), now(), '开发者', '开发者'),
(1, 3, 7, '702', 0, now(), now(), '开发者', '开发者'),
(1, 3, 8, '801', 0, now(), now(), '开发者', '开发者'),
(1, 3, 8, '802', 0, now(), now(), '开发者', '开发者'),
(2, 1, 1, '101', 0, now(), now(), '开发者', '开发者'),
(2, 1, 1, '102', 0, now(), now(), '开发者', '开发者'),
(2, 1, 2, '201', 0, now(), now(), '开发者', '开发者'),
(2, 1, 2, '202', 0, now(), now(), '开发者', '开发者'),
(2, 1, 3, '301', 0, now(), now(), '开发者', '开发者'),
(2, 1, 3, '302', 0, now(), now(), '开发者', '开发者'),
(2, 1, 4, '401', 0, now(), now(), '开发者', '开发者'),
(2, 1, 4, '402', 0, now(), now(), '开发者', '开发者'),
(2, 1, 5, '501', 0, now(), now(), '开发者', '开发者'),
(2, 1, 5, '502', 0, now(), now(), '开发者', '开发者'),
(2, 1, 6, '601', 0, now(), now(), '开发者', '开发者'),
(2, 1, 6, '602', 0, now(), now(), '开发者', '开发者'),
(2, 1, 7, '701', 0, now(), now(), '开发者', '开发者'),
(2, 1, 7, '702', 0, now(), now(), '开发者', '开发者'),
(2, 1, 8, '801', 0, now(), now(), '开发者', '开发者'),
(2, 1, 8, '802', 0, now(), now(), '开发者', '开发者'),
(2, 2, 1, '101', 0, now(), now(), '开发者', '开发者'),
(2, 2, 1, '102', 0, now(), now(), '开发者', '开发者'),
(2, 2, 2, '201', 0, now(), now(), '开发者', '开发者'),
(2, 2, 2, '202', 0, now(), now(), '开发者', '开发者'),
(2, 2, 3, '301', 0, now(), now(), '开发者', '开发者'),
(2, 2, 3, '302', 0, now(), now(), '开发者', '开发者'),
(2, 2, 4, '401', 0, now(), now(), '开发者', '开发者'),
(2, 2, 4, '402', 0, now(), now(), '开发者', '开发者'),
(2, 2, 5, '501', 0, now(), now(), '开发者', '开发者'),
(2, 2, 5, '502', 0, now(), now(), '开发者', '开发者'),
(2, 2, 6, '601', 0, now(), now(), '开发者', '开发者'),
(2, 2, 6, '602', 0, now(), now(), '开发者', '开发者'),
(2, 2, 7, '701', 0, now(), now(), '开发者', '开发者'),
(2, 2, 7, '702', 0, now(), now(), '开发者', '开发者'),
(2, 2, 8, '801', 0, now(), now(), '开发者', '开发者'),
(2, 2, 8, '802', 0, now(), now(), '开发者', '开发者'),
(2, 3, 1, '101', 0, now(), now(), '开发者', '开发者'),
(2, 3, 1, '102', 0, now(), now(), '开发者', '开发者'),
(2, 3, 2, '201', 0, now(), now(), '开发者', '开发者'),
(2, 3, 2, '202', 0, now(), now(), '开发者', '开发者'),
(2, 3, 3, '301', 0, now(), now(), '开发者', '开发者'),
(2, 3, 3, '302', 0, now(), now(), '开发者', '开发者'),
(2, 3, 4, '401', 0, now(), now(), '开发者', '开发者'),
(2, 3, 4, '402', 0, now(), now(), '开发者', '开发者'),
(2, 3, 5, '501', 0, now(), now(), '开发者', '开发者'),
(2, 3, 5, '502', 0, now(), now(), '开发者', '开发者'),
(2, 3, 6, '601', 0, now(), now(), '开发者', '开发者'),
(2, 3, 6, '602', 0, now(), now(), '开发者', '开发者'),
(2, 3, 7, '701', 0, now(), now(), '开发者', '开发者'),
(2, 3, 7, '702', 0, now(), now(), '开发者', '开发者'),
(2, 3, 8, '801', 0, now(), now(), '开发者', '开发者'),
(2, 3, 8, '802', 0, now(), now(), '开发者', '开发者'),
(3, 1, 1, '101', 0, now(), now(), '开发者', '开发者'),
(3, 1, 1, '102', 0, now(), now(), '开发者', '开发者'),
(3, 1, 2, '201', 0, now(), now(), '开发者', '开发者'),
(3, 1, 2, '202', 0, now(), now(), '开发者', '开发者'),
(3, 1, 3, '301', 0, now(), now(), '开发者', '开发者'),
(3, 1, 3, '302', 0, now(), now(), '开发者', '开发者'),
(3, 1, 4, '401', 0, now(), now(), '开发者', '开发者'),
(3, 1, 4, '402', 0, now(), now(), '开发者', '开发者'),
(3, 1, 5, '501', 0, now(), now(), '开发者', '开发者'),
(3, 1, 5, '502', 0, now(), now(), '开发者', '开发者'),
(3, 1, 6, '601', 0, now(), now(), '开发者', '开发者'),
(3, 1, 6, '602', 0, now(), now(), '开发者', '开发者'),
(3, 1, 7, '701', 0, now(), now(), '开发者', '开发者'),
(3, 1, 7, '702', 0, now(), now(), '开发者', '开发者'),
(3, 1, 8, '801', 0, now(), now(), '开发者', '开发者'),
(3, 1, 8, '802', 0, now(), now(), '开发者', '开发者'),
(3, 2, 1, '101', 0, now(), now(), '开发者', '开发者'),
(3, 2, 1, '102', 0, now(), now(), '开发者', '开发者'),
(3, 2, 2, '201', 0, now(), now(), '开发者', '开发者'),
(3, 2, 2, '202', 0, now(), now(), '开发者', '开发者'),
(3, 2, 3, '301', 0, now(), now(), '开发者', '开发者'),
(3, 2, 3, '302', 0, now(), now(), '开发者', '开发者'),
(3, 2, 4, '401', 0, now(), now(), '开发者', '开发者'),
(3, 2, 4, '402', 0, now(), now(), '开发者', '开发者'),
(3, 2, 5, '501', 0, now(), now(), '开发者', '开发者'),
(3, 2, 5, '502', 0, now(), now(), '开发者', '开发者'),
(3, 2, 6, '601', 0, now(), now(), '开发者', '开发者'),
(3, 2, 6, '602', 0, now(), now(), '开发者', '开发者'),
(3, 2, 7, '701', 0, now(), now(), '开发者', '开发者'),
(3, 2, 7, '702', 0, now(), now(), '开发者', '开发者'),
(3, 2, 8, '801', 0, now(), now(), '开发者', '开发者'),
(3, 2, 8, '802', 0, now(), now(), '开发者', '开发者'),
(3, 3, 1, '101', 0, now(), now(), '开发者', '开发者'),
(3, 3, 1, '102', 0, now(), now(), '开发者', '开发者'),
(3, 3, 2, '201', 0, now(), now(), '开发者', '开发者'),
(3, 3, 2, '202', 0, now(), now(), '开发者', '开发者'),
(3, 3, 3, '301', 0, now(), now(), '开发者', '开发者'),
(3, 3, 3, '302', 0, now(), now(), '开发者', '开发者'),
(3, 3, 4, '401', 0, now(), now(), '开发者', '开发者'),
(3, 3, 4, '402', 0, now(), now(), '开发者', '开发者'),
(3, 3, 5, '501', 0, now(), now(), '开发者', '开发者'),
(3, 3, 5, '502', 0, now(), now(), '开发者', '开发者'),
(3, 3, 6, '601', 0, now(), now(), '开发者', '开发者'),
(3, 3, 6, '602', 0, now(), now(), '开发者', '开发者'),
(3, 3, 7, '701', 0, now(), now(), '开发者', '开发者'),
(3, 3, 7, '702', 0, now(), now(), '开发者', '开发者'),
(3, 3, 8, '801', 0, now(), now(), '开发者', '开发者'),
(3, 3, 8, '802', 0, now(), now(), '开发者', '开发者'),
(4, 1, 1, '101', 0, now(), now(), '开发者', '开发者'),
(4, 1, 1, '102', 0, now(), now(), '开发者', '开发者'),
(4, 1, 2, '201', 0, now(), now(), '开发者', '开发者'),
(4, 1, 2, '202', 0, now(), now(), '开发者', '开发者'),
(4, 1, 3, '301', 0, now(), now(), '开发者', '开发者'),
(4, 1, 3, '302', 0, now(), now(), '开发者', '开发者'),
(4, 1, 4, '401', 0, now(), now(), '开发者', '开发者'),
(4, 1, 4, '402', 0, now(), now(), '开发者', '开发者'),
(4, 1, 5, '501', 0, now(), now(), '开发者', '开发者'),
(4, 1, 5, '502', 0, now(), now(), '开发者', '开发者'),
(4, 1, 6, '601', 0, now(), now(), '开发者', '开发者'),
(4, 1, 6, '602', 0, now(), now(), '开发者', '开发者'),
(4, 1, 7, '701', 0, now(), now(), '开发者', '开发者'),
(4, 1, 7, '702', 0, now(), now(), '开发者', '开发者'),
(4, 1, 8, '801', 0, now(), now(), '开发者', '开发者'),
(4, 1, 8, '802', 0, now(), now(), '开发者', '开发者'),
(4, 2, 1, '101', 0, now(), now(), '开发者', '开发者'),
(4, 2, 1, '102', 0, now(), now(), '开发者', '开发者'),
(4, 2, 2, '201', 0, now(), now(), '开发者', '开发者'),
(4, 2, 2, '202', 0, now(), now(), '开发者', '开发者'),
(4, 2, 3, '301', 0, now(), now(), '开发者', '开发者'),
(4, 2, 3, '302', 0, now(), now(), '开发者', '开发者'),
(4, 2, 4, '401', 0, now(), now(), '开发者', '开发者'),
(4, 2, 4, '402', 0, now(), now(), '开发者', '开发者'),
(4, 2, 5, '501', 0, now(), now(), '开发者', '开发者'),
(4, 2, 5, '502', 0, now(), now(), '开发者', '开发者'),
(4, 2, 6, '601', 0, now(), now(), '开发者', '开发者'),
(4, 2, 6, '602', 0, now(), now(), '开发者', '开发者'),
(4, 2, 7, '701', 0, now(), now(), '开发者', '开发者'),
(4, 2, 7, '702', 0, now(), now(), '开发者', '开发者'),
(4, 2, 8, '801', 0, now(), now(), '开发者', '开发者'),
(4, 2, 8, '802', 0, now(), now(), '开发者', '开发者'),
(4, 3, 1, '101', 0, now(), now(), '开发者', '开发者'),
(4, 3, 1, '102', 0, now(), now(), '开发者', '开发者'),
(4, 3, 2, '201', 0, now(), now(), '开发者', '开发者'),
(4, 3, 2, '202', 0, now(), now(), '开发者', '开发者'),
(4, 3, 3, '301', 0, now(), now(), '开发者', '开发者'),
(4, 3, 3, '302', 0, now(), now(), '开发者', '开发者'),
(4, 3, 4, '401', 0, now(), now(), '开发者', '开发者'),
(4, 3, 4, '402', 0, now(), now(), '开发者', '开发者'),
(4, 3, 5, '501', 0, now(), now(), '开发者', '开发者'),
(4, 3, 5, '502', 0, now(), now(), '开发者', '开发者'),
(4, 3, 6, '601', 0, now(), now(), '开发者', '开发者'),
(4, 3, 6, '602', 0, now(), now(), '开发者', '开发者'),
(4, 3, 7, '701', 0, now(), now(), '开发者', '开发者'),
(4, 3, 7, '702', 0, now(), now(), '开发者', '开发者'),
(4, 3, 8, '801', 0, now(), now(), '开发者', '开发者'),
(4, 3, 8, '802', 0, now(), now(), '开发者', '开发者'),
(5, 1, 1, '101', 0, now(), now(), '开发者', '开发者'),
(5, 1, 1, '102', 0, now(), now(), '开发者', '开发者'),
(5, 1, 2, '201', 0, now(), now(), '开发者', '开发者'),
(5, 1, 2, '202', 0, now(), now(), '开发者', '开发者'),
(5, 1, 3, '301', 0, now(), now(), '开发者', '开发者'),
(5, 1, 3, '302', 0, now(), now(), '开发者', '开发者'),
(5, 1, 4, '401', 0, now(), now(), '开发者', '开发者'),
(5, 1, 4, '402', 0, now(), now(), '开发者', '开发者'),
(5, 1, 5, '501', 0, now(), now(), '开发者', '开发者'),
(5, 1, 5, '502', 0, now(), now(), '开发者', '开发者'),
(5, 1, 6, '601', 0, now(), now(), '开发者', '开发者'),
(5, 1, 6, '602', 0, now(), now(), '开发者', '开发者'),
(5, 1, 7, '701', 0, now(), now(), '开发者', '开发者'),
(5, 1, 7, '702', 0, now(), now(), '开发者', '开发者'),
(5, 1, 8, '801', 0, now(), now(), '开发者', '开发者'),
(5, 1, 8, '802', 0, now(), now(), '开发者', '开发者'),
(5, 2, 1, '101', 0, now(), now(), '开发者', '开发者'),
(5, 2, 1, '102', 0, now(), now(), '开发者', '开发者'),
(5, 2, 2, '201', 0, now(), now(), '开发者', '开发者'),
(5, 2, 2, '202', 0, now(), now(), '开发者', '开发者'),
(5, 2, 3, '301', 0, now(), now(), '开发者', '开发者'),
(5, 2, 3, '302', 0, now(), now(), '开发者', '开发者'),
(5, 2, 4, '401', 0, now(), now(), '开发者', '开发者'),
(5, 2, 4, '402', 0, now(), now(), '开发者', '开发者'),
(5, 2, 5, '501', 0, now(), now(), '开发者', '开发者'),
(5, 2, 5, '502', 0, now(), now(), '开发者', '开发者'),
(5, 2, 6, '601', 0, now(), now(), '开发者', '开发者'),
(5, 2, 6, '602', 0, now(), now(), '开发者', '开发者'),
(5, 2, 7, '701', 0, now(), now(), '开发者', '开发者'),
(5, 2, 7, '702', 0, now(), now(), '开发者', '开发者'),
(5, 2, 8, '801', 0, now(), now(), '开发者', '开发者'),
(5, 2, 8, '802', 0, now(), now(), '开发者', '开发者'),
(5, 3, 1, '101', 0, now(), now(), '开发者', '开发者'),
(5, 3, 1, '102', 0, now(), now(), '开发者', '开发者'),
(5, 3, 2, '201', 0, now(), now(), '开发者', '开发者'),
(5, 3, 2, '202', 0, now(), now(), '开发者', '开发者'),
(5, 3, 3, '301', 0, now(), now(), '开发者', '开发者'),
(5, 3, 3, '302', 0, now(), now(), '开发者', '开发者'),
(5, 3, 4, '401', 0, now(), now(), '开发者', '开发者'),
(5, 3, 4, '402', 0, now(), now(), '开发者', '开发者'),
(5, 3, 5, '501', 0, now(), now(), '开发者', '开发者'),
(5, 3, 5, '502', 0, now(), now(), '开发者', '开发者'),
(5, 3, 6, '601', 0, now(), now(), '开发者', '开发者'),
(5, 3, 6, '602', 0, now(), now(), '开发者', '开发者'),
(5, 3, 7, '701', 0, now(), now(), '开发者', '开发者'),
(5, 3, 7, '702', 0, now(), now(), '开发者', '开发者'),
(5, 3, 8, '801', 0, now(), now(), '开发者', '开发者'),
(5, 3, 8, '802', 0, now(), now(), '开发者', '开发者');


insert into tb_stop (place) values
('A区02号'),
('A区03号'),
('A区04号'),
('A区05号'),
('A区06号'),
('A区07号'),
('A区08号'),
('A区09号'),
('A区10号'),
('A区11号'),
('A区12号'),
('B区01号'),
('B区02号'),
('B区03号'),
('B区04号'),
('B区05号'),
('B区06号'),
('B区07号'),
('B区08号'),
('B区09号'),
('B区10号'),
('B区11号'),
('B区12号'),
('C区01号'),
('C区02号'),
('C区03号'),
('C区04号'),
('C区05号'),
('C区06号'),
('C区07号'),
('C区08号'),
('C区09号'),
('C区10号'),
('C区11号'),
('C区12号'),
('D区01号'),
('D区02号'),
('D区03号'),
('D区04号'),
('D区05号'),
('D区06号'),
('D区07号'),
('D区08号'),
('D区09号'),
('D区10号'),
('D区11号'),
('D区12号'),
('E区01号'),
('E区02号'),
('E区03号'),
('E区04号'),
('E区05号'),
('E区06号'),
('E区07号'),
('E区08号'),
('E区09号'),
('E区10号'),
('E区11号'),
('E区12号'),
('F区01号'),
('F区02号'),
('F区03号'),
('F区04号'),
('F区05号'),
('F区06号'),
('F区07号'),
('F区08号'),
('F区09号'),
('F区10号'),
('F区11号'),
('F区12号'),
('G区01号'),
('G区02号'),
('G区03号'),
('G区04号'),
('G区05号'),
('G区06号'),
('G区07号'),
('G区08号'),
('G区09号'),
('G区10号'),
('G区11号'),
('G区12号'),
('H区01号'),
('H区02号'),
('H区03号'),
('H区04号'),
('H区05号'),
('H区06号'),
('H区07号'),
('H区08号'),
('H区09号'),
('H区10号'),
('H区11号'),
('H区12号');
