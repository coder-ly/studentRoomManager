CREATE TABLE `tb_class`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `class_no`    int(11)   NOT NULL COMMENT '班级编号',
    `class_name`  varchar(200)                                                  DEFAULT NULL COMMENT '班级名称',
    `institute`   varchar(200)                                                  DEFAULT NULL COMMENT '所属学院',
    `grade`       varchar(200)                                                  DEFAULT NULL COMMENT '年级',
    `scroom`      varchar(200)                                                  DEFAULT NULL COMMENT '教室',
    `profession`  varchar(200)                                                  DEFAULT NULL COMMENT '专业',
    `classNum`    varchar(200)                                                  DEFAULT NULL COMMENT '班级人数',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='班级信息';


CREATE TABLE `tb_room`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `room_no`     int(11)   NOT NULL COMMENT '宿舍编号',
    `Shno`        varchar(200)                                                  DEFAULT NULL COMMENT '公寓楼号',
    `Spno`        varchar(200)                                                  DEFAULT NULL COMMENT '宿舍门牌号',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='宿舍信息';


  CREATE TABLE `tb_course`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `course_no`     int(11)   NOT NULL COMMENT '课程编号',
    `course_name`        varchar(200)                                                  DEFAULT NULL COMMENT '课程名称',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='课程信息表';

  CREATE TABLE `tb_score`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `course_no`   int(11)   NOT NULL COMMENT '课程编号',
    `student_no`  varchar(200)                                                  DEFAULT NULL COMMENT '学生编号',
    `trem`        varchar(200)                                                  DEFAULT NULL COMMENT '学期号',
    `score`       varchar(200)                                                  DEFAULT NULL COMMENT '成绩',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='成绩信息表';


  CREATE TABLE `tb_logistics`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `room_no`     int(11)   NOT NULL COMMENT '宿舍编号',
    `student_no`  varchar(200)                                                  DEFAULT NULL COMMENT '学生编号',
    `status`      varchar(3)                                                    DEFAULT NULL COMMENT '状态 0：已上报 1：维修中 2：维修完成',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上报备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='后勤维修表';


  CREATE TABLE `tb_school_card`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `card_no`     int(11)   NOT NULL COMMENT '校园卡编号',
    `student_no`  varchar(200)                                                  DEFAULT NULL COMMENT '学生编号',
    `password`    varchar(200)                                                  DEFAULT NULL COMMENT '校园卡密码',
    `balance`     decimal                                                       DEFAULT NULL COMMENT '余额',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上报备注',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='校园卡表';

  CREATE TABLE `tb_school_card_list`
(
    `id`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `card_no`     int(11)   NOT NULL COMMENT '校园卡编号',
    `balance`     decimal                                                       DEFAULT NULL COMMENT '消费金额',
    `comments`    varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '消费事由',
    `deleted`     int(1)    NOT NULL                                            DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL                                            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='校园卡消费表';


  CREATE TABLE `tb_information`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `title`       varchar(500) NOT NULL COMMENT '标题',
    `pub_user`    varchar(100) NOT NULL COMMENT '发布人',
    `user_no`     varchar(100) NOT NULL COMMENT '用户编号',
    `comments`    varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发布内容',
    `deleted`     int(1)       NOT NULL                                          DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp    NOT NULL                                          DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL                                          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='信息发布表';

  CREATE TABLE `tb_student_course`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `student_no`  varchar(100) NOT NULL COMMENT '学号',
    `course_no`   varchar(100) NOT NULL COMMENT '课程编号',
    `comments`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `deleted`     int(1)       NOT NULL                                         DEFAULT '0' COMMENT '是否删除,0否,1是',
    `create_time` timestamp    NOT NULL                                         DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL                                         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='学生选课表';