[TOC]

# JDBC

## 一. 简单介绍

1. 概念：

   JDBC（<span style="color:red">J</span>ava <span style="color:red">D</span>ata<span style="color:red">B</span>ase <span style="color:red">C</span>onnectivity）Java数据库连接

   是Java语言中用来规范客户端程序如何来访问数据库的应用程序接口，提供了诸如查询和更新数据库中数据的方法。JDBC也是Sun Microsystems的商标。我们通常说的JDBC是面向关系型数据库的。

2. JDBC提供了一种**与平台无关**的用于执行SQL语句的标准JAVA API，可以方便实现多种关系型数据库的统一操作

> JDBC本质：其实是官方（sun公司）定义的一套操作所有关系型数据库的规则，即接口。各个数据库厂商去实现这套接口，提供数据库驱动jar包。我们可以使用这套接口（JDBC）编程，真正执行的代码是驱动jar包中的实现类。



>The Java Database Connectivity (JDBC) API is the industry standard for database-independent connectivity between the Java programming language and a wide range of databases SQL databases and other tabular data sources, such as spreadsheets or flat files. The JDBC API provides a call-level API for SQL-based database access.
>
>JDBC technology allows you to use the Java programming language to exploit "Write Once, Run Anywhere" capabilities for applications that require access to enterprise data. With a JDBC technology-enabled driver, you can connect all corporate data even in a heterogeneous environment.

***



## 二. 基本使用

### 1. 步骤

#### 1. 导入驱动jar包

#### 2. 注册驱动

```java
Class.forName("com.mysql.jdbc.Driver");
```

#### 3. 获取数据库链接对象Connection

```java
 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8", "root", "root");
```

#### 4. 定义sql语句

```java
String sql = "UPDATE account SET balance = 500 WHERE id = 1";
```

#### 5. 获取执行sql语句的对象 Statement(或预编译sql)

```java
Statement stmt = conn.createStatement();
```

#### 6. 执行sql，接受返回结果

```java
int count = stmt.executeUpdate(sql);
```

#### 7. 处理结果

```java
System.out.println(count);
```

#### 8. 释放资源

```java
stmt.close();
conn.close();
```



```java
/*
*  JDBC快速入门
*  1.导入驱动jar包 复制jar包到当前项目libs目录下，右键目录 Add as libiary.
*  2.注册驱动 Class.forName("com.mysql.jdbc.Driver");
*  3.获取数据库连接对象 DriverManager.getConnection("jdbc:mysql://host:端口/数据库名称","用户","密码");
*  4.定义sql语句  String sql = "SQL语句";
*  5.获取执行sql的对象 Statement Statement stmt = con.createStatement();
*  6.执行SQL语句
*  7.处理结果
*  8.释放资源
* */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo1 {
    public static void main(String[] args) throws Exception {
        //2.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //3.获取数据库连接对象
        Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.3:3306/test?useSSL=false", "root", "Yangbiaocc520.");
        //4.定义sql语句
        String sql = "SELECT * FROM emp;";
        //5.获取执行sql的对象 Statement
        Statement stmt = con.createStatement();
        //6.执行sql
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()){
            Employee emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setSalary(rs.getDouble("salary"));
            emp.setEname(rs.getString("ename"));
            emp.setJob_id(rs.getInt("job_id"));
            emp.setMgr(rs.getInt("mgr"));
            emp.setJoindate(rs.getDate("joindate"));
            emp.setBonus(rs.getDouble("bonus"));
            emp.setDept_id(rs.getInt("dept_id"));
            System.out.println(emp.toString());
        }
        stmt.close();
        con.close();

    }
}
```



### 2. 详解各个对象

#### DriverManager：驱动管理对象

功能：

1. 注册驱动：告诉程序该使用哪一个数据库驱动jar

```java
static void registerDriver(Driver driver); // 注册与给定的驱动程序 DriverManager 。 
/*代码实现：*/
Class.forName("com.mysql.jdbc.Driver");
```

在MySQL5之后的驱动jar包，又不存在以下静态代码块，因此可以省略注册驱动的步骤

```java
 static {
     try {
         java.sql.DriverManager.registerDriver(new Driver());
     } catch (SQLException E) {
         throw new RuntimeException("Can't register driver!");
     }
 }
```



2. 获取数据库连接

