[TOC]



# MySQL基础

数据库(DataBase, 简称DB)：按照数据结构来组织、存储和管理数据的仓库。

> 一个长期存储在计算机内的、有组织的、可共享的、统一管理的大量数据的集合

关系型数据库

>关系型数据库，是指采用了关系模型来组织数据的数据库，其以行和列的形式存储数据，以便于用户理解，关系型数据库这一系列的行和列被称为表，一组表组成了数据库。用户通过查询来检索数据库中的数据，而查询是一个用于限定数据库中某些区域的执行代码。关系模型可以简单理解为二维表格模型，而一个关系型数据库就是由二维表及其之间的关系组成的一个数据组织。
>
> 
>
>主流的关系型数据库有Oracle、DB2、MySQL、Microsoft SQL Server、Microsoft Access 、SQLite...

## SQL

### 一. 定义

Structured Query Language：结构化查询语言



> 一种数据库查询和程序设计语言，用于存取数据以及查询、更新和管理关系数据库系统



### 二. 分类

1. DDL(Data Definition Language)数据定义语言

   > 用来定义数据库对象：数据库，表，列等。关键字：create, drop,alter 等

2. DML(Data Manipulation Language)数据操作语言

   > 用来对数据库中表的数据进行增删改。关键字：insert, delete, update 等

3. DQL(Data Query Language)数据查询语言

   > 用来查询数据库中表的记录(数据)。关键字：select, where 等

4. DCL(Data Control Language)数据控制语言(了解)

   > 用来定义数据库的访问权限和安全级别，及创建用户。关键字：GRANT， REVOKE 等



### 三. DDL 操作数据库、表 CRUB

#### 1. 操作数据库

##### C( Create) 创建

* 创建数据库：

  ```sql
  CREATE DATABASE 数据库名称;
  ```

* 创建数据库判断存在

  ```sql
  CREATE DATABASE IF NOT EXISTS 数据库名称;
  ```

* 创建数据库，并指定字符集

  ```sql
  CREATE DATABASE 数据库名称 CHARACTER SET 字符集名称;
  ```

##### R( Retrieve) 查询

 * 查询所有数据库的名称

   ```sql
   SHOW DATABASES;
   ```

 * 查询某个数据库的字符集:查询某个数据库的创建语句

   ```sql
   SHOW CREATE DATABASE 数据库名称;
   ```



##### U( Update):修改

  * 修改数据库的字符集

    ```sql
    ALTER DATABASE 数据库名称 CHARACTER SET 字符集名称
    ```

##### D( Delete):删除

		*  删除数据库
	
	```sql
	DROP DATABASE 数据库名称;
	```

  * 判断数据库存在，存在再删除

    ```sql
    DROP DATABASE IF EXISTS 数据库名称;
    ```

##### 使用数据库

* 查询当前正在使用的数据库名称

  ```sql
  SELECT DATABASE();
  ```

* 使用数据库

  ```sql
  USE 数据库名称;
  ```

#### 2. 操作表

##### C( Create) 创建

1. 语法：

   ```sql
   CREATE TABLE 表名(
   	列名1 数据类型1,
       列名2 数据类型2,
       列名3 数据类型3,
       ....
       列名n 数据类型n
   )
   ```

   * 注意：最后一列不加逗号(**,**)

2. 数据类型：

   1. INT: 整数类型

      ```sql
      age INT(3)
      ```

   2. DOUBLE: 小数类型

      ```sql
      score DOUBLE(5,2)
      ```

   3. DTAE: 日期，只包含年月日，yyyy-MM-dd

   4. DATETIME: 日期，包含年月日是封面, yyyy-MM-dd HH:mm:ss

   5. TIMESTAMP: 时间戳类型	减去1970年1月1日 0时0分0秒 的毫秒值

      * 如果将来不给这个字段赋值，或赋值为null，则默认使用当前的系统时间，来自动赋值

   6. VARCHAR: 字符串

      ```sql
      CREATE TABLE STUDENT(
      	id INT,
          name VARCHAR(32),
          age INT(3),
          score DOUBLE(4,1),
          birthday DATE,
          insert_time TIMESTAMP
      )
      ```

##### R( Retrieve)：查询

* 查询某个数据库中所有的表名称

  ```sql
  SHOW TABLES;
  ```

* 查询表结构

  ```sql
  DESC 表名;
  ```

##### U( Update): 修改

1. 修改表名

   ```sql
   ALTER TABLE 表名 RENAME TO 新的表名;
   ```

2. 修改表的字符集

   ```sql
   ALTER TABLE 表名 CHARACTER SET 字符集名称;
   ```

3. 添加一列

   ```sql
   ALTER TABLE 表名 ADD 列名 数据类型;
   ```

4. 修改列

   ```sql
   ALTER TABLE 表名 CHANGE 列名 新列名 新数据类型;
   ALTER TABLE 表名 MODIFY 列名 新数据类型
   ```

