[TOC]

# 一、MyBatis入门

## 1. 什么是MyBatis

> MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

[MyBatis官网](https://mybatis.org/mybatis-3/zh/index.html)

## 2. MyBatis 快速入门

开发步骤：

1. 添加MyBatis的坐标

2. 创建Account数据表

3. 编写Account实体类 
4. 编写AccountMapper接口

4. 编写映射文件AccountMapper.xml

5. 编写核心文件SqlMapConfig.xml

6. 编写测试类

### 2.1 导入坐标

```xml
<!-- mysql驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version>
</dependency>
<!-- MyBatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.5</version>
</dependency>
<!-- 单元测试 -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<!-- log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```



### 2.2 创建表

```sql
CREATE TABLE `account` (
  `name` char(20) NOT NULL,
  `money` double(10,2) DEFAULT '0.00',
  `id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```



### 2.3 创建实体类

```java
public class Account {
    private int id;
    private String name;
    private Double money;
    // setter getter toString;
}
```



### 2.4 编写AccountMapper接口

```java
public interface AccountMapper {
    
    public List<Account> findAll();

    public Account findById(int id);

    public void save(Account account);

    public void deleteById(int id);

    public void update(Account account);
}
```



### 2.5 编写映射文件AccountMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.onecolour.mybatisTest.mapper.AccountMapper">
    <!-- 查询全部 -->
    <select id="findAll" resultType="account">
        SELECT * FROM account;
    </select>
</mapper>
```

### 2.6 编写核心文件SqlMapConfig.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!-- 加载数据库配置文件 -->
    <properties resource="jdbc.properties"/>

    <!-- 自定义别名 -->
    <typeAliases>
        <typeAlias type="cn.onecolour.mybatisTest.domain.Account" alias="account"/>
    </typeAliases>

    <!-- 数据源环境 -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    
    <!-- 加载映射文件 -->
    <mappers>
        <mapper resource="mapper/AccountMapper.xml"/>
    </mappers>

</configuration>
```



### 2.7 编写测试类

```java
public class MyBatisTest1 {
    private SqlSession sqlSession;
    @Before
    public void before() throws IOException {
        // 加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new
                SqlSessionFactoryBuilder().build(resourceAsStream);
        // 获得sqlSession对象
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test1(){
        // 执行sql语句
        List<Account> accountList = sqlSession.selectList("cn.onecolour.mybatisTest.mapper.AccountMapper.findAll");
        // 打印结果
        System.out.println(accountList);
    }

    @Test
    public void test2(){
        // 获得代理对象
        AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
        List<Account> accountList = accountMapper.findAll();
        System.out.println(accountList);
    }

    @After
    public void after(){
        sqlSession.close();
    }
}
```



## 3. MyBatis CRUD

数据表、实体、AccountMapper接口与上方相同

### 3.1 插入数据

AccountMapper mapper标签中加入：

```xml
<!-- 插入Account -->
<insert id="save" parameterType="account">
    INSERT INTO account (name, money) VALUES(#{name},#{money})
</insert>
```

测试方法：

```java
@Test
public void test3(){
    // 创建Account对象
    Account account = new Account();
    account.setName("wangwu");
    account.setMoney(125d);
    // 获得mapper对象
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    // 执行插入操作
    accountMapper.save(account);
    sqlSession.commit();
}
```

**注意事项：**

* 插入语句使用insert标签
* 在映射文件中使用parameterType属性指定要插入的数据类型
* Sql语句中使用#{实体属性名}方式引用实体中的属性值
* 插入操作使用的API
  * 方法1：先获得mapper再执行mapper.save 命名空间为mapper接口全类名，id 与mapper接口中的一致
  * 方法2：sqlSession.insert(“命名空间.id”,实体对象);
* 插入操作涉及数据库数据变化，所以要使用sqlSession对象显示的提交事务，即sqlSession.commit() 

### 3.2 查询&修改数据

AccountMapper mapper标签中加入：

```xml
<!-- 根据id查询 -->
<select id="findById" resultType="account" parameterType="int">
    SELECT * FROM account WHERE id =#{id};
</select>

<!-- 修改 -->
<update id="update" parameterType="account">
    UPDATE account SET name=#{name},money=#{money} WHERE id=#{id}
</update>
```

测试方法：

```java
@Test
public void test4(){
    // 获得mapper对象
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    // 查询一个account
    Account account = accountMapper.findById(1);
    account.setMoney(account.getMoney()+500);
    // 执行更新操作
    accountMapper.update(account);
    sqlSession.commit();
}
```

* 修改语句使用update标签

* 修改操作使用的API
  * 方法2：sqlSession.update(“命名空间.id”,实体对象);

### 3.3 删除数据

AccountMapper mapper标签中加入：

```xml
<!-- 删除 -->
<delete id="deleteById" parameterType="int">
    DELETE FROM account WHERE id=#{id}
</delete>
```

测试方法

```java
@Test
public void test5(){
    // 获得mapper对象
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    accountMapper.deleteById(3);
    sqlSession.commit();
}
```

* 删除语句使用delete标签
* Sql语句中使用#{任意字符串}方式引用传递的单个参数
* 删除操作使用的API
  * 方法2：sqlSession.delete(“命名空间.id”,Object);



# 二、MyBatis XML 配置

## 1. 配置文件

### 1.1 XML头

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
```

### 1.2 标签层级

* configuration（配置）
  * <a href="#properties" style="text-decoration:none;color:red">properties（属性）</a>
  * <a href="https://mybatis.org/mybatis-3/zh/configuration.html#settings" style="text-decoration:none;">settings（设置）</a>
  * <a href="#typeAliases" style="text-decoration:none;color:red">typeAliases（类型别名）</a>
  * <a href="https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers" style="text-decoration:none;">typeHandlers（类型处理器）</a>
  * <a href="https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory" style="text-decoration:none;">objectFactory（对象工厂）</a>
  * <a href="https://mybatis.org/mybatis-3/zh/configuration.html#plugins" style="text-decoration:none;">plugins（插件）</a>
  * <a href="#enviroments" style="text-decoration:none;color:red">environments（环境配置）</a>
    * environment（环境变量）
      * <a href="#transactionManager" style="text-decoration:none;color:red">transactionManager（事务管理器）</a>
      * <a href="#dataSource" style="text-decoration:none;color:red">dataSource（数据源）</a>
  * <a href="https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider" style="text-decoration:none;">databaseIdProvider（数据库厂商标识）</a>
  * <a href="#mappers" style="text-decoration:none;color:red">mappers（映射器）</a>

### 1.3 常用配置

#### <span id="properties">属性 properties</span>

这些属性可以在外部进行配置（.properties 文件），并可以进行动态替换，也可以在 properties 元素的子元素中设置。

```xml
<properties resource="jdbc.properties">
    <property name="accountMapper" value="mapper/AccountMapper.xml"/>
</properties>

<!-- 加载映射文件 -->
<mappers>
    <mapper resource="${accountMapper}"/>
</mappers>
```



#### <span id="typeAliases">类型别名（typeAliases）</span>

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。

```xml
<typeAliases>
    <typeAlias type="cn.onecolour.mybatisTest.domain.Account" alias="account"/>
</typeAliases>
```

当这样配置时，`account` 可以用在任何使用 `cn.onecolour.mybatisTest.domain.Account` 的地方。

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

```xml
<typeAliases>
    <package name="cn.onecolour.mybatisTest.domain"/>
</typeAliases>
```

每一个在包 `domain` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.Account` 的别名为 `account`

如果POJO使用了注解`@Alias("name")`，则以注解为准



#### <span id="enviroments">环境配置（enviroments）</span>

MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者想在具有相同 Schema 的多个生产数据库中使用相同的 SQL 映射。还有许多类似的使用场景。

* **每个数据库对应一个 SqlSessionFactory 实例**



`environments` 元素定义了如何配置环境。

```xml
<environments default="development">
    <environment id="development">
        <transactionManager type="JDBC"/>
        <dataSource type="POOLED">
            <property name="driver" value="${jdbc.driver}"/>
            <property name="url" value="${jdbc.url}"/>
            <property name="username" value="${jdbc.username}"/>
            <property name="password" value="${jdbc.password}"/>
        </dataSource>
    </environment>
</environments>
```

注意一些关键点:

* 默认使用的环境 ID（比如：default="development"）。
* 每个 environment 元素定义的环境 ID（比如：id="development"）。
* 事务管理器的配置（比如：type="JDBC"）。
* 数据源的配置（比如：type="POOLED"）。

默认环境和环境 ID 顾名思义。 环境可以随意命名，但务必保证默认的环境 ID 要匹配其中一个环境 ID。

##### <span id="transactionManager">事务管理器（transactionManager）</span>

在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）：

* JDBC – 这个配置直接使用了 <span style="color:red">JDBC </span>的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。
* MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。

##### <span id="dataSource">数据源（dataSource）</span>

dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。

1. **UNPOOLED**– 这个数据源的实现会每次请求时打开和关闭连接。虽然有点慢，但对那些数据库连接可用性要求不高的简单应用程序来说，是一个很好的选择。

   * `driver` – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
   * `url` – 这是数据库的 JDBC URL 地址。
   * `username` – 登录数据库的用户名。
   * `password` – 登录数据库的密码。
   * `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。
   * `defaultNetworkTimeout` – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。

   作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上“driver.”前缀即可，例如：

   * `driver.encoding=UTF8`

   这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

2. **POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 Web 应用快速响应请求。

   除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

   * `poolMaximumActiveConnections` – 在任意时间可存在的活动（正在使用）连接数量，默认值：10
   * `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
   * `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
   * `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。
   * `poolMaximumLocalBadConnectionTolerance` – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3（新增于 3.4.5）
   * `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。
   * `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
   * `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

3. **JNDI** – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个 JNDI 上下文的数据源引用。



#### <span id="mappers">映射器 mappers</span>

告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>
```

```xml
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>
```

```xml
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```



## 2. 映射文件

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

* `cache` – 该命名空间的缓存配置。
* `cache-ref` – 引用其它命名空间的缓存配置。
* `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
* `parameterMap` – 老式风格的参数映射。此元素已被废弃，并可能在将来被移除！请使用行内参数映射。文档中不会介绍此元素。
* `sql` – 可被其它语句引用的可重用语句块。
* `insert` – 映射插入语句。
* `update` – 映射更新语句。
* `delete` – 映射删除语句。
* `select` – 映射查询语句。



### 2.1 select标签

简单查询语句

```xml
<select id="findById" resultType="account" parameterType="int">
    SELECT * FROM account WHERE id =#{id};
</select>
<!-- 这个语句名为 findById，接受一个 int（或 Integer）类型的参数，并返回一个 别名定义为account的类型的对象，其中的键是列名，值便是结果行中的对应值。 -->
```

注意：

```
#{id}
```

这就告诉 MyBatis 创建一个预处理语句（PreparedStatement）参数，在 JDBC 中，这样的一个参数在 SQL 中会由一个“?”来标识，并被传递到一个新的预处理语句中



| 属性            | 描述                                                         |
| :-------------- | :----------------------------------------------------------- |
| `id`            | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType` | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| `resultType`    | 期望从这条语句中返回结果的类全限定名或别名。 注意，如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身的类型。 resultType 和 resultMap 之间只能同时使用一个。 |
| `resultMap`     | 对外部 resultMap 的命名引用。结果映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂的映射问题都能迎刃而解。 resultType 和 resultMap 之间只能同时使用一个。 |
| `flushCache`    | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：false。 |
| `useCache`      | 将其设置为 true 后，将会导致本条语句的结果被二级缓存缓存起来，默认值：对 select 元素为 true。 |
| `timeout`       | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `fetchSize`     | 这是一个给驱动的建议值，尝试让驱动程序每次批量返回的结果行数等于这个设置值。 默认值为未设置（unset）（依赖驱动）。 |
| `statementType` | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `resultSetType` | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖数据库驱动）。 |
| `databaseId`    | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |
| `resultOrdered` | 这个设置仅针对嵌套结果 select 语句：如果为 true，将会假设包含了嵌套结果集或是分组，当返回一个主结果行时，就不会产生对前面结果集的引用。 这就使得在获取嵌套结果集的时候不至于内存不够用。默认值：`false`。 |
| `resultSets`    | 这个设置仅适用于多结果集的情况。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |



### 2.2 insert, update 和 delete

数据变更语句 insert，update 和 delete 的实现非常接近：

```xml
<insert
  id="insertAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  keyProperty=""
  keyColumn=""
  useGeneratedKeys=""
  timeout="20">

<update
  id="updateAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">

<delete
  id="deleteAuthor"
  parameterType="domain.blog.Author"
  flushCache="true"
  statementType="PREPARED"
  timeout="20">
```

| 属性               | 描述                                                         |
| :----------------- | :----------------------------------------------------------- |
| `id`               | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterMap`     | 用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。 |
| `flushCache`       | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：（对 insert、update 和 delete 语句）true。 |
| `timeout`          | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `statementType`    | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| `useGeneratedKeys` | （仅适用于 insert 和 update）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系型数据库管理系统的自动递增字段），默认值：false。 |
| `keyProperty`      | （仅适用于 insert 和 update）指定能够唯一识别对象的属性，MyBatis 会使用 getGeneratedKeys 的返回值或 insert 语句的 selectKey 子元素设置它的值，默认值：未设置（`unset`）。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `keyColumn`        | （仅适用于 insert 和 update）设置生成键值在表中的列名，在某些数据库（像 PostgreSQL）中，当主键列不是表中的第一列的时候，是必须设置的。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `databaseId`       | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |

下面是 insert，update 和 delete 语句的示例：

```xml
<insert id="insertAuthor">
  insert into Author (id,username,password,email,bio)
  values (#{id},#{username},#{password},#{email},#{bio})
</insert>

<update id="updateAuthor">
  update Author set
    username = #{username},
    password = #{password},
    email = #{email},
    bio = #{bio}
  where id = #{id}
</update>

<delete id="deleteAuthor">
  delete from Author where id = #{id}
</delete>
```



### 2.3 sql标签

Sql中可将重复的sql提取出来，使用时用include引用即可，最终达到sql重用的目的

```xml
//建立sql片段
<sql id="query_user_where">
    <if test="id!=null and id!=''">
        and id=#{id}
    </if>
    <if test="username!=null and username!=''">
        and username like '%${username}%'
    </if>
</sql>

//使用include引用sql片段
<select id="findUserList" parameterType="user" resultType="user">
    select * from user
    <where>
        <include refid="query_user_where"/>
    </where>
</select>

//引用其它mapper.xml的sql片段
<include refid="namespace.sql片段"/>
```

#### 字符串替换

默认情况下，使用 `#{}` 参数语法时，MyBatis 会创建 `PreparedStatement` 参数占位符，并通过占位符安全地设置参数（就像使用 ? 一样）。如果字符不需要转义用`${}`

```xml
ORDER BY ${columnName}
```

### 2.4 ResultMap 结果映射

resultmap是mybatis中最复杂的元素之一，它描述如何从结果集中加载对象，主要作用是定义映射规则、级联的更新、定制类型转化器。

#### 2.4.1 resultmap构成元素

| 元素          | 子元素      | 作用                                                |
| ------------- | ----------- | --------------------------------------------------- |
| constructor   | idArg 、arg | 用于配置构造器方法                                  |
| id            |             | 将结果集标记为id，以方便全局调用                    |
| result        |             | 配置POJO到数据库列名映射关系                        |
| association   | 级联使用    | 代表一对一关系                                      |
| collection    | 级联使用    | 代表一对多关系                                      |
| discriminator | 级联使用    | 鉴别器 根据实际选择实例，可以通过特定条件确定结果集 |

##### 1. id和result元素

以Account类为例：

```java
public class Account {
    private int id;
    private String name;
    private Double money;

    public Account() {
    }

    public Account(int id, String name, Double money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }
    // getter and setter
}
```

id、result是最简单的映射，id为主键映射；result其他基本数据库表字段到实体类属性的映射

```xml
<resultMap id="Account" type="account">
    <id property="id" column="id" javaType="int" jdbcType="INTEGER"/>
    <result property="name" column="name" javaType="String" jdbcType="VARCHAR"/>
    <result property="money" column="money" javaType="Double" jdbcType="DOUBLE"/>
</resultMap>
<!-- 查询全部 -->
<select id="findAll" resultMap="Account">
    SELECT * FROM account
</select>
```

id、result语句属性配置细节：

| 属性        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| property    | 需要映射到JavaBean 的属性名称。                              |
| column      | 数据表的列名或者标签别名。                                   |
| javaType    | 一个完整的类名，或者是一个类型别名。如果你匹配的是一个JavaBean，那MyBatis 通常会自行检测到。然后，如果你是要映射到一个HashMap，那你需要指定javaType 要达到的目的。 |
| jdbcType    | 数据表支持的类型列表。这个属性只在insert,update 或delete 的时候针对允许空的列有用。JDBC 需要这项，但MyBatis 不需要。如果你是直接针对JDBC 编码，且有允许空的列，而你要指定这项。 |
| typeHandler | 使用这个属性可以覆写类型处理器。这项值可以是一个完整的类名，也可以是一个类型别名。 |



#### 2.4.2 CONSTRUCTOR 构造器

在resultMap中，通常使用id、result子元素把Java实体类的属性映射到数据库表的字段上。但是如果在遇到JavaBean没有无参构造函数时，我还需要使用构造器元素实现一个JavaBean的实例化和数据注入。
再以Account为例，那么对应的resultmap就需要添加构造器：

```xml
<resultMap id="Account" type="account">
    <constructor>
        <idArg column="id" javaType="int" jdbcType="INTEGER"/>
        <arg column="name" javaType="String" jdbcType="VARCHAR"/>
        <arg column="money" javaType="Double" jdbcType="DOUBLE"/>
    </constructor>
</resultMap>
<!-- 查询全部 -->
<select id="findAll" resultMap="Account">
    SELECT * FROM account
</select>
```

注意：构造器中基本类型要换成包装类型，否则会报`NoSuchMethodException`



#### 2.4.3 结果集

##### 1.  使用map储存结果集

一般情况下，所有select语句都可以使用map储存，但是使用map就意味着可读性的下降，**所以这不是推荐的方式。**

```xml
<!-- 根据id查询,装入map集合中 -->
<select id="findByIdReturnMap" resultType="map" parameterType="int">
    SELECT * FROM account WHERE id =#{id};
</select>
```

```java
public Map<String,String> findByIdReturnMap(int id);
```

```java
@Before
public void before() throws IOException {
    //加载核心配置文件
    InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
    //获得sqlSession工厂对象
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    //获得sqlSession对象
    sqlSession = sqlSessionFactory.openSession();
}

@Test
public void test6(){
    AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
    Map<String, String> map = accountMapper.findByIdReturnMap(1);
    System.out.println(map);
}
```



##### 2. 使用POJO储存结果集

一般我们都使用POJO储存查询结果。我们可以使用select自动映射，还可以使用select语句中的resultMap属性配置映射集合，不过需要提前定义resultMap。
那我们就可以将之前的select语句修改：

```xml
<!-- 根据id查询 -->
<select id="findById" resultType="account" parameterType="int">
    SELECT * FROM account WHERE id =#{id};
</select>
```



#### 2.4.4 级联

在数据库中包含着一对多、一对一的关系。比如说一个人和他的身份证就是一对一的关系，但是他和他的银行卡就是一对多的关系。我们的生活中存在着很多这样的场景。我们也希望在获取这个人的信息的同时也可以把他的身份证信息一同查出，这样的情况我们就要使用级联。在级联中存在3种对应关系。

* 一对一的关系
* 一对多的关系
* 多对多的关系（这种情况由于比较复杂，我们通常会使用双向一对多的关系（即中间表）来降低复杂度）

a. 实体类

```java
public class User {
    private String uuid;
    private String username;
    private String password;
    private List<Card> cardList;
    private List<Goods> goodsList;
    // Constructor Getter Setter toString
}

public class Card {
    private String uuid;
    private Double money;
    private User user;
    // Constructor Getter Setter toString
}

public class Goods {
    private String uuid;
    private String name;
    private String description;
    private Double price;
    private List<User> userList;
    // Constructor Getter Setter toString
}
```

b. 工具类 UUIDUtil

```java
public class UUIDUtil {

    public static String getUUID(){
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    public static String getUUID(String s){
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", s);
    }

}
```

c. 配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!-- 加载数据库配置文件 -->
    <properties resource="jdbc.properties"/>

    <!-- 自定义别名 -->
    <typeAliases>
        <typeAlias type="cn.onecolour.association.domain.User" alias="user"/>
        <typeAlias type="cn.onecolour.association.domain.Card" alias="card"/>
        <typeAlias type="cn.onecolour.association.domain.Goods" alias="goods"/>
    </typeAliases>

    <!-- 数据源环境 -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.driver}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>

    <!-- 加载映射文件 -->
    <mappers>
        <mapper resource="cn/onecolour/association/mapper/UserMapper.xml"/>
        <mapper resource="cn/onecolour/association/mapper/CardMapper.xml"/>
        <mapper resource="cn/onecolour/association/mapper/GoodsMapper.xml"/>
    </mappers>

</configuration>
```

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://192.168.1.3/develop?useSSL=false&characterEncoding=utf-8
jdbc.username=root
jdbc.password=Yangbiaocc520.
```



d. 对应接口

UserMapper

CardMapper

GoodsMapper



e. 映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.onecolour.association.mapper.CardMapper">
<!-- 一个Mapper接口对应一个 接口名Mapper.xml -->
</mapper>
```



f. 表创建sql

```sql
CREATE TABLE user(
	uuid VARCHAR(32) PRIMARY KEY NOT NULL,
	username VARCHAR(20) NOT NULL UNIQUE,
	password VARCHAR(20) NOT NULL
) DEFAULT CHARSET=utf8;

CREATE TABLE goods(
	uuid VARCHAR(32) NOT NULL,
	name VARCHAR(20) NOT NULL,
	description VARCHAR(255),
	price double(12,2) DEFAULT '999999999.99',
	PRIMARY KEY(uuid)
) DEFAULT CHARSET=utf8;

CREATE TABLE user_goods(
	user_uuid VARCHAR(32) NOT NULL,
	goods_uuid VARCHAR(32) NOT NULL,
	PRIMARY KEY(user_uuid, goods_uuid),
	CONSTRAINT `user_goods_fk_user_uuid` FOREIGN KEY (user_uuid) REFERENCES user(uuid),
	CONSTRAINT `user_goods_fk_goods_uuid` FOREIGN KEY (goods_uuid) REFERENCES goods(uuid)
) DEFAULT CHARSET=utf8;

CREATE TABLE cards(
	uuid VARCHAR(32) NOT NULL,
	money double(12,2) DEFAULT '0.00',
	user_uuid VARCHAR(32) NOT NULL,
	PRIMARY KEY(uuid),
	CONSTRAINT `cards_fk_user_uuid` FOREIGN KEY (user_uuid) REFERENCES user(uuid)
	
) DEFAULT CHARSET=utf8;
```





##### (1) association 一对一级联

Card实体类中，一个Card对象对应一个User，因此查询Card时可以采用`association`级联查询

1. 首先在UserMapper.xml中新建一个查询

```xml
<!-- 根据UUID查询 -->
<select id="findByUuid" resultType="user" parameterType="String">
    SELECT * FROM user WHERE uuid=#{uuid}
</select>
```

在UserMapper接口中加入对应方法：

```java
public User findByUuid(String uuid);
```



2. 其次在CardMapper中进行级联查询

   * 建resultMap，id随意，type为实体全类名（在配置文件中进行了配置，这里可以简写，下同）

   ```xml
   <resultMap id="cardMap" type="card">
       <id property="uuid" column="uuid"/>
       <result property="money" column="money"/>
       <association property="user" column="user_uuid" select="cn.onecolour.association.mapper.UserMapper.findByUuid" />
   </resultMap>
   ```

   * 新建查询，将resultMap指定为上面所取的id

   ```xml
   <!-- 查询全部 -->
   <select id="findAll" resultMap="cardMap">
       SELECT uuid, money, user_uuid FROM cards
   </select>
   <!-- 根据UUID查询 -->
   <select id="findByUuid" resultMap="cardMap" parameterType="String">
       SELECT uuid, money, user_uuid FROM cards WHERE uuid=#{uuid}
   </select>
   ```



##### (2) collection 一对多级联

一对多，多对多（采用中间表——>双向一对多降低复杂度）

User与Card是一对多关系，即一个User可以拥有多张Card，且一张Card只能被一个User拥有

User与Goods是多对多关系，一个Goods可以被多个User拥有，一个User也可以拥有多个Goods



1. 首先说一对多关系 User-->Cards

   * UserMapper.xml

   ```xml
   <resultMap id="User" type="user">
       <id property="uuid" column="uuid" javaType="String"/>
       <result property="username" column="username" javaType="String"/>
       <result property="password" column="password" javaType="String"/>
       <collection property="cardList" column="uuid" select="findCardsByUserUuid"/>
   </resultMap>
   <!-- 查询全部with Card -->
   <select id="findAllWithCardsAndGoods" resultMap="User">
       SELECT * FROM user
   </select>
   <!-- 根据UserUUID查询Card-->
   <select id="findByUuidWithCardsAndGoods" parameterType="String" resultMap="User">
       SELECT * FROM user WHERE uuid=#{uuid}
   </select>
   
   <!-- 根据UserUuid查询Cards, 这个查询应该放在CardMapper.xml中，这里为了书写简便，将其放入UserMapper.xml-->
   <select id="findCardsByUserUuid" parameterType="String" resultType="card">
       SELECT * FROM cards WHERE user_uuid=#{user_uuid}
   </select>
   
   ```

   

2. 多对多级联 User-->Goods

   * UserMapper.xml

   ```xml
   <resultMap id="User" type="user">
       <id property="uuid" column="uuid" javaType="String"/>
       <result property="username" column="username" javaType="String"/>
       <result property="password" column="password" javaType="String"/>
       <collection property="goodsList" column="uuid" select="findGoodsByUserUuid"/>
   </resultMap>
   <!-- 查询全部with goods -->
   <select id="findAllWithGoods" resultMap="User">
       SELECT * FROM user
   </select>
   <!-- 根据UUID查询goods-->
   <select id="findByUuidWithGoods" parameterType="String" resultMap="User">
       SELECT * FROM user WHERE uuid=#{uuid}
   </select>
   <!-- 根据UserUuid查询Goods,这个查询应该放在UserMapper.xml中为了简便书写 -->
   <select id="findGoodsByUserUuid" parameterType="String" resultType="goods">
       SELECT * FROM user_goods, goods WHERE user_uuid=#{user_uuid} AND goods_uuid=goods.uuid
   </select>
   ```

   

#### 2.4.5 鉴别器

[鉴别器](https://mybatis.org/mybatis-3/zh/sqlmap-xml.html)



### 2.5 动态sql

Mybatis 的映射文件中，前面我们的 SQL 都是比较简单的，有些时候业务逻辑复杂时，我们的 SQL是动态变化的，此时在前面的学习中我们的 SQL 就不能满足要求了。

[动态SQL](https://mybatis.org/mybatis-3/zh/dynamic-sql.html)

使用动态 SQL 最常见情景是根据条件包含 where 子句的一部分

MyBatis3中动态SQL有以下几种元素种类

* if
* choose (when, otherwise)
* trim (where, set)
* foreach

#### 2.5.1 if

根据实体类的不同取值，使用不同的 SQL语句来进行查询。比如在 id如果不为空时可以根据id查询，如果username 不同空时还要加入用户名作为条件。这种情况在我们的多条件组合查询中经常会碰到。

```XML
<!-- 通过user实体查询 -->
<select id="findDetailByUser" parameterType="user" resultMap="User">
    SELECT * FROM user WHERE 1=1
    <if test="uuid != null">
        AND uuid=#{id}
    </if>
    <if test="username != null">
        AND username = #{username}
    </if>
</select>
```



```java
...
@Test
public void findDetailByUser() {
    User user = new User();
    user.setUsername("jack");
    user = mapper.findDetailByUser(user);
    System.out.println(user);
}
...
```

控制台打印如下

```
User{uuid='2A2E5C07BD634378BF9BB9D5DDBE9D1C', username='jack', password='1234567', cardList=[Card{uuid='0FBF5A2281CB4D198E68209DF86DAA6D', money=21.02, user=null}], goodsList=[Goods{uuid='4080ED9DE2654283B8DF8D4E9F4C4AA5', name='呼吸（横扫国际国内各大好书榜单，《纽约时报》年度十佳！豆瓣年度高分图书NO.1！奥巴马、蒋方舟诚挚推荐！）', description='豆瓣9.3！每一个“科幻必读”书单上都有特德·姜！《新周刊》、虎嗅网、界面文化年度好书，雨果奖、星云奖获奖作品', price=39.9, userList=null}, Goods{uuid='4836E09C266142098F722FDD2E47F56B', name='你一生的故事（特德·姜代表作，脑洞大开得遍世界大奖，始于科学内核，收于人文关怀，每一个“科幻必读”书单上都有特德·姜，刘慈欣、大卫·布', description='电影《降临》原著小说，得遍世界科幻大奖的开脑洞之作，万人五星推荐', price=29.9, userList=null}]}
```

**choose、when、otherwise**

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，MyBatis 提供了 choose 元素，它有点像 Java 中的 switch 语句。

上面的例子可以改为：

```xml
<!-- 通过user实体查询 -->
<select id="findDetailByUser" parameterType="user" resultMap="User">
    SELECT * FROM user WHERE 1=1
    <choose>
        <when test="uuid != null and username != null">
            AND uuid=#{id} AND username = #{username}
        </when>
        <when test="uuid != null">
            AND uuid=#{id}
        </when>
        <when test="username != null">
            AND username = #{username}
        </when>
        <otherwise>
            AND 1=2
        </otherwise>
    </choose>
</select>
```

**trim、where、set**

1. where 上面的查询可以修改为：

```xml
<!-- 通过user实体查询 -->
<select id="findDetailByUser" parameterType="user" resultMap="User">
    SELECT * FROM user
    <where>
        <if test="uuid != null">
            AND uuid=#{id}
        </if>
        <if test="username != null">
            AND username = #{username}
        </if>
    </where>
</select>
```



2. trim 

   * 使用trim去除多余逗号

     ```xml
     <update id="update" parameterType="user">
         UPDATE user
         <trim prefix="SET" suffixOverrides=",">
             <if test="username != null">username=#{username},</if>
             <if test="password != null">password=#{password}</if>
         </trim>
         WHERE uuid=#{uuid}
     </update>
     ```

以下是trim标签中涉及到的属性：

| 属性            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| prefix          | 给sql语句拼接的前缀                                          |
| suffix          | 给sql语句拼接的后缀                                          |
| prefixOverrides | 去除sql语句前面的关键字或者字符，该关键字或者字符由prefixOverrides属性指定，假设该属性指定为"AND"，当sql语句的开头为"AND"，trim标签将会去除该"AND"，可以用管道符隔开，如`AND | OR` |
| suffixOverrides | 去除sql语句后面的关键字或者字符，该关键字或者字符由suffixOverrides属性指定 |

* 使用trim代替where

```xml
<!-- 通过user实体查询 -->
<select id="findDetailByUser" parameterType="user" resultMap="User">
    SELECT * FROM user
    <trim prefix="WHERE" prefixOverrides="AND | OR">
        <if test="uuid != null">
            AND uuid=#{id}
        </if>
        <if test="username != null">
            AND username = #{username}
        </if>
    </trim>
</select>
```



3. set

用于动态更新语句的类似解决方案叫做 *set*。*set* 元素可以用于动态包含需要更新的列，忽略其它不更新的列。

```xml
<!-- 更新 -->
<update id="update" parameterType="user">
    UPDATE user
    <set>
        <if test="username != null">username=#{username},</if>
        <if test="password != null">password=#{password}</if>
    </set>
    WHERE uuid=#{uuid}
</update>
```

*set* 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。

#### 2.5.2  foreach

循环执行sql的拼接操作，例如：SELECT * FROM USER WHERE id IN (1,2,5)。

```xml
<select id="findByUuidList" parameterType="list" resultType="user">
    SELECT * FROM user WHERE uuid IN
    <foreach collection="list" open="(" separator="," close=")" index="0" item="uuid">
        #{uuid}
    </foreach>
</select>
```

* collection：代表要遍历的集合元素，注意编写时不要写#{}

* open：代表语句的开始部分

* close：代表结束部分

* item：代表遍历集合的每个元素，生成的变量名

* sperator：代表分隔符

可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 *foreach*。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。



#### SQL片段抽取

Sql 中可将重复的 sql 提取出来，使用时用 include 引用即可，最终达到 sql 重用的目的

```xml
<!-- SELECT * FROM user -->
<sql id="selectUser">
    SELECT * FROM user
</sql>
<!-- 查询全部 -->
<select id="findAll" resultType="user">
    <include refid="selectUser"/>
</select>
<!-- 根据UUID查询 -->
<select id="findByUuid" resultType="user" parameterType="String">
    <include refid="selectUser"/> <where> uuid=#{uuid}</where>
</select>
<!-- 根据UUID列表查询 -->
<select id="findByUuidList" parameterType="list" resultType="user">
    <include refid="selectUser"/>
    <where>
        uuid IN
        <foreach collection="list" open="(" separator="," close=")" index="0" item="uuid">
            #{uuid}
        </foreach>
    </where>
</select>
```



***



## 3. Plugins

MyBatis可以使用第三方的插件来对功能进行扩展，这里以分页助手[PageHelper](https://pagehelper.github.io/docs/howtouse/)为例进行演示



开发步骤

①导入通用PageHelper的坐标

②在mybatis核心配置文件中配置PageHelper插件

③测试分页数据获取

1. 导入通用PageHelper的坐标

```xml
<!-- PageHelper -->
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>3.7.5</version>
</dependency>
<dependency>
    <groupId>com.github.jsqlparser</groupId>
    <artifactId>jsqlparser</artifactId>
    <version>0.9.1</version>
</dependency>
```



2. 在mybatis核心配置文件中配置PageHelper插件

```xml
<plugins>
    <!-- PageHelper -->
    <plugin interceptor="com.github.pagehelper.PageHelper">
        <property name="helperDialect" value="mysql"/>
    </plugin>
</plugins>
```



3. 测试分页数据获取

```java
@Test
public void pageHelperTest() {
    PageHelper.startPage(1,2);
    List<User> users = mapper.findAll();
    for (User user : users) {
        System.out.println(user);
    }
}
```

获得分页相关其他参数：

```java
//其他分页的数据
PageInfo<User> pageInfo = new PageInfo<User>(users);
System.out.println("总条数："+pageInfo.getTotal());
System.out.println("总页数："+pageInfo.getPages());
System.out.println("当前页："+pageInfo.getPageNum());
System.out.println("每页显示长度："+pageInfo.getPageSize());
System.out.println("是否第一页："+pageInfo.isIsFirstPage());
System.out.println("是否最后一页："+pageInfo.isIsLastPage());


```



***





# 三、MyBatis注解开发

@Insert：实现新增

@Update：实现更新

@Delete：实现删除

@Select：实现查询

@Result：实现结果集封装

@Results：可以与@Result 一起使用，封装多个结果集

@One：实现一对一结果集封装

@Many：实现一对多结果集封装



简单示例

```java
public interface UserMapper {

    @Insert("INSERT INTO user(uuid, username, password)" +
            "VALUES (#{uuid},#{username},#{password})")
    void addUser(User user);

    @Select("SELECT * FROM user")
    @ResultType(User.class)
    List<User> findAll();

    @Select("SELECT * FROM user WHERE username=#{username}")
    @ResultType(User.class)
    User findUserByUsername(String username);

    @Update("UPDATE user SET username=#{username}, password=#{password} " +
            "WHERE uuid=#{uuid}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE uuid=#{uuid}")
    void deleteUser(User user);

    @Select("SELECT * FROM user")
    @Results(id = "User", value = {
        @Result(id = true, column = "uuid", property = "uuid", javaType = String.class),
        @Result(property = "username", column = "username"),
        @Result(property = "password", column = "password"),
        @Result(property = "cardList",column = "uuid",javaType = List.class,
                many = @Many(select = "cn.onecolour.annotation.mapper.CardMapper.findCardListByUserUuid")),
        @Result(property = "goodsList",column = "uuid",javaType = List.class,
                many = @Many(select = "cn.onecolour.annotation.mapper.GoodsMapper.findGoodsListByUserUuid")),

    })
    List<User> findAllWithCardAndGoods();
}

public interface GoodsMapper {
    @Select("SELECT * FROM user_goods, goods " +
            "WHERE user_uuid=#{user_uuid} AND goods_uuid=goods.uuid")
    @ResultType(Goods.class)
    List<Goods> findGoodsListByUserUuid(String UserUuid);
}


public interface CardMapper {
    @Select("SELECT * FROM cards WHERE user_uuid=#{user_uuid}")
    @ResultType(Card.class)
    List<Card> findCardListByUserUuid(String UserUuid);
}
```

Test

```java
public class UserMapperTest {
    private SqlSession sqlSession;
    UserMapper mapper;
    @Before
    public void before() throws IOException {
        //加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //获得sqlSession工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        //获得sqlSession对象
        sqlSession = sqlSessionFactory.openSession();
        mapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void addUser() {
        User user = new User(UUIDUtil.getUUID(),"Mary","1234567",null,null);
        mapper.addUser(user);
    }

    @Test
    public void findAll() {
        PageHelper.startPage(1,2);
        List<User> userList = mapper.findAll();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void findUserByUsername() {
        User user = mapper.findUserByUsername("Mary");
        System.out.println(user);
    }

    @Test
    public void updateUser() {
        User user = mapper.findUserByUsername("Mary");
        user.setPassword("abcdef");
        mapper.updateUser(user);
    }

    @Test
    public void deleteUser() {
        User user = mapper.findUserByUsername("Mary");
        mapper.deleteUser(user);
    }

    @Test
    public void findAllWithCardAndGoods() {
        List<User> userList = mapper.findAllWithCardAndGoods();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @After
    public void after(){
        sqlSession.commit();
        sqlSession.close();
    }
}
```



# 四、SpringMVC整合Mybatis

[Spring SpringMVC Mybatis整合](../../框架&others/框架整合/Spring+SpringMVC+MyBatis.md)