```java
static Connection getConnection(String url, String user, String password);
```

* 参数：

  * url: 指定链接地址

    * 语法：jdbc:mysql://ip地址(域名):端口号/数据库名称?[参数列表]

    * 例子: 

      ```java
      jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8
      ```

    * 如果使用的是本机mysql服务器，且使用默认端口，则可省略hostname和端口

  * user: 用户名

  * password: 密码

#### Connection：数据库连接对象

功能：

1. 获取执行sql 的对象

```java
conn = DriverManager.getConnection(url, username, password);
```

2. 管理事务
   * 开启事务：setAutoCommit(boolean autoCommit) ：调用该方法设置参数为false，即开启事务
   * 提交事务：commit() 
   * 回滚事务：rollback()

**常用方法**

| 方法                              | 描述                                             |
| --------------------------------- | ------------------------------------------------ |
| createStatement()                 | 创建向数据库发送sql的statement对象。             |
| prepareStatement(sql)             | 创建向数据库发送预编译sql的PrepareSatement对象。 |
| prepareCall(sql)                  | 创建执行存储过程的callableStatement对象。        |
| setAutoCommit(boolean autoCommit) | 设置事务是否自动提交。                           |
| commit()                          | 在链接上提交事务。                               |
| rollback()                        | 在此链接上回滚事务。                             |

#### Statement：执行sql的对象

1. 执行sql
   1. boolean execute(String sql) ：可以执行任意的sql <span style="color:red">了解 </span>
   2. int executeUpdate(String sql) ：执行**DML**（insert、update、delete）语句、**DDL**(create，alter、drop)语句
   3. ResultSet executeQuery(String sql)  ：执行DQL（select)语句
2. 常用方法

| 方法                      | 含义                                       |
| ------------------------- | ------------------------------------------ |
| executeQuery(String sql)  | 用于向数据发送查询语句。                   |
| executeUpdate(String sql) | 用于向数据库发送insert、update或delete语句 |
| execute(String sql)       | 用于向数据库发送任意sql语句                |
| addBatch(String sql)      | 把多条sql语句放到一个批处理中。            |
| executeBatch()            | 向数据库发送一批sql语句执行。              |

```java
// 练习
/*
 * 1. account表 添加一条记录
 * 2. account表 修改记录
 * 3. account表 删除一条记录
 */


Statement stmt = null;
Connection conn = null;
 try {
     //1. 注册驱动
     Class.forName("com.mysql.jdbc.Driver");
     //2. 定义sql
     String sql = "insert into account values(null,'王五',3000)";
     //3.获取Connection对象
     conn = DriverManager.getConnection("jdbc:mysql:///db3", "root", "root");
     //4.获取执行sql的对象 Statement
     stmt = conn.createStatement();
     //5.执行sql
     int count = stmt.executeUpdate(sql);//影响的行数
     //6.处理结果
     System.out.println(count);
     if(count > 0){
         System.out.println("添加成功！");
     }else{
         System.out.println("添加失败！");
     }
 } catch (ClassNotFoundException e) {
     e.printStackTrace();
 } catch (SQLException e) {
     e.printStackTrace();
 } finally {
     //stmt.close();
     //7. 释放资源
     //避免空指针异常
     if(stmt != null){
         try {
             stmt.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
     if(conn != null){
         try {
             conn.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
 }
```



#### ResultSet：结果集对象,封装查询结果

ResultSet既然用于封装执行结果的，所以该对象提供的都是用于获取数据的get方法

MySQL常用数据类型与JAVA数据类型转换

| SQL类型                  | Jdbc对应方法           | 返回类型           |
| ------------------------ | ---------------------- | ------------------ |
| bit(1)，bit(n)           | getBoolean，getBytes() | Boolean，byte[]    |
| tinyint                  | getByte()              | Byte               |
| smallint                 | getShort()             | Short              |
| int                      | getInt                 | Int                |
| bigint                   | getLong()              | Long               |
| char,varchar,longvarchar | getString              | String             |
| text(clob) blob          | getClob()，getblob()   | Clob，blob         |
| date                     | getDate()              | java.sql.Date      |
| time                     | getTime()              | java.sql.Time      |
| timestamp                | getTimestamp           | java.sql.Timestamp |

* boolean next(): 游标向下移动一行，判断当前行是否是最后一行末尾(是否有数据)，如果是，则返回false，如果不是则返回true