5. 删除列

   ```sql
   ALTER TABLE 表名 DROP 列名;
   ```

##### D( Delete): 删除

```sql
DROP TABLE 表名;
DROP TABLE IF EXISTS 表名;
```



------



### 四. DML 增删改表中数据

#### 1. 添加数据

语法：

```sql
INSERT INTO 表名(列名1, 列名2, ..., 列名n) VALUES(值1, 值2, ..., 值n);
INSERT INTO VALUES(值1, 值2, ..., 值n);
```

注意：

1. 第一种添加方式列名与值要一一对应；
2. 如果表名后不定义列名，则默认给所有列添加值；
3. 除了数字类型，其他类型均需要用引号（单双均可）括起来

#### 2. 删除数据

语法：

```sql
DELETE FROM 表名 [WHERE 条件];
```

注意： 

1. 如果不加条件，则删除表中所有记录。

2. 如果要删除所有记录，推荐使用

   ```sql
   TRUNCATE TABLE 表名; -- 推荐使用，效率更高 先删除表，然后再创建一张一样的表。
   ```

#### 3. 修改数据

语法：

```sql
UPDATE 表名 SET 列名1 = 值1, 列名2 = 值2, ... [WHERE 条件];
```

注意：

1. 如果不加任何条件，则会将表中所有记录全部修改。



------



### 五. DQL 查询表中数据

```sql
SELECT * FROM 表名; -- 查询所有数据
```

#### 1. 语法

```sql
SELECT
	字段列表
FROM
	表名列表
GROUP BY
	分组字段
HAVING
	分组后的条件
ORDER BY
	排序
LIMIT
	分页限定
```

#### 2. 基础查询

1. 多个字段的查询

   ```sql
   SELECT 字段名1，字段名2... FROM 表名;
   -- 如果查询所有字段可以用*来代替字段列表
   ```

2. 去除重复

   ```sql
   DISTINICT
   ```

3. 计算列

   * 一般可以使用四则运算计算一些列的值。（一般只会进行数值型的计算）

   * NULL参与的运算，计算值都为NULL

   * ```SQL
     IFNULL(expr1, expr2)
     ```

     如果expr1不是NULL，IFNULL()返回expr1，否则它返回expr2

4. 起别名

   ```sql
   gender VARCHAR(5) as sex; 
   ```

#### 3. 条件查询 WHERE

```sql
WHERE 条件
```

1. 运算符

   ```sql
   >、< 、<= 、>= 、= 、<> -- <>为不等于
   BETWEEN...AND  
   IN(集合) 
   LIKE： 语句 -- 模糊查询。
   		   -- 占位符：
   		   -- _:单个任意字符
   		   -- %：多个任意字符
   IS NULL
   AND -- 或 &
   OR -- 或 ||
   NOT -- 或！
   ```

2. 示例

   ```sql
   -- 查询年龄大于20岁
   SELECT * FROM student WHERE age > 20;
   SELECT * FROM student WHERE age >= 20;
   
   -- 查询年龄等于20岁
   SELECT * FROM student WHERE age = 20;
   
   -- 查询年龄不等于20岁
   SELECT * FROM student WHERE age != 20;
   SELECT * FROM student WHERE age <> 20;
   
   -- 查询年龄大于等于20 小于等于30
   SELECT * FROM student WHERE age >= 20 &&  age <=30;
   SELECT * FROM student WHERE age >= 20 AND  age <=30;
   SELECT * FROM student WHERE age BETWEEN 20 AND 30;
   
   -- 查询年龄22岁，18岁，25岁的信息
   SELECT * FROM student WHERE age = 22 OR age = 18 OR age = 25
   SELECT * FROM student WHERE age IN (22,18,25);
   
   -- 查询英语成绩为null
   SELECT * FROM student WHERE english IS NULL;
   
   -- 查询英语成绩不为null
   SELECT * FROM student WHERE english  IS NOT NULL;
   ```

#### 4. 排序查询 ORDER BY

1. 语法：

   ```sql
   ORDER BY 排序字段1 排序方式1 ，  排序字段2 排序方式2...
   ```

2. 排序方式：

   * ASC: 升序，默认值；
   * DESC: 降序

**注意**：如果有多个排序条件，则当前边的条件值一样时，才会判断第二条件。

#### 5. 聚合函数

```sql
COUNT -- 计算个数，1. 一般选择主键或非空列；
			  -- 2. COUNT(*)
MAX -- 计算最大值
MIN -- 计算最小值
SUM -- 计算和
AVG -- 计算平均值
```

> **注意：**聚合函数的计算应排除NULL值
>
> 解决方法：
>
>    			1. 选择不包含非空的列进行计算
>    	        			2. IFNULL函数

#### 6. 分组查询 GROUP BY

1. 语法：

```sq
GROUP BY 分组字段;
```

