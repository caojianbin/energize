/*
name: 赋能 增量sql
version: 0.0.1
Date: 2019-11-09 17:40:48
*/
/**
新增用户账号表
 */
drop table if exists st_energize_admin_account;
create table st_energize_admin_account
(
   admin_id             int not null auto_increment comment '赋能管理员账号id',
   admin_name           varchar(20) comment '管理员姓名',
   admin_account        varchar(20) comment '管理员账号',
   admin_password       varchar(50) comment '管理员账号密码',
   admin_end_login_time timestamp comment '最后登录时间',
   st_salt              varchar(255) comment '随机UUID盐值',
   primary key (admin_id)
);

/**
新增赋能后台菜单表
 */
create table st_empowerment_admin_menu
(
   id                   integer not null auto_increment comment '自增ID',
   menu_id              varchar(20) not null comment '当下后台菜单ID',
   menu_title           varchar(20) comment '当下后台菜单标题',
   menu_url             varchar(20) comment '当下后台菜单链接',
   menu_code            varchar(20) comment '菜单code',
   menu_icon            varchar(20) comment '当下后台菜单小图标',
   menu_parent_code     varchar(20) comment '当下后台父级菜单code',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_admin_menu comment '赋能后台菜单表';

/**
新增赋能后台角色菜单中间表
 */
create table st_empowerment_admin_role_menu
(
   id                   integer not null auto_increment comment '自增ID',
   role_id              varchar(20) comment '角色ID',
   menu_id              varchar(20) comment '菜单ID',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_admin_role_menu comment '赋能后台角色菜单中间表';
/**
新增赋能后台角色表
 */
create table st_empowerment_role_table
(
   id                   integer not null auto_increment comment '自增ID',
   role_id              varchar(20) not null comment '角色ID',
   role_name            varchar(20) comment '角色名称',
   role_code            varchar(20) comment '角色代码',
   role_statu           varchar(20) comment '角色状态  关联业务字典表',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_role_table comment '赋能后台角色表';

/**
新增赋能后台用户角色中间表
 */
create table st_empowerment_user_role_table
(
   id                   integer not null auto_increment comment '自增ID',
   user_id              varchar(20) comment '用户编号',
   role_id              varchar(20) comment '角色ID',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_user_role_table comment '赋能后台用户角色中间表';
/**
新增赋能管理后台用户表
 */
create table st_empowerment_user_table
(
   id                   integer not null auto_increment comment '自增ID',
   user_id              varchar(20) not null comment '用户编号',
   user_user_name       varchar(20) comment '用户昵称',
   user_phone           varchar(20) comment '用户手机号,登录账号',
   user_pass_word       varchar(20) comment '用户登录密码',
   user_salt            varchar(20) comment '用户登录密码随机盐',
   user_statu           varchar(20) comment '用户状态   1-启用  2-禁用   3-删除
            关联业务字典表
            key :  user_statu
            value:   1-启用  2-禁用   3-删除',
   user_heard_img       varchar(100) comment '用户头像',
   user_lastLoginTime   varchar(20) comment '用户最后登陆时间',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_user_table comment '赋能管理后台用户表';
/**
新增赋能后台角色权限中间表
 */
create table st_empowerment_admin_role_permission
(
   id                   integer not null auto_increment comment '自增ID',
   role_id              varchar(20) comment '角色ID',
   permission_id        varchar(20) comment '权限ID',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_admin_role_permission comment '赋能后台角色权限中间表';
/**
新增赋能后台权限表
 */
create table st_empowerment_admin_permission
(
   id                   integer not null auto_increment comment '自增ID',
   permission_id        varchar(20) not null comment '权限ID',
   permission_name      varchar(20) comment '权限名称',
   permission_code      varchar(20) comment '权限代码',
   permission_statu     varchar(20) comment '权限状态   关联业务字典表',
   permission_parent_code varchar(20) comment '权限父类code',
   create_time          varchar(20) comment '记录创建时间',
   create_name          varchar(20) comment '记录创建人',
   update_time          varchar(20) comment '记录修改时间',
   update_name          varchar(20) comment '记录修改人',
   primary key (id)
);

alter table st_empowerment_admin_permission comment '赋能后台权限表';