* getXxx(参数):获取数据

  * 获取任意类型的数据：getObject(int index) getObject(string columnName)
  * Xxx：代表数据类型   如： int getInt() ,	String getString()
  * 参数：
    * int：代表列的编号,从1开始   如： getString(1)
    * String：代表列名称。 如： getDouble("balance")

  * 注意：

    * 使用步骤：

      1. 游标向下移动一行
      2. 判断是否有数据
      3. 获取数据

      ```java
      while(rs.next()){
          //获取数据
          //6.2 获取数据
          int id = rs.getInt(1);
          String name = rs.getString("name");
          double balance = rs.getDouble(3);
          System.out.println(id + "---" + name + "---" + balance);
      }
      ```

```java
/*
定义一个方法，查询emp表的数据将其封装为对象，然后装载集合，返回。
    1. 定义Emp类
    2. 定义方法 public List<Emp> findAll(){}
	3. 实现方法 select * from emp;
*/


// 1.定义Emp类
import java.util.Date;
public class Emp {
    private int id;
    private String ename;
    private int job_id;
    private int mgr;
    private Date joindate;
    private double salary;
    private double bonus;
    private int dept_id;

    public Emp() {
    }

    public Emp(int id, String ename, int job_id, int mgr, Date joindate, double salary, double bonus, int dept_id) {
        this.id = id;
        this.ename = ename;
        this.job_id = job_id;
        this.mgr = mgr;
        this.joindate = joindate;
        this.salary = salary;
        this.bonus = bonus;
        this.dept_id = dept_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", ename='" + ename + '\'' +
                ", job_id=" + job_id +
                ", mgr=" + mgr +
                ", joindate=" + joindate +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", dept_id=" + dept_id +
                '}';
    }
}
```

```java
//2. 定义方法 public List<Emp> findAll(){};
//首先定义一个Demo类
public class JDBCDemo {

    public static void main(String[] args) {
        List<Emp> list = new JDBCDemo().findAll();
        System.out.println(list);
        System.out.println(list.size());
    }
    /**
     * 查询所有emp对象
     * @return
     */
    public List<Emp> findAll(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Emp> list = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql:///db3", "root", "root");
            //3.定义sql
            String sql = "select * from emp";
            //4.获取执行sql的对象
            stmt = conn.createStatement();
            //5.执行sql
            rs = stmt.executeQuery(sql);
            //6.遍历结果集，封装对象，装载集合
            Emp emp = null;
            list = new ArrayList<Emp>();
            while(rs.next()){
                //获取数据
                int id = rs.getInt("id");
                String ename = rs.getString("ename");
                int job_id = rs.getInt("job_id");
                int mgr = rs.getInt("mgr");
                Date joindate = rs.getDate("joindate");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                int dept_id = rs.getInt("dept_id");
                // 创建emp对象,并赋值
                emp = new Emp();
                emp.setId(id);
                emp.setEname(ename);
                emp.setJob_id(job_id);
                emp.setMgr(mgr);
                emp.setJoindate(joindate);
                emp.setSalary(salary);
                emp.setBonus(bonus);
                emp.setDept_id(dept_id);

                //装载集合
                list.add(emp);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
```

#### PreparedStatement：执行sql的对象

1. SQL注入问题：在拼接sql时，有一些sql的特殊关键字参与字符串的拼接。会造成安全性问题，例如：

>1. 输入用户随便，输入密码：a' or 'a' = 'a
>2. sql：select * from user where username = 'fhdsjkf' and password = 'a' or 'a' = 'a' 

2. Statement会使数据库频繁编译SQL，可能造成数据库缓冲区溢出。PreparedStatement 可对SQL进行预编译，从而提高数据库的执行效率。

   并且PreperedStatement对于sql中的参数，允许使用占位符的形式进行替换，简化sql语句的编写。

3. 预编译的SQL：参数使用?作为占位符

4. 步骤

   1. 导入驱动jar抱

   2. 注册驱动

   3. 获取数据库连接对象Connection

   4. 定义sql

      * 注意：sql的参数使用？作为占位符。 如：

        select * from user where username = ? and password = ?;

   5.  获取执行sql语句的对象 

      PreparedStatement Connection.prepareStatement(String sql) ;

   6. 给?赋值

      * 方法：

        参数1：?的位置编号，从1开始

        参数2：?的值

   7. 执行sql，接收返回结果，不需要再传递sql语句
   8. 处理结果
   9.  释放资源