> **注意：**
>
>  1. 分组之后查询的字段：分组字段、聚合函数
>
>  2. WHERE 和 HAVING 的区别:
>
>      1. WHERE在分组之前进行限定，如果不满足条件，则不参与分组。HAVING在分组之后进行限定，如果不满足结果，则不会被查询出来
>
>      2. WHERE 后不可以跟聚合函数，HAVING可以进行聚合函数的判断。
>
>         example：
>
>         ```sql
>         --  按照性别分组。分别查询男、女同学的平均分,人数 要求：分数低于70分的人，不参与分组,分组之后。人数要大于2个人
>         SELECT 
>         	sex , AVG(math),COUNT(id) 
>         FROM 
>         	student 
>         WHERE 
>         	math > 70 
>         GROUP BY 
>         	sex 
>         HAVING 
>         	COUNT(id) > 2;
>         ```

#### 7. 分页查询 LIMIT

LIMIT为MySQL的*方言*，Oracle为ROWNUM

1. 语法

   ```sql
   LIMIT start_index, number; -- limit 开始的索引,每页查询的条数;
   ```

2. 公式： 开始的索引 = （当前的页码 - 1） * 每页显示的条数

   ```sql
   -- 每页显示3条记录 
   SELECT * FROM student LIMIT 0,3; -- 第1页
   SELECT * FROM student LIMIT 3,3; -- 第2页
   SELECT * FROM student LIMIT 6,3; -- 第3页
   ```

***



### 六. DCL 管理用户，授权

#### 1. 添加用户

```sql
CREATE 
	USER '用户名'@'主机名' IDENTIFIED 
BY 
	'密码';
```



#### 2. 删除用户

```sql
DROP 
	USER '用户名'@'主机名';
```



#### 3. 修改用户密码

```sql
UPDATE 
	USER 
SET 
	PASSWORD = PASSWORD('新密码') 
WHERE 
	USER = '用户名';
```



#### 4. MySQL忘记root密码

1. 停止mysql服务

2. 使用无验证方式启动mysql服务： mysqld --skip-grant-tables

3. 打开新的命令窗口 直接输入mysql : 

   ```sql
   USE mysql;
   ```

   ```sql
   UPDATE 
   	USER 
   SET 
   	PASSWORD = PASSWORD('你的新密码') 
   WHERE 
   	USER = 'root';
   ```

4. 结束mysql服务
5. 使用新密码登录

#### 5. 查询用户

```sql
USE mysql;
SELECT * FROM USER;
```

'root'@'%'

通配符： % 表示可以在任意主机使用用户登录数据库

#### 6. 权限管理

1. 查询权限

```sql
-- 查询权限
SHOW GRANTS FOR '用户名'@'主机名';
SHOW GRANTS FOR 'lisi'@'%';
```

2. 授予权限

```sql
-- 授予权限
GRANT 权限列表 ON 数据库名.表名 TO '用户名'@'主机名';

-- 给张三用户授予所有权限，在任意数据库任意表上
GRANT ALL ON *.* TO 'zhangsan'@'localhost';
```

3. 撤销权限

```sql
-- 撤销权限：
REVOKE 权限列表 ON 数据库名.表名 FROM '用户名'@'主机名';
REVOKE UPDATE ON db3.`account` FROM 'lisi'@'%';
```

#### 7. 权限列表

| **权 限**                   | **作用范围**         | **作 用**                     |
| :-------------------------- | :------------------- | :---------------------------- |
| **all**                     | 服务器               | 所有权限                      |
| **select**                  | 表、列               | 选择行                        |
| **insert**                  | 表、列               | 插入行                        |
| **update**                  | 表、列               | 更新行                        |
| **delete**                  | 表                   | 删除行                        |
| **create**                  | 数据库、表、索引     | 创建                          |
| **drop**                    | 数据库、表、视图     | 删除                          |
| **reload**                  | 服务器               | 允许使用flush语句             |
| **shutdown**                | 服务器               | 关闭服务                      |
| **process**                 | 服务器               | 查看线程信息                  |
| **file**                    | 服务器               | 文件操作                      |
| **grant option**            | 数据库、表、存储过程 | 授权                          |
| **references**              | 数据库、表           | 外键约束的父表                |
| **index**                   | 表                   | 创建/删除索引                 |
| **alter**                   | 表                   | 修改表结构                    |
| **show databases**          | 服务器               | 查看数据库名称                |
| **super**                   | 服务器               | 超级权限                      |
| **create temporary tables** | 表                   | 创建临时表                    |
| **lock tables**             | 数据库               | 锁表                          |
| **execute**                 | 存储过程             | 执行                          |
| **replication client**      | 服务器               | 允许查看主/从/二进制日志状态  |
| **replication slave**       | 服务器               | 主从复制                      |
| **create view**             | 视图                 | 创建视图                      |
| **show view**               | 视图                 | 查看视图                      |
| **create routine**          | 存储过程             | 创建存储过程                  |
| **alter routine**           | 存储过程             | 修改/删除存储过程             |
| **create user**             | 服务器               | 创建用户                      |
| **event**                   | 数据库               | 创建/更改/删除/查看事件       |
| **trigger**                 | 表                   | 触发器                        |
| **create tablespace**       | 服务器               | 创建/更改/删除表空间/日志文件 |
| **proxy**                   | 服务器               | 代理成为其它用户              |
| **usage**                   | 服务器               | 没有权限                      |



