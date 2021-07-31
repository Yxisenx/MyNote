drop table if exists t_class_teacher;
drop table if exists t_student;
drop table if exists t_class;
drop table if exists t_teacher;

create table t_class
(
    id          int(16) auto_increment not null primary key,
    grade       int(4)                 not null,
    grade_order int(4)                 not null
) engine = innodb
  default charset = utf8;

create table t_student
(
    id           int(16) auto_increment not null primary key,
    name         varchar(16)            not null,
    age          int(3)                 not null,
    gender       varchar(6)             not null default 'man',
    phone_number varchar(15)            not null,
    address      varchar(64)            not null,
    class_id     int(16)                not null,
    foreign key (class_id) references t_class (id)
) engine = innodb
  default charset = utf8;

create table t_teacher
(
    id           int(16) auto_increment not null primary key,
    name         varchar(16)            not null,
    age          int(3)                 not null,
    gender       varchar(6)             not null default 'man',
    phone_number varchar(15)            not null,
    address      varchar(64)            not null,
    subject      varchar(32)            not null
) engine = innodb
  charset = utf8;

create table t_class_teacher
(
    teacher_id int(16) not null,
    class_id   int(16) not null,
    primary key (teacher_id, class_id),
    foreign key (teacher_id) references t_teacher (id),
    foreign key (class_id) references t_class (id)
) engine = innodb
  charset = utf8;

INSERT INTO `t_class`
VALUES (1, 1, 1);
INSERT INTO `t_class`
VALUES (2, 1, 2);
INSERT INTO `t_class`
VALUES (3, 1, 3);
INSERT INTO `t_class`
VALUES (4, 2, 1);
INSERT INTO `t_class`
VALUES (5, 2, 2);
INSERT INTO `t_class`
VALUES (6, 2, 3);
INSERT INTO `t_class`
VALUES (7, 3, 1);
INSERT INTO `t_class`
VALUES (8, 3, 2);
INSERT INTO `t_class`
VALUES (9, 3, 3);

INSERT INTO `t_teacher`
VALUES (1, '李一', 31, 'man', '123456789', '明月路太阳小区', '语文');
INSERT INTO `t_teacher`
VALUES (2, '李二', 28, 'man', '965654522', '明月路紫薇小区', '数学');
INSERT INTO `t_teacher`
VALUES (3, '李三', 29, 'woman', '564235462', '太阳路月亮小区', '英语');

INSERT INTO `t_class_teacher`
VALUES (1, 1);
INSERT INTO `t_class_teacher`
VALUES (2, 1);
INSERT INTO `t_class_teacher`
VALUES (1, 2);
INSERT INTO `t_class_teacher`
VALUES (2, 2);
INSERT INTO `t_class_teacher`
VALUES (3, 2);
INSERT INTO `t_class_teacher`
VALUES (1, 3);
INSERT INTO `t_class_teacher`
VALUES (3, 3);

INSERT INTO `t_student`
VALUES (1, '张三', 16, 'man', '13131313131', '明月路太阳小区', 1);
INSERT INTO `t_student`
VALUES (2, '李四', 15, 'man', '12312312312', '明月路紫薇小区', 1);
INSERT INTO `t_student`
VALUES (3, 'Rose', 15, 'woman', '12323232323', '明月路香榭小区', 1);
INSERT INTO `t_student`
VALUES (4, '杰克', 16, 'man', '23654236542', '太阳路月亮小区', 2);