5. **注意**：一般情况下都将使用PreparedStatement而不使用Statement
   * 防止SQL注入
   * 效率更高



***



## 三. 抽取JDBC工具类

### 1. JDBCUtils

由于每一次使用JDBC进行的步骤大致相同，因此可以将共同的步骤制作为一个工具类。

目的：简化书写，提高代码复用性

### 2. JDBCUtils实现

为保证工具类的通用性，在src目录下创建如下配置文件

*jdbc.properties*

```properties
# jdbc地址
url = jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8
#用户名
user = root
#密码
password = root
#驱动jar包名称
driver=com.mysql.jdbc.Driver
```

代码实现：

```java
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

/**
 * 原生JDBC工具类
 */
public class JDBCUtils {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try {
            // 创建Properties集合类
            Properties pro = new Properties();

            // 获取src路径下的配置文件路径
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res = classLoader.getResource("JDBCUtils.properties");
            String path = res.getPath();

            // 加载配置文件
            pro.load(new FileReader(path));
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            
            // 注册驱动
            Class.forName(driver);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接对象
     *
     * @return Connection对象
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源1
     * @param statement
     * @param connection
     */

    public static void close(Statement statement, Connection connection) {
        colse(null, statement, connection);
    }
    /**
     * 释放资源2
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
```



***



## 四. JDBC控制事物

使用Connection对象管理事物

* 开启事务：setAutoCommit(boolean autoCommit) ：调用该方法设置参数为false，即开启事务
  		*  在执行sql之前开启事务
      		*  
  *  提交事务：commit() 
      		*  当所有sql都执行完提交事务
  * 回滚事务：rollback() 
    		*  在catch中回滚事务

```java
import com.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 事务操作
 */
public class JDBCDemo10 {


    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);

            //2.定义sql
            //2.1 张三 - 500
            String sql1 = "update account set balance = balance - ? where id = ?";
            //2.2 李四 + 500
            String sql2 = "update account set balance = balance + ? where id = ?";
            //3.获取执行sql对象
            pstmt1 = conn.prepareStatement(sql1);
            pstmt2 = conn.prepareStatement(sql2);
            //4. 设置参数
            pstmt1.setDouble(1,500);
            pstmt1.setInt(2,1);

            pstmt2.setDouble(1,500);
            pstmt2.setInt(2,2);
            //5.执行sql
            pstmt1.executeUpdate();
            // 手动制造异常
            int i = 3/0;

            pstmt2.executeUpdate();
            //提交事务
            conn.commit();
        } catch (Exception e) {
            //事务回滚
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(pstmt1,conn);
            JDBCUtils.close(pstmt2,null);
        }


    }

}
```





***



# 数据库连接池

## 一. 简介



<img src="img\JDBC\数据库连接池.png" alt="数据库连接池" style="float:left;zoom:75%;" />

### 1. 使用连接池的原因

​		在实际应用开发中，特别是在WEB应用系统中，如果直接使用JDBC直接访问数据库中的数据，每一次数据访问请求都必须经历建立数据库连接、打开数据库、存取数据和关闭数据库连接等步骤，而连接并打开数据库是一件既消耗资源又费时的工作，如果频繁发生这种数据库操作，系统的性能必然会急剧下降，甚至会导致系统崩溃。



### 2.基本原理

​		为数据库连接建立一个“缓冲池”，预先在池中放入一定数量的数据库连接。需要时，判断连接池中是否有空闲连接。如果有，修改其连接状态，从池子中取出连接进行使用，操作完毕后，再将连接放入池子中，重新设置其连接状态。从而避免了频繁的创建对象申请资源。

创建对象：

* 开辟堆空间
* 调用构造函数
* 返回地址



***



## 二.数据库连接池的作用

**①资源重用**

由于数据库连接得到重用，避免了频繁创建、释放连接引起的大量性能开销。在减少系统消耗的基础上，增进了系统环境的平稳性（减少内存碎片以级数据库临时进程、线程的数量）

**②更快的系统响应速度**