***



### 七. 约束

> 对表中的数据进行限定，保证数据的正确性、有效性和完整性。

分类：

1. 主键约束： PRIMARY KEY
2. 非空约束： NOT NULL
3. 唯一约束： UNIQUE
4. 外键约束： FOREIFN KEY

#### 1. 非空约束 NOT NULL

> 某一列的值不能为NULL

1. 创建表时添加约束

```sql
CREATE TABLE stu(
	id INT,
	NAME VARCHAR(20) NOT NULL -- name为非空
);
```

2. 创建表后，添加非空约束

```sql
ALTER TABLE stu MODIFY NAME VARCHAR(20) NOT NULL;
```

3. 删除name的非空约束

```sql
ALTER TABLE stu MODIFY NAME VARCHAR(20);
```

#### 2. 唯一约束 UNIQUE

> 某一列的值不能重复

唯一约束可以有NULL值，但是只能有<span style="color:red; font-size:18px">一条</span>记录为NULL

1. 创建表时添加唯一约束

```sql
CREATE TABLE stu(
	id INT,
	phone_number VARCHAR(20) UNIQUE -- 手机号
);
```

2. 表创建后，添加唯一约束

```sql
ALTER TABLE stu MODIFY phone_number VARCHAR(20) UNIQUE;
```

3. 删除唯一约束

```sql
ALTER TABLE stu DROP INDEX phone_number;
```

#### 3. 主键约束  PRIMARY KEY

> **注意：**
>
> 1. 主键非空且唯一
> 2. 一张表只能有一个字段作为主键
> 3. 主键就是表中记录的唯一标识

1. 在创建表时添加主键约束

```sql
CREATE TABLE stu(
	id INT PRIMARY KEY,
	name VARCHAR(20) -- 手机号
);
```

2. 创建表后，添加主键

```sql
ALTER TABLE stu MODIFY id INT PRIMARY KEY;
```

3. 删除主键

```sql
ALTER TABLE stu DROP PRIMARY KEY;
```

##### 自动增长 AUTO_INCREMENT

> 如果某一列是数值类型的，使用 AUTO_INCREMENT 可以来完成值得自动增长

1. 在创建表时，添加主键约束，并且完成主键自增长

```sql
CREATE TABLE stu(
	id INT PRIMARY KEY AUTO_INCREMENT,-- 给id添加主键约束
    name VARCHAR(20)
);
```

2. 创建表后，添加自动增长

```sql
ALTER TABLE stu MODIFY id INT AUTO_INCREMENT;
```

3. 删除自动增长

```sql
ALTER TABLE stu MODIFY id INT;
```

#### 4. 外键约束FOREIGN KEY

1. 创建表时，添加外键

```sql
CREATE TABLE 表名(
	...
    外键列
    CONSTRAINT 外键名称 FOREIGN KEY (外键列名称) REFERENCES 主表名称(主列表名称)
);
```

2. 创建表之后，添加外键

```sql
ALTER TABLE 表名 ADD CONSTRAINT 外键名称 
					FOREIGN KEY (外键字段名称) REFERENCES 主表名称(主表列名称);
```

3. 删除外键

```sql
ALTER TABLE 表名 DROP FOREIGN KEY 外键名称;
```

4. 级联操作

   1. 添加级联

   ```sql
   ALTER TABLE 表名 ADD CONSTRAINT 外键名称 
   					FOREIGN KEY (外键字段名称) REFERENCES 主表名称(主表列名称) ON UPDATE CASCADE ON DELETE CASCADE ;
   ```

   2. 分类

      * 级联更新：ON UPDATE CASCADE

      * 级联删除：ON DELETE CASCADE 



***



### 八. 数据库设计

#### 1. 多表之间的关系

##### a. 分类

1. 一对一
   * 如：人与身份证
   * 一个人只有一个身份证，一个身份证只能对应一个人
2. 一对多||多对一
   * 如：部门和员工
   * 一个部门有多个员工，一个员工只能对应一个部门
3. 多对多
   * 如：学生和课程
   * 一个学生可以选择很多门课程，一个课程也可以被很多学生选择

##### b. 实现关系

1. 一对多||多对一
   * 在多的一方建立外键，指向一的一方的主键。
2. 多对多
   * 多对多关系实现需要借助第三张中间表。中间表至少包含两个字段，这两个字段作为第三张表的外键，分别指向两张表的主键
3. 一对一（很少见，了解）
   * 一对一关系实现，可以在任意一方添加唯一外键指向另一方的主键。

##### c. 案例

