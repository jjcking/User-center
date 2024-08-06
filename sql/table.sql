create table user
(
    userRole     int      default 0                 not null comment '用户权限 0-普通用户 1-管理员',
    id           bigint auto_increment comment '主键'
        primary key,
    userName     varchar(256)                       null comment '用户昵称',
    userAccount  varchar(256)                       not null comment '登陆账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '用户性别',
    userPassword varchar(256)                       not null comment '用户密码',
    phone        varchar(20)                        null comment '用户电话',
    emial        varchar(30)                        null comment '用户邮箱',
    isValid      tinyint  default 0                 not null comment '是否有效',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除(逻辑删除)',
    planetCode   varchar(256)                       null comment '星球编号'
)
    comment '用户表';