数据库连接池在初始化过程中，往往已经创建了若干数据库连接置于池内备用。此时连接池的初始化操作均已完成。对于业务请求处理而言，直接利用现有可用连接，避免了数据库连接初始化和释放过程的时间开销，从而缩减了系统整体响应时间。

**③新的资源分配手段**

对于多应用共享同一数据库的系统而言，可在应用层通过数据库连接的配置，实现数据库连接技术。

**④统一的连接管理，避免数据库连接泄露**

在较为完备的数据库连接池实现中，可根据预先的连接占用超时设定，强制收回被占用的连接，从而避免了常规数据库连接操作中可能出现的资源泄露



***



## 三.数据库连接池的工作原理

连接池的工作原理主要由三部分组成，分别为

* 连接池的建立
* 连接池中连接的使用管理
* 连接池的关闭

#### 1. 连接池的建立

一般在系统初始化时，连接池会根据系统配置建立，并在池中创建了几个连接对象，以便使用时能从连接池中获取。连接池中的连接不能随意创建和关闭，这样避免了连接随意建立和关闭造成的系统开销。

Java中提供了很多容器类可以方便的构建连接池，例如Vector、Stack等。

#### 2. 连接池的管理

连接池管理策略是连接池机制的核心，连接池内连接的分配和释放对系统的性能有很大的影响。其管理策略是：

当客户请求数据库连接时，首先查看连接池中是否有空闲连接，如果存在空闲连接，则将连接分配给客户使用；如果没有空闲连接，则查看当前所开的连接数是否已经达到最大连接数，如果没达到就重新创建一个连接给请求的客户；如果达到就按设定的最大等待时间进行等待，如果超出最大等待时间，则抛出异常给客户。

当客户释放数据库连接时，先判断该连接的引用次数是否超过了规定值，如果超过就从连接池中删除该连接，否则保留为其他客户服务。

该策略保证了数据库连接的有效复用，避免频繁的建立、释放连接所带来的系统资源开销。

#### 3. 连接池的关闭

当应用程序退出时，关闭连接池中所有的连接，释放连接池相关的资源，该过程正好与创建相反。



***



## 四. 注意

1. 并发问题： 为了使连接管理服务具有最大的通用性，必须考虑多线程环境，即并发问题。

2. 事务处理：当2个线程共用一个连接Connection对象，而且各自都有自己的事务要处理时候，对于连接池是一个很头疼的问题，因为即使Connection类提供了相应的事务支持，可是我们仍然不能确定那个数据库操作是对应那个事务的，这是由于我们有２个线程都在进行事务操作而引起的。

   为此我们可以使用每一个事务独占一个连接来实现，虽然这种方法有点浪费连接池资源但是可以大大降低事务管理的复杂性。

3. 连接池的分配与释放：

   连接池的分配与释放，对系统的性能有很大的影响。合理的分配与释放，可以提高连接的复用度，从而降低建立新连接的开销，同时还可以加快用户的访问速度。

   对于连接的管理可使用一个List。即把已经创建的连接都放入List中去统一管理。每当用户请求一个连接时，系统检查这个List中有没有可以分配的连接。如果有就把那个最合适的连接分配给他，如果没有就抛出一个异常给用户。

4. 连接池的配置与维护：

   连接池中到底应该放置多少连接，才能使系统的性能最佳？

   系统可采取设置最小连接数（minConnection）和最大连接数（maxConnection）等参数来控制连接池中的连接。比方说，最小连接数是系统启动时连接池所创建的连接数。如果创建过多，则系统启动就慢，但创建后系统的响应速度会很快；如果创建过少，则系统启动的很快，响应起来却慢。这样，可以在开发时，设置较小的最小连接数，开发起来会快，而在系统实际使用时设置较大的，因为这样对访问客户来说速度会快些。最大连接数是连接池中允许连接的最大数目，具体设置多少，要看系统的访问量，可通过软件需求上得到。

   如何确保连接池中的最小连接数呢？有动态和静态两种策略。动态即每隔一定时间就对连接池进行检测，如果发现连接数量小于最小连接数，则补充相应数量的新连接,以保证连接池的正常运转。静态是发现空闲连接不够时再去检查。



***



## 五. 实现