```sql
-- 创建旅游线路分类表 tab_category
CREATE TABLE tab_category (
	cid INT PRIMARY KEY AUTO_INCREMENT,-- 旅游线路分类主键，自动增长
	cname VARCHAR(100) NOT NULL UNIQUE -- 旅游线路分类名称非空，唯一，字符串 100
);

-- 创建旅游线路表 tab_route
CREATE TABLE tab_route(
    rid INT PRIMARY KEY AUTO_INCREMENT, -- 旅游线路主键，自动增长
    rname VARCHAR(100) NOT NULL UNIQUE, -- 旅游线路名称非空，唯一，字符串 100
    price DOUBLE, -- 价格
    rdate DATE, -- 上架时间，日期类型
    cid INT, -- 外键，所属分类
	FOREIGN KEY (cid) REFERENCES tab_category(cid)
);

-- 创建用户表 tab_user
CREATE TABLE tab_user (
	uid INT PRIMARY KEY AUTO_INCREMENT, -- 用户主键，自增长
	username VARCHAR(100) UNIQUE NOT NULL, -- 用户名长度 100，唯一，非空
	PASSWORD VARCHAR(30) NOT NULL, -- 密码长度 30，非空
    NAME VARCHAR(100), -- 真实姓名长度 100
    birthday DATE, -- 生日
    sex CHAR(1) DEFAULT '男', -- 性别，定长字符串 1
    telephone VARCHAR(11), -- 手机号，字符串 11
    email VARCHAR(100) -- 邮箱，字符串长度 100
);

-- 创建收藏表 tab_favorite
	rid INT, -- 线路id,外键
	DATE DATETIME,
	uid INT, -- 用户id，外键
	-- 创建复合主键
	PRIMARY KEY(rid,uid), -- 联合主键
	FOREIGN KEY (rid) REFERENCES tab_route(rid),
	FOREIGN KEY(uid) REFERENCES tab_user(uid)
);
```

#### 2. 数据库设计范式