1. 标准接口：javax.sql.DataSource

   * 基本实现——生成一个标准连接对象

   * 连接池实现——生成一个连接对象，该对象将自动参与连接池。此实现与中间层连接池管理器一起工作

   * 分布式事务实现——生成一个用于分布式事务的连接对象，并且几乎总是参与到连接池中。该实现与中间层事务管理器一起工作，几乎总是使用连接池管理器

   至少应该具有的两个方法：

   * 获取连接：getConnection()

   * 归还连接：Connection.close()。如果连接对象Connection是从连接池中获取的，那么调用Connection.close()方法，则不会再关闭连接了。而是归还连接

### 1. C3P0

使用步骤：

1. 导入jar包c3p0-xxx.jar, mchange-commons-java-0.2.12.jar, 数据库驱动jar包

2. 定义配置文件：

   * 名称：c3p0.properties 或者 c3p0-config.xml

     ```xml
     <c3p0-config>
       <!-- 使用默认的配置读取连接池对象 -->
       <default-config>
       	<!--  连接参数 -->
         <property name="driverClass">com.mysql.jdbc.Driver</property>
         <property name="jdbcUrl">jdbc:mysql://192.168.1.3/test?useSSL=false&amp;characterEncoding=utf-8</property>
         <property name="user">root</property>
         <property name="password">root</property>
         
         <!-- 连接池参数 -->
         <property name="initialPoolSize">5</property>
         <property name="maxPoolSize">10</property>
         <property name="checkoutTimeout">3000</property>
       </default-config>
     
       <named-config name="otherc3p0"> 
         <!--  连接参数 -->
         <!-- 连接池参数 -->
       </named-config>
     </c3p0-config>
     ```

     

   * 路径：直接将文件放在src目录下即可。

3. 创建核心对象 数据库连接池对象 ComboPooledDataSource
4. 获取连接： getConnection

```java
 //1.创建数据库连接池对象
DataSource ds  = new ComboPooledDataSource();
//2. 获取连接对象
Connection conn = ds.getConnection();
```

### 2. Druid

Druid数据库连接池实现技术，由阿里巴巴提供的

使用步骤：

1. 导入druid-xxx.jar, 数据库驱动
2. 定义配置文件：
      * 是properties形式的
      * 可以叫任意名称，可以放在任意目录下（一般放在src或src/config下）
   3. 加载配置文件。Properties
   4. 获取数据库连接池对象：通过工厂来来获取  DruidDataSourceFactory
   5. 获取连接：getConnection

```java
//3.加载配置文件
Properties pro = new Properties();
InputStream is = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
pro.load(is);
//4.获取连接池对象
DataSource ds = DruidDataSourceFactory.createDataSource(pro);
//5.获取连接
Connection conn = ds.getConnection();
```

```java
import cn.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * 使用新的工具类
 */
public class DruidDemo {
    public static void main(String[] args) {
        /*
         * 完成添加操作：给account表添加一条记录
         */
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //2.定义sql
            String sql = "insert into account values(null,?,?)";
            //3.获取pstmt对象
            pstmt = conn.prepareStatement(sql);
            //4.给？赋值
            pstmt.setString(1,"王五");
            pstmt.setDouble(2,3000);
            //5.执行sql
            int count = pstmt.executeUpdate();
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6. 释放资源
            JDBCUtils.close(pstmt,conn);
        }
    }
}
```



## 六. Druid的JDBCUtils

```java
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.datasource.druid.DruidDemo;
import java.io.InputStream;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author ：Yxisen
 * @date ：Created in 2020/8/7 20:23
 * @description： Druid工具类
 */
public class JDBCUtils {
    // 定义连接池对象
    public static DataSource ds;

    static {
        //载入配置
        try {
            Properties pro = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return Connection对象
     * @throws SQLException
     * @description 从连接池中获取连接
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


    /**
     * 释放资源1
     *
     * @param statement
     * @param connection
     */

    public static void close(Statement statement, Connection connection) {
        JDBCUtils.close(null,statement,connection);
    }

    /**
     * 释放资源2
     *
     * @param statement
     * @param connection
     * @param resultSet
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return 数据库连接池
     * @description 获得数据库连接池
     */
    public static DataSource getDataSource() {
        return ds;
    }

}
```

# JDBCTemplate

Spring框架对JDBC的简单封装。提供了一个JDBCTemplate对象简化JDBC的开发

使用步骤：

1. 导入jar包，驱动jar包

<img src="img\JDBC\JDBCTemplate.png" alt="image" style="float:left;" />

2. 创建JdbcTemplate对象。依赖于数据源Database

```java
JdbcTemplate template = new JdbcTemplate(ds);
```

3. 调用JdbcTemplate的方法来完成CRUD的操作

| 方法           | 作用                                                         |
| -------------- | ------------------------------------------------------------ |
| update()       | 执行DML语句。增、删、改语句                                  |
| queryForMap()  | 查询结果将结果集封装为map集合，将列名作为key，将值作为value 将这条记录封装为一个map集合 |
| queryForList() | 查询结果将结果集封装为list集合                               |
| query()        | 查询结果，将结果封装为JavaBean对象                           |
| queryForObject | 查询结果，将结果封装为对象                                   |

注意:

1. queryForMap( )查询长度只能是1
2. queryForList( )将每一条记录封装为一个Map集合，再将Map集合装载到List集合中
3. query( ) 的参数：RowMapper
   * 一般我们使用BeanPropertyRowMapper实现类。可以完成数据到JavaBean的自动封装
   * new BeanPropertyRowMapper<类型>(类型.class)
4. queryForObject 一般用于复合函数的查询

```java
import test.domain.Emp;
import cn..JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JdbcTemplateDemo2 {

    //Junit单元测试，可以让方法独立执行


    //1. 获取JDBCTemplate对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 1. 修改1号数据的 salary 为 10000
     */
    @Test
    public void test1(){

        //2. 定义sql
        String sql = "update emp set salary = 10000 where id = 1001";
        //3. 执行sql
        int count = template.update(sql);
        System.out.println(count);
    }

    /**
     * 2. 添加一条记录
     */
    @Test
    public void test2(){
        String sql = "insert into emp(id,ename,dept_id) values(?,?,?)";
        int count = template.update(sql, 1015, "郭靖", 10);
        System.out.println(count);

    }

    /**
     * 3.删除刚才添加的记录
     */
    @Test
    public void test3(){
        String sql = "delete from emp where id = ?";
        int count = template.update(sql, 1015);
        System.out.println(count);
    }

    /**
     * 4.查询id为1001的记录，将其封装为Map集合
     * 注意：这个方法查询的结果集长度只能是1
     */
    @Test
    public void test4(){
        String sql = "select * from emp where id = ? or id = ?";
        Map<String, Object> map = template.queryForMap(sql, 1001,1002);
        System.out.println(map);
        //{id=1001, ename=孙悟空, job_id=4, mgr=1004, joindate=2000-12-17, salary=10000.00, bonus=null, dept_id=20}

    }

    /**
     * 5. 查询所有记录，将其封装为List
     */
    @Test
    public void test5(){
        String sql = "select * from emp";
        List<Map<String, Object>> list = template.queryForList(sql);

        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }
    }

    /**
     * 6. 查询所有记录，将其封装为Emp对象的List集合
     */

    @Test
    public void test6(){
        String sql = "select * from emp";
        List<Emp> list = template.query(sql, new RowMapper<Emp>() {

            @Override
            public Emp mapRow(ResultSet rs, int i) throws SQLException {
                Emp emp = new Emp();
                int id = rs.getInt("id");
                String ename = rs.getString("ename");
                int job_id = rs.getInt("job_id");
                int mgr = rs.getInt("mgr");
                Date joindate = rs.getDate("joindate");
                double salary = rs.getDouble("salary");
                double bonus = rs.getDouble("bonus");
                int dept_id = rs.getInt("dept_id");

                emp.setId(id);
                emp.setEname(ename);
                emp.setJob_id(job_id);
                emp.setMgr(mgr);
                emp.setJoindate(joindate);
                emp.setSalary(salary);
                emp.setBonus(bonus);
                emp.setDept_id(dept_id);

                return emp;
            }
        });


        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

    /**
     * 6. 查询所有记录，将其封装为Emp对象的List集合
     */

    @Test
    public void test6_2(){
        String sql = "select * from emp";
        List<Emp> list = template.query(sql, new BeanPropertyRowMapper<Emp>(Emp.class));
        for (Emp emp : list) {
            System.out.println(emp);
        }
    }

    /**
     * 7. 查询总记录数
     */

    @Test
    public void test7(){
        String sql = "select count(id) from emp";
        Long total = template.queryForObject(sql, Long.class);
        System.out.println(total);
    }

}
```