>​		设计数据库时，需要遵循的一些规范。要遵循后边的范式要求，必须先遵循前边的所有范式要求
>
>​		设计关系数据库时，遵从不同的规范要求，设计出合理的关系型数据库，这些不同的规范要求被称为不同的范式，各种范式呈递次规范，越高的范式数据库冗余越小。
>
>​		目前关系数据库有六种范式：**第一范式（1NF）**、**第二范式（2NF）**、**第三范式（3NF）**、巴斯-科德范式（BCNF）、第四范式(4NF）和第五范式（5NF，又称完美范式）。
>
>
>
>一般情况下数据库设计满足1NF, 2NF, 3NF即可

##### 第一范式 1NF

> 每一列都是不可分割的原子数据项

##### 第二范式 2NF

> 在1NF的基础上，非码属性必须完全依赖于码（在1NF基础上消除非主属性对主码的部分函数依赖）

1. *函数依赖*：A-->B,如果通过A属性(属性组)的值，可以确定唯一B属性的值。则称B依赖于A
   * 例如：学号-->姓名。  （学号，课程名称） --> 分数

2. *完全函数依赖*：A-->B， 如果A是一个属性组，则B属性值的确定需要依赖于A属性组中所有的属性值。
   * 例如：（学号，课程名称） --> 分数
3. *部分函数依赖*：A-->B， 如果A是一个属性组，则B属性值的确定只需要依赖于A属性组中某一些值即可。
   * 例如：（学号，课程名称） -- > 姓名
4. *传递函数依赖*：A-->B, B -- >C . 如果通过A属性(属性组)的值，可以确定唯一B属性的值，在通过B属性（属性组）的值可以确定唯一C属性的值，则称 C 传递函数依赖于A
   * 例如：学号-->系名，系名-->系主任
5. ***码***：如果在一张表中，一个属性或属性组，被其他所有属性所完全依赖，则称这个属性(属性组)为该表的码
   * 主属性：码属性组中的所有属性
   * 非主属性：除过码属性组的属性

##### 第三范式 3NF

在2NF基础上，任何非主属性不依赖于其它非主属性（在2NF基础上消除传递依赖）



***



### 九. 数据库的备份和还原

命令行：

* 备份： mysql dump -u用户名 -p密码 数据库名称 > 保存的路径

* 还原： 
  1. 登录数据库
  2. 创建数据库
  3. 使用数据库
  4. 执行文件 source 文件路径



***



### 十. 多表查询

在不添加约束的情况下，多表查询的结果为*笛卡尔积*。

>笛卡尔积：
>
>* 在数学中，两个集合X和Y的笛卡尔积（Cartesian product），又称直积，表示为X × Y，第一个对象是X的成员而第二个对象是Y的所有可能有序对的其中一个成员  。

因此：要完成多表查询首先要添加约束，消除无用数据

#### 1. 内连接查询

1. 查询表
2. 约束
3. 查询字段

##### 隐式内连接查询

语法：

```sql
SELECT 
	查询字段列表
FROM
	表名列表
WHERE
	约束条件
```



例子：

```sql
-- 查询所有员工信息和对应的部门信息
SELECT * FROM emp,dept WHERE emp.dept_id = dept.id;

-- 查询员工表的名称，性别。部门表的名称
SELECT emp.name,emp.gender,dept.name FROM emp,dept WHERE emp.dept_id = dept.id;
-- ^另一种写法
SELECT 
	t1.name, -- 员工表的姓名
	t1.gender,-- 员工表的性别
	t2.name -- 部门表的名称
FROM
	emp t1,
	dept t2
WHERE 
	t1.dept_id = t2.id;
```

##### 显式内连接查询

语法：

```sql
SELECT
	字段列表
FROM
	表1 [INNER] 
JOIN 
	表2
ON 
	条件
```

[INNER] -- 中括号表示其中的INNER可省略

例子：

```sql
SELECT 
	* 
FROM 
	emp
JOIN 
	dept 
ON 
	emp.dept_id = dept.id;
```



#### 2.外连接查询

##### 左外连接查询

语法：

```sql
SELECT
	字段列表
FROM
	表1
LEFT [OUTER] JOIN
	表2
ON
	条件
```

查询的是左表所有数据以及两表交集部分。



例子：

```sql
-- 查询所有员工信息，如果员工有部门，则查询部门名称，没有部门，则不显示部门名称
SELECT 
	t1.*,t2.name 
FROM 
	emp t1 
LEFT JOIN 
	dept t2 
ON 
	t1.dept_id = t2.id;
```



##### 右外连接查询

语法：

```sql
SELECT
	字段列表
FROM
	表1
RIGHT [OUTER] JOIN
	表2
ON
	条件
```

查询的是右表所有数据以及两表交集部分。

#### 3. 子查询

查询中嵌套查询，称嵌套查询为子查询

##### 子查询结果为单行单列

子查询可以作为条件，使用运算符去判断。

```sql
-- 查询员工工资小于平均工资的人
SELECT 
	* 
FROM 
	emp 
WHERE 
	emp.salary < (SELECT AVG(salary) FROM emp);
```

##### 子查询的结果为多行单列

* 子查询可以作为条件，使用运算符in来判断

```sql
-- 查询'财务部'和'市场部'所有的员工信息
SELECT 
	* 
FROM 
	emp 
WHERE 
	dept_id 
IN 
	(SELECT id FROM dept WHERE NAME = '财务部' OR NAME = '市场部');
```

##### 子查询的结果为多行多列

* 子查询可以作为一张虚拟表参与查询

```sql
-- 查询员工入职日期是2011-11-11日之后的员工信息和部门信息
SELECT 
	* 
FROM 
	dept t1 ,
	(SELECT * FROM emp WHERE emp.`join_date` > '2011-11-11') t2
WHERE 
	t1.id = t2.dept_id;
```

#### 4.多表查询综合练习

创建表

```sql
-- 创建表
-- 部门表
CREATE TABLE dept (
  id INT PRIMARY KEY PRIMARY KEY, -- 部门id
  dname VARCHAR(50), -- 部门名称
  loc VARCHAR(50) -- 部门所在地
);

-- 添加4个部门
INSERT INTO dept(id,dname,loc) VALUES 
(10,'教研部','北京'),
(20,'学工部','上海'),
(30,'销售部','广州'),
(40,'财务部','深圳');



-- 职务表，职务名称，职务描述
CREATE TABLE job (
  id INT PRIMARY KEY,
  jname VARCHAR(20),
  description VARCHAR(50)
);

-- 添加4个职务
INSERT INTO job (id, jname, description) VALUES
(1, '董事长', '管理整个公司，接单'),
(2, '经理', '管理部门员工'),
(3, '销售员', '向客人推销产品'),
(4, '文员', '使用办公软件');



-- 员工表
CREATE TABLE emp (
  id INT PRIMARY KEY, -- 员工id
  ename VARCHAR(50), -- 员工姓名
  job_id INT, -- 职务id
  mgr INT , -- 上级领导
  joindate DATE, -- 入职日期
  salary DECIMAL(7,2), -- 工资
  bonus DECIMAL(7,2), -- 奖金
  dept_id INT, -- 所在部门编号
  CONSTRAINT emp_jobid_ref_job_id_fk FOREIGN KEY (job_id) REFERENCES job (id),
  CONSTRAINT emp_deptid_ref_dept_id_fk FOREIGN KEY (dept_id) REFERENCES dept (id)
);

-- 添加员工
INSERT INTO emp(id,ename,job_id,mgr,joindate,salary,bonus,dept_id) VALUES 
(1001,'孙悟空',4,1004,'2000-12-17','8000.00',NULL,20),
(1002,'卢俊义',3,1006,'2001-02-20','16000.00','3000.00',30),
(1003,'林冲',3,1006,'2001-02-22','12500.00','5000.00',30),
(1004,'唐僧',2,1009,'2001-04-02','29750.00',NULL,20),
(1005,'李逵',4,1006,'2001-09-28','12500.00','14000.00',30),
(1006,'宋江',2,1009,'2001-05-01','28500.00',NULL,30),
(1007,'刘备',2,1009,'2001-09-01','24500.00',NULL,10),
(1008,'猪八戒',4,1004,'2007-04-19','30000.00',NULL,20),
(1009,'罗贯中',1,NULL,'2001-11-17','50000.00',NULL,10),
(1010,'吴用',3,1006,'2001-09-08','15000.00','0.00',30),
(1011,'沙僧',4,1004,'2007-05-23','11000.00',NULL,20),
(1012,'李逵',4,1006,'2001-12-03','9500.00',NULL,30),
(1013,'小白龙',4,1004,'2001-12-03','30000.00',NULL,20),
(1014,'关羽',4,1007,'2002-01-23','13000.00',NULL,10);



-- 工资等级表
CREATE TABLE salarygrade (
  grade INT PRIMARY KEY,   -- 级别
  losalary INT,  -- 最低工资
  hisalary INT -- 最高工资
);

-- 添加5个工资等级
INSERT INTO salarygrade(grade,losalary,hisalary) VALUES 
(1,7000,12000),
(2,12010,14000),
(3,14010,20000),
(4,20010,30000),
(5,30010,99990);

-- 需求：

-- 1.查询所有员工信息。查询员工编号，员工姓名，工资，职务名称，职务描述

-- 2.查询员工编号，员工姓名，工资，职务名称，职务描述，部门名称，部门位置
   
-- 3.查询员工姓名，工资，工资等级

-- 4.查询员工姓名，工资，职务名称，职务描述，部门名称，部门位置，工资等级

-- 5.查询出部门编号、部门名称、部门位置、部门人数
 
-- 6.查询所有员工的姓名及其直接上级的姓名,没有领导的员工也需要查询
```

查询：

```sql
-- 1.查询所有员工信息。查询员工编号，员工姓名，工资，职务名称，职务描述
SELECT
	t1.id 员工编号,
	t1.ename 姓名,
	t1.salary 工资,
	t2.jname 职务名称,
	t2.description 职务描述 
FROM
	emp t1,
	job t2 
WHERE
	t1.job_id = t2.id;

-- 2.查询员工编号，员工姓名，工资，职务名称，职务描述，部门名称，部门位置
SELECT
	t1.id 员工编号,
	t1.ename 姓名,
	t1.salary 工资,
	t2.jname 职务名称,
	t2.description 职务描述,
	t3.dname 部门名称,
	t3.loc 部门位置 
FROM
	emp t1,
	job t2,
	dept t3 
WHERE
	t1.job_id = t2.id 
	AND t1.dept_id = t3.id 
ORDER BY
	t1.id;

-- 3.查询员工姓名，工资，工资等级
SELECT
	t1.ename 员工姓名,
	t1.salary 工资,
	t2.grade 工资等级 
FROM
	emp t1,
	salarygrade t2 
WHERE
	t1.salary BETWEEN t2.losalary 
	AND t2.hisalary 
ORDER BY
	t2.grade,
	t1.salary;

-- 4.查询员工姓名，工资，职务名称，职务描述，部门名称，部门位置，工资等级
SELECT
	t1.ename 员工姓名,
	t1.salary 员工工资,
	t2.jname 职务名称,
	t2.description 职务描述,
	t3.dname 部门名称,
	t3.loc 部门位置,
	t4.grade 工资等级 
FROM
	emp t1,
	job t2,
	dept t3,
	salarygrade t4 
WHERE
	t1.job_id = t2.id 
	AND t1.dept_id = t3.id 
	AND t1.salary BETWEEN t4.losalary 
	AND t4.hisalary 
ORDER BY
	t3.dname,
	t4.grade,
	t1.salary;

-- 5.查询出部门编号、部门名称、部门位置、部门人数
SELECT
	t1.dname 部门名称,
	t1.id 部门编号,
	t1.loc 部门位置,
	t2.number 部门人数 
FROM
	dept t1,
	( SELECT dept_id, COUNT( id ) number FROM emp GROUP BY dept_id ) t2 
WHERE
	t1.id = t2.dept_id 
ORDER BY
	t1.id;

-- 6.查询所有员工的姓名及其直接上级的姓名,没有领导的员工也需要查询
SELECT
	t1.ename 员工姓名,
	t2.ename 上级领导姓名 
FROM
	emp t1
	LEFT JOIN emp t2 ON t1.mgr = t2.id;
```

### 十一. 事物

[事务](https://www.runoob.com/mysql/mysql-transaction.html "RUNOOB.com MySQL事物")主要用于处理操作量大，复杂度高的数据。

> 比如说，在人员管理系统中，你删除一个人员，你既需要删除人员的基本资料，也要删除和该人员相关的信息，如信箱，文章等等，这样，这些数据库操作语句就构成一个事务！

* 在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务。
* 事务处理可以用来维护数据库的完整性，保证成批的 SQL 语句要么全部执行，要么全部不执行。
* 事务用来管理 insert,update,delete 语句
* MySQL数据库中事务默认提交

#### 1. 事务必须满足的4个条件（ACID）
1. 原子性（**A**tomicity，或称不可分割性）——不可分割的最小操作单位，要么同时成功，要么同时失败

  * 一个事务（transaction）中的所有操作，<span style="color:red">要么全部完成，要么全部不完成</span>，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。
2. 一致性（**C**onsistency）——事务操作执行与预设相符

  * 在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。
3. 隔离性（**I**solation，又称独立性）——多个事务之间相互独立。

  * 数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。
4. 持久性（**D**urability）——当事务提交或回滚后，数据库会持久化的保存数据。

  * 事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。

#### 2. 事物的隔离级别

##### 事务隔离的不同级别

1. Read uncommitted 读未提交
   * 产生的问题：脏读、不可重复读、幻读

2. Read committed 读提交（Oracle默认）
   * 产生的问题：不可重复读、幻读
3. Repeatable read 可重复读(MySQL默认)
   * 产生的问题：幻读

4. Serializable 串行化 
   * 可以解决所有的问题

> 隔离级别从小到大安全性越来越高，但是效率越来越低

* 数据库查询隔离级别：

```sql
SELECT @@tx_isolation;
```

* 数据库设置隔离级别

```sql
SET GLOBAL TRANSACTION ISOLATION LEVEL  级别字符串;
```



#### 3.事务提交的两种方式

```sql
-- 添加数据
INSERT INTO account (NAME, balance) VALUES ('zhangsan', 1000), ('lisi', 1000);
-- 张三给李四转账 500 元
		
-- 0. 开启事务
START TRANSACTION;
-- 1. 张三账户 -500
		
UPDATE account SET balance = balance - 500 WHERE NAME = 'zhangsan';
-- 2. 李四账户 +500
-- 出错了...
UPDATE account SET balance = balance + 500 WHERE NAME = 'lisi';
		
-- 发现执行没有问题，提交事务
COMMIT;
		
-- 发现出问题了，回滚事务
ROLLBACK;
```



##### 自动提交

* MySQL就是自动提交的
* 一条DML(增删改)语句会自动提交一次事务。

##### 手动提交

* Oracle 数据库默认是手动提交事务
* 需要先开启事务，再提交

##### 修改事务的默认提交方式

* 查看事务的默认提交方式：SELECT @@autocommit; -- 1 代表自动提交  0 代表手动提交
* 修改默认提交方式： set @@autocommit = 0;



***



### 十二. 附录（MySQL数据类型）

#### 1. 数值类型

MySQL支持所有标准SQL数值数据类型。

这些类型包括严格数值数据类型(INTEGER、SMALLINT、DECIMAL和NUMERIC)，以及近似数值数据类型(FLOAT、REAL和DOUBLE PRECISION)。

关键字INT是INTEGER的同义词，关键字DEC是DECIMAL的同义词。

BIT数据类型保存位字段值，并且支持MyISAM、MEMORY、InnoDB和BDB表。

作为SQL标准的扩展，MySQL也支持整数类型TINYINT、MEDIUMINT和BIGINT。下面的表显示了需要的每个整数类型的存储和范围。

![MySQL基本类型](img\MySQL\MySQL基本类型.JPG )

#### 2.日期和时间类型

表示时间值的日期和时间类型为DATETIME、DATE、TIMESTAMP、TIME和YEAR。

每个时间类型有一个有效值范围和一个"零"值，当指定不合法的MySQL不能表示的值时使用"零"值。

TIMESTAMP类型有专有的自动更新特性，将在后面描述。

![MySQL日期类型](img\MySQL\MySQL日期类型.JPG)

#### 3. 字符串类型

字符串类型指CHAR、VARCHAR、BINARY、VAR、BINARY、BLOB、TEXT、ENUM和SET

![MySQL字符串类型](img\MySQL\MySQL字符串类型.JPG)

**注意**：char(n) 和 varchar(n) 中括号中 n 代表字符的个数，并不代表字节个数，比如 CHAR(30) 就可以存储 30 个字符。

CHAR 和 VARCHAR 类型类似，但它们保存和检索的方式不同。它们的最大长度和是否尾部空格被保留等方面也不同。在存储或检索过程中不进行大小写转换。

BINARY 和 VARBINARY 类似于 CHAR 和 VARCHAR，不同的是它们包含二进制字符串而不要非二进制字符串。也就是说，它们包含字节字符串而不是字符字符串。这说明它们没有字符集，并且排序和比较基于列值字节的数值值。

BLOB 是一个二进制大对象，可以容纳可变数量的数据。有 4 种 BLOB 类型：TINYBLOB、BLOB、MEDIUMBLOB 和 LONGBLOB。它们区别在于可容纳存储范围不同。

有 4 种 TEXT 类型：TINYTEXT、TEXT、MEDIUMTEXT 和 LONGTEXT。对应的这 4 种 BLOB 类型，可存储的最大长度不同，可根据实际情况选择。