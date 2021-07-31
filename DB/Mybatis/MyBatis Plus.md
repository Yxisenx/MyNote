# 一、Mybatis-plus概述

## 1. Mybatis-plus介绍

[MyBatis-Plus ](https://github.com/baomidou/mybatis-plus)（简称 MP）是一个 [MyBatis](http://www.mybatis.org/mybatis-3/)的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。该框架由baomidou（苞米豆）组织开发并且开源的。

官网：https://mybatis.plus/

<img src="../img/Mybatis Plus/mp1.png" alt="mp1" style="float:left;zoom:75%;" />

## 2. 支持数据库

* mysql mariadb oracle db2 h2 hsql sqlite postgresql sqlserver
* 达梦数据库 虚谷数据库 人大金仓数据库

>任何能使用 `mybatis` 进行 crud, 并且支持标准 sql 的数据库

## 3. 特性

* **无侵入**：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
* **损耗小**：启动即会自动注入基本 CURD，性能基本无损耗，直接面向对象操作
* **强大的 CRUD 操作**：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
* **支持 Lambda 形式调用**：通过 Lambda 表达式，方便的编写各类查询条件，无需再担心字段写错
* **支持主键自动生成**：支持多达 4 种主键策略（内含分布式唯一 ID 生成器 - Sequence），可自由配置，完美解决主键问题
* **支持 ActiveRecord 模式**：支持 ActiveRecord 形式调用，实体类只需继承 Model 类即可进行强大的 CRUD 操作
* **支持自定义全局通用操作**：支持全局通用方法注入（ Write once, use anywhere ）
* **内置代码生成器**：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置等您来使用
* **内置分页插件**：基于 MyBatis 物理分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通 List 查询
* **分页插件支持多种数据库**：支持 MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer 等多种数据库
* **内置性能分析插件**：可输出 Sql 语句以及其执行时间，建议开发测试时启用该功能，能快速揪出慢查询
* **内置全局拦截插件**：提供全表 delete 、 update 操作智能分析阻断，也可自定义拦截规则，预防误操作



## 4. 架构

<img src="../img/Mybatis Plus/mp2.jpg" alt="mp1" style="float:left;zoom:75%;" />

1. MP主要包含以下模块：

**核心功能(core)**，基于Mybatis的封装，提供了Mybatis Plus的基础配置类与核心功能，如内置通用 Mapper，Lambda 表达式查询等。

**注解(annotation)**: 提供了Mybatis Plus中注解的定义。

**扩展功能(extension)**: 提供扩展及插件功能，包括分页插件 通用 Service扩展 性能分析插件等。

**代码生成器(generator)**: 通过代码生成器可以快速生成 Mapper接口 Entity实体类 Mapper XML Service Controller 等各个模块的代码，极大的提升了开发效率。

2. 执行流程

执行流程：

（1）扫描注解Entity，反射提取注解信息如：表名称 字段名称等信息。

（2）分析注解信息并基于com.baomidou.mybatisplus.core.enums的SQL模板生成基本CRUD SQL。

（3）最后将这些SQL注入到Mybatis环境中。

因此Mybatis plus无需编写CRUD SQL语句，只需继承BaseMapper，魔术般的拥有了CRUD功能(通用CRUD)。



# 二、MP整合

[示例](https://gitee.com/baomidou/mybatis-plus-samples)

## 1. 整合spring

[SpringMVC + MP](https://gitee.com/baomidou/mybatisplus-spring-mvc)

### 1.1 pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.onecolour</groupId>
    <artifactId>mp-mvc</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <spring.version>5.2.13.RELEASE</spring.version>
        <aspectj.version>1.9.6</aspectj.version>
        <jackson.version>2.11.4</jackson.version>
    </properties>

    <dependencies>
        <!-- logging -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.30</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.4.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.23</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.23</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.tomcat</groupId>
            <artifactId>annotations-api</artifactId>
            <version>6.0.29</version>
        </dependency>

        <!-- IOC -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- AOP -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>${aspectj.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- mvc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- web -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.taglibs</groupId>
            <artifactId>taglibs-standard-impl</artifactId>
            <version>1.2.5</version>
            <scope>provided</scope>
        </dependency>

        <!-- test -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.18</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>shiro-spring-mvc</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <uriEncoding>utf-8</uriEncoding>
                    <port>80</port>
                    <path>/</path>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 1.2 applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="cn.onecolour.mp.mvc">
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
        <context:exclude-filter type="annotation" expression="org.springframework.web.bind.annotation.ControllerAdvice"/>
    </context:component-scan>

    <import resource="classpath:applicationContext-mapper.xml"/>

</beans>
```

### 1.3 spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 包扫描 -->
    <context:component-scan base-package="cn.onecolour.mp.mvc.controller"/>

    <!-- 注解驱动 -->
    <mvc:annotation-driven/>

    <!-- 使用默认的Servlet来响应静态文件 -->
    <mvc:default-servlet-handler/>

    <!-- 请求映射处理适配器中配置消息处理器 -->
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
        <property name="messageConverters">
            <list>
                <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                    <property name="defaultCharset" value="utf-8"/>
                </bean>
                <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                    <property name="supportedMediaTypes">
                        <list>
                            <bean class="org.springframework.http.MediaType">
                                <constructor-arg name="type" value="text"/>
                                <constructor-arg name="subtype" value="plain"/>
                                <constructor-arg name="charset" value="UTF-8"/>
                            </bean>
                            <bean class="org.springframework.http.MediaType">
                                <constructor-arg name="type" value="*"/>
                                <constructor-arg name="subtype" value="*"/>
                                <constructor-arg name="charset" value="UTF-8"/>
                            </bean>
                            <bean class="org.springframework.http.MediaType">
                                <constructor-arg name="type" value="text"/>
                                <constructor-arg name="subtype" value="*"/>
                                <constructor-arg name="charset" value="UTF-8"/>
                            </bean>
                            <bean class="org.springframework.http.MediaType">
                                <constructor-arg name="type" value="application"/>
                                <constructor-arg name="subtype" value="json"/>
                                <constructor-arg name="charset" value="UTF-8"/>
                            </bean>
                        </list>
                    </property>
                </bean>
            </list>
        </property>
    </bean>
</beans>
```

### 1.4 applicationContext-mapper.xml

`jdbc.properties`

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://192.168.1.3/test?useSSL=false&characterEncoding=utf-8
jdbc.username=yang
jdbc.password=yang
jdbc.initialSize=5
jdbc.maxActive=50
jdbc.maxWait=5000
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
       http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 1. 导入数据库配置文件 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!-- 2. 数据源 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="initialSize" value="${jdbc.initialSize}"/>
        <property name="maxActive" value="${jdbc.maxActive}"/>
        <property name="maxWait" value="${jdbc.maxWait}"/>
    </bean>
    <!-- Spring 整合 MP MybatisSqlSessionFactoryBean-->
    <bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 别名设置 -->
        <property name="typeAliasesPackage" value="cn.onecolour.mp.mvc.entity"/>
        <!-- mybatis配置文件位置 -->
        <!--<property name="configLocation" value="classpath:mybatis.xml"/>-->
        <!-- 加载sql映射文件 -->
        <!--<property name="mapperLocations" value="classpath:mapper/*.xml"/>-->
        <property name="plugins">
            <array>
                <ref bean="mybatisPlusInterceptor"/>
            </array>
        </property>
    </bean>

<!--    <bean id="configuration" class="com.baomidou.mybatisplus.core.MybatisConfiguration">
        <property name="useDeprecatedExecutor" value="false"/>
    </bean>-->

    <!-- mp 拦截器 -->
    <bean id="mybatisPlusInterceptor" class="com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor">
        <property name="interceptors">
            <list>
                <ref bean="paginationInnerInterceptor"/>
            </list>
        </property>
    </bean>

    <!-- 自动分页插件 -->
    <bean id="paginationInnerInterceptor" class="com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor">
        <property name="dbType" value="MYSQL"/>
        <property name="maxLimit" value="50"/>
    </bean>
    <bean id="countSqlParser" class="com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize">
        <!-- 设置为 true 可以优化部分 join 的sql -->
        <property name="optimizeJoin" value="true"/>
    </bean>
    <!--  mapper扫描器，用于产生代理对象 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="cn.onecolour.mp.mvc.mapper"/>
    </bean>



    <!-- 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!-- 事务注解驱动-->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    <!-- 事务管理 -->
    <tx:advice id="transactionInterceptor" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="insert*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="delete*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="select*" read-only="true"/>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="count*" read-only="true"/>
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    <!-- 事务aop织入 -->
    <aop:config>
        <aop:pointcut id="serviceMethods" expression="execution(* cn.onecolour.mp.mvc.service.impl.*.*(..))"/>
        <aop:advisor advice-ref="transactionInterceptor" pointcut-ref="serviceMethods"/>
    </aop:config>
</beans>
```

### 1.5 Mapper、实体、Service层、实体映射文件

#### <span id="mybatisx-generator">1.5.1 使用MybatisX插件生成</span>

<img src="../img/Mybatis Plus/mp2.png" alt="mp1" style="float:left" />

<img src="../img/Mybatis Plus/mp3.png" alt="mp1" style="float:left" />

<img src="../img/Mybatis Plus/mp4.png" alt="mp1" style="float:left" />

#### 1.5.2 自行编写

##### 实体

```java
@TableName(value ="user")
@Getter
@Setter
public class User implements Serializable {
    /**
     * user id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    /**
     * QQ
     */
    private String qq;

    /**
     * 邮箱
     */
    private String email;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("[");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", gender=").append(gender);
        sb.append(", age=").append(age);
        sb.append(", address=").append(address);
        sb.append(", qq=").append(qq);
        sb.append(", email=").append(email);
        sb.append("]");
        return sb.toString();
    }

}
```

##### Mapper接口

```java
package cn.onecolour.mp.mvc.mapper;

import cn.onecolour.mp.mvc.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {

}
```

##### Service层

```java
package cn.onecolour.mp.mvc.service;

import cn.onecolour.mp.mvc.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    public User findById(Integer id);
}
```

```java
package cn.onecolour.mp.mvc.service.impl;

import cn.onecolour.mp.mvc.entity.User;
import cn.onecolour.mp.mvc.mapper.UserMapper;
import cn.onecolour.mp.mvc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
}
```

### 1.6 测试

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:spring-mvc.xml"})
public class UserServiceTest {

    @Autowired
    UserService userService;
    /**
     * 通用查询操作  通过ID查询
     */
    @Test
    public void testFindById() {
        System.out.println(userService.findById(5));
    }
    
}
```

测试结果：

<img src="../img/Mybatis Plus/mp5.png" alt="mp1" style="float:left" />

## 2. 整合Spring boot

### 2.1 导包

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.onecolour</groupId>
    <artifactId>mp-boot</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <parent>
        <artifactId>spring-boot-starter-parent</artifactId>
        <groupId>org.springframework.boot</groupId>
        <version>2.3.9.RELEASE</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.5</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

### 2.2 application.yml

```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.1.3/test?useSSL=false&characterEncoding=utf-8
    username: yang
    password: yang
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      initial-size: 5
      max-active: 50
      max-wait: 5000
mybatis-plus:
  mapper-locations: classpath:/mapper/*.xml
```

### 2.3 自动生成Mapper和映射文件 

[同上](#mybatisx-generator)

### 2.4 Service层

```java
package cn.onecolour.mp.boot.service;

import cn.onecolour.mp.boot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User findById(Integer id);
}
```

```java
package cn.onecolour.mp.boot.service.impl;

import cn.onecolour.mp.boot.entity.User;
import cn.onecolour.mp.boot.mapper.UserMapper;
import cn.onecolour.mp.boot.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
}
```

### 2.5 Controller

```java
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public Object search(@PathVariable Integer id){
        return userService.findById(id);
    }
}
```

### 2.6 启动程序

```java
@SpringBootApplication
@ImportResource("classpath:applicationContext-transaction.xml") // 导入spring配置文件
@EnableTransactionManagement // 开启事务管理
@MapperScan(basePackages = "cn.onecolour.mp.boot.mapper") // 扫描mapper
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }
}
```

### 2.7 事务管理

#### 2.7.1 xml方式

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!-- 事务注解驱动-->
    <!-- 如果开启会提示Bean已注册，未开启override -->
    <!--<tx:annotation-driven transaction-manager="transactionManager"/>-->
    <!-- 事务管理 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="insert*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="delete*" propagation="REQUIRED" rollback-for="java.lang.Throwable"/>
            <tx:method name="select*" read-only="true"/>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="count*" read-only="true"/>
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    <!-- 事务aop织入 -->
    <aop:config>
        <aop:pointcut id="serviceMethods" expression="execution(* cn.onecolour.mp.boot.service.impl.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="serviceMethods"/>
    </aop:config>
</beans>
```

#### 2.7.2 配置类方式

```java
package cn.onecolour.mp.boot.config;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class TransactionConfig {
    @Autowired
    private DataSource dataSource;

    @Bean("txManager")
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /* 事务管理 */
    @Bean("txAdvice")
    public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap( txMap );
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(txManager);
        interceptor.setTransactionAttributeSource(source);
        return interceptor;
    }

    /* 切面拦截规则 参数会自动从容器中注入 */
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution (* cn.onecolour.mp.boot.service.impl.*.*(..))");
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }

}

```

### 2.8 MP分页插件配置

```java
package cn.onecolour.mp.boot.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class MPConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(50L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
    /**
     * Sql优化
     */
    @Bean
    public JsqlParserCountOptimize countSqlParser(){
        JsqlParserCountOptimize optimize = new JsqlParserCountOptimize();
        optimize.setOptimizeJoin(true);
        return optimize;
    }

}

```



# 三、CRUD接口

[CRUD接口](https://mybatis.plus/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3)

## 1. Service CRUD接口

>说明:
>
>* 通用 Service CRUD 封装[IService](https://gitee.com/baomidou/mybatis-plus/blob/3.0/mybatis-plus-extension/src/main/java/com/baomidou/mybatisplus/extension/service/IService.java)接口，进一步封装 CRUD 采用 `get 查询单行` `remove 删除` `list 查询集合` `page 分页` 前缀命名方式区分 `Mapper` 层避免混淆，
>* 泛型 `T` 为任意实体对象
>* 建议如果存在自定义通用 Service 方法的可能，请创建自己的 `IBaseService` 继承 `Mybatis-Plus` 提供的基类
>* 对象 `Wrapper` 为 [条件构造器](https://mybatis.plus/guide/wrapper.html)



### 1.1 Save

```java
// 插入一条记录（选择字段，策略插入）
boolean save(T entity);
// 插入（批量）
boolean saveBatch(Collection<T> entityList);
// 插入（批量）
boolean saveBatch(Collection<T> entityList, int batchSize);
```

|      类型      |   参数名   |     描述     |
| :------------: | :--------: | :----------: |
|       T        |   entity   |   实体对象   |
| Collection\<T> | entityList | 实体对象集合 |
|      int       | batchSize  | 插入批次数量 |

### 1.2 SaveOrUpdate

```java
// TableId 注解存在更新记录，否插入一条记录
boolean saveOrUpdate(T entity);
// 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);
// 批量修改插入
boolean saveOrUpdateBatch(Collection<T> entityList);
// 批量修改插入
boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
```

|     类型      |    参数名     |               描述               |
| :-----------: | :-----------: | :------------------------------: |
|       T       |    entity     |             实体对象             |
|  Wrapper<T>   | updateWrapper | 实体对象封装操作类 UpdateWrapper |
| Collection<T> |  entityList   |           实体对象集合           |
|      int      |   batchSize   |           插入批次数量           |

### 1.3 Remove

```java
// 根据 entity 条件，删除记录
boolean remove(Wrapper<T> queryWrapper);
// 根据 ID 删除
boolean removeById(Serializable id);
// 根据 columnMap 条件，删除记录
boolean removeByMap(Map<String, Object> columnMap);
// 删除（根据ID 批量删除）
boolean removeByIds(Collection<? extends Serializable> idList);
```

|                类型                |    参数名    |          描述           |
| :--------------------------------: | :----------: | :---------------------: |
|            Wrapper\<T>             | queryWrapper | 实体包装类 QueryWrapper |
|            Serializable            |      id      |         主键ID          |
|        Map<String, Object>         |  columnMap   |     表字段 map 对象     |
| Collection<? extends Serializable> |    idList    |       主键ID列表        |

### 1.4 Update

```java
// 根据 UpdateWrapper 条件，更新记录 需要设置sqlset
boolean update(Wrapper<T> updateWrapper);
// 根据 whereWrapper 条件，更新记录
boolean update(T updateEntity, Wrapper<T> whereWrapper);
// 根据 ID 选择修改
boolean updateById(T entity);
// 根据ID 批量更新
boolean updateBatchById(Collection<T> entityList);
// 根据ID 批量更新
boolean updateBatchById(Collection<T> entityList, int batchSize);
```

|      类型      |    参数名     |               描述               |
| :------------: | :-----------: | :------------------------------: |
|  Wrapper\<T>   | updateWrapper | 实体对象封装操作类 UpdateWrapper |
|       T        |    entity     |             实体对象             |
| Collection\<T> |  entityList   |           实体对象集合           |
|      int       |   batchSize   |           更新批次数量           |

### 1.5 Get

```java
// 根据 ID 查询
T getById(Serializable id);
// 根据 Wrapper，查询一条记录。结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
T getOne(Wrapper<T> queryWrapper);
// 根据 Wrapper，查询一条记录
T getOne(Wrapper<T> queryWrapper, boolean throwEx);
// 根据 Wrapper，查询一条记录
Map<String, Object> getMap(Wrapper<T> queryWrapper);
// 根据 Wrapper，查询一条记录
<V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
```

|            类型             |    参数名    |              描述               |
| :-------------------------: | :----------: | :-----------------------------: |
|        Serializable         |      id      |             主键ID              |
|         Wrapper\<T>         | queryWrapper | 实体对象封装操作类 QueryWrapper |
|           boolean           |   throwEx    |   有多个 result 是否抛出异常    |
|              T              |    entity    |            实体对象             |
| Function<? super Object, V> |    mapper    |            转换函数             |

### 1.6 List

```java
// 查询所有
List<T> list();
// 查询列表
List<T> list(Wrapper<T> queryWrapper);
// 查询（根据ID 批量查询）
Collection<T> listByIds(Collection<? extends Serializable> idList);
// 查询（根据 columnMap 条件）
Collection<T> listByMap(Map<String, Object> columnMap);
// 查询所有列表
List<Map<String, Object>> listMaps();
// 查询列表
List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);
// 查询全部记录
List<Object> listObjs();
// 查询全部记录
<V> List<V> listObjs(Function<? super Object, V> mapper);
// 根据 Wrapper 条件，查询全部记录
List<Object> listObjs(Wrapper<T> queryWrapper);
// 根据 Wrapper 条件，查询全部记录
<V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);
```

|                类型                 |    参数名    |              描述               |
| :---------------------------------: | :----------: | :-----------------------------: |
|             Wrapper\<T>             | queryWrapper | 实体对象封装操作类 QueryWrapper |
| Collection\<? extends Serializable> |    idList    |           主键ID列表            |
|        Map\<?String, Object>        |  columnMap   |         表字段 map 对象         |
|    Function\<? super Object, V>     |    mapper    |            转换函数             |

### 1.7 Page

```java
// 无条件分页查询
IPage<T> page(IPage<T> page);
// 条件分页查询
IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
// 无条件分页查询
IPage<Map<String, Object>> pageMaps(IPage<T> page);
// 条件分页查询
IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);
```

|    类型     |    参数名    |              描述               |
| :---------: | :----------: | :-----------------------------: |
|  IPage\<T>  |     page     |            翻页对象             |
| Wrapper\<T> | queryWrapper | 实体对象封装操作类 QueryWrapper |



### 1.8 Count

```java
// 查询总记录数
int count();
// 根据 Wrapper 条件，查询总记录数
int count(Wrapper<T> queryWrapper);
```

|    类型     |    参数名    |              描述               |
| :---------: | :----------: | :-----------------------------: |
| Wrapper\<T> | queryWrapper | 实体对象封装操作类 QueryWrapper |

### 1.9 Chain

#### 1.9.1 query

```java
// 链式查询 普通
QueryChainWrapper<T> query();
// 链式查询 lambda 式。注意：不支持 Kotlin
LambdaQueryChainWrapper<T> lambdaQuery(); 

// 示例：
QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
userQueryWrapper
    .between("age", 19, 22)
    .eq("gender", "男")
    .likeRight("name", "张");
userService.list(userQueryWrapper).forEach(System.out::println);
query().eq("column", value).one();
lambdaQuery().eq(Entity::getId, value).list();
```

#### 1.9.2 update

```java
// 链式更改 普通
UpdateChainWrapper<T> update();
// 链式更改 lambda 式。注意：不支持 Kotlin 
LambdaUpdateChainWrapper<T> lambdaUpdate();

// 示例：
update().eq("column", value).remove();
lambdaUpdate().eq(Entity::getId, value).update(entity);
```



## 2. Mapper CRUD接口

### 2.1 Insert

```java
// 插入一条记录
int insert(T entity);
```

| 类型 | 参数名 |   描述   |
| :--: | :----: | :------: |
|  T   | entity | 实体对象 |

### 2.2 Delete

```java
// 根据 entity 条件，删除记录
int delete(@Param(Constants.WRAPPER) Wrapper<T> wrapper);
// 删除（根据ID 批量删除）
int deleteBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
// 根据 ID 删除
int deleteById(Serializable id);
// 根据 columnMap 条件，删除记录
int deleteByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
```

|                类型                |  参数名   |                描述                |
| :--------------------------------: | :-------: | :--------------------------------: |
|            Wrapper\<T>             |  wrapper  | 实体对象封装操作类（可以为 null）  |
| Collection<? extends Serializable> |  idList   | 主键ID列表(不能为 null 以及 empty) |
|            Serializable            |    id     |               主键ID               |
|        Map<String, Object>         | columnMap |          表字段 map 对象           |

### 2.3 Update

```java
// 根据 whereWrapper 条件，更新记录
int update(@Param(Constants.ENTITY) T updateEntity, @Param(Constants.WRAPPER) Wrapper<T> whereWrapper);
// 根据 ID 修改
int updateById(@Param(Constants.ENTITY) T entity);
```

|    类型     |    参数名     |                             描述                             |
| :---------: | :-----------: | :----------------------------------------------------------: |
|      T      |    entity     |               实体对象 (set 条件值,可为 null)                |
| Wrapper\<T> | updateWrapper | 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句） |

### 2.4 Select

```java
// 根据 ID 查询
T selectById(Serializable id);
// 根据 entity 条件，查询一条记录
T selectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

// 查询（根据ID 批量查询）
List<T> selectBatchIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);
// 根据 entity 条件，查询全部记录
List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
// 查询（根据 columnMap 条件）
List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> columnMap);
// 根据 Wrapper 条件，查询全部记录
List<Map<String, Object>> selectMaps(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
// 根据 Wrapper 条件，查询全部记录。注意： 只返回第一个字段的值
List<Object> selectObjs(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);

// 根据 entity 条件，查询全部记录（并翻页）
IPage<T> selectPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
// 根据 Wrapper 条件，查询全部记录（并翻页）
IPage<Map<String, Object>> selectMapsPage(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
// 根据 Wrapper 条件，查询总记录数
Integer selectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
```

|                类型                |    参数名    |                  描述                   |
| :--------------------------------: | :----------: | :-------------------------------------: |
|            Serializable            |      id      |                 主键ID                  |
|             Wrapper<T>             | queryWrapper |    实体对象封装操作类（可以为 null）    |
| Collection<? extends Serializable> |    idList    |   主键ID列表(不能为 null 以及 empty)    |
|        Map<String, Object>         |  columnMap   |             表字段 map 对象             |
|              IPage<T>              |     page     | 分页查询条件（可以为 RowBounds.DEFAULT) |



# 四、条件构造器

[条件构造器](https://mybatis.plus/guide/wrapper.html#abstractwrapper)

## 1. Wrapper类体系图

<img src="../img/Mybatis Plus/mp6.png" alt="mp1" style="float:left;zoom:75%;" />

## 2. Wrapper介绍

Wrapper ： 条件构造抽象类，最顶端父类，抽象类中提供4个方法贴源码展示

AbstractWrapper ： 用于查询条件封装，生成 sql 的 where 条件

AbstractLambdaWrapper ： Lambda 语法使用 Wrapper统一处理解析 lambda 获取 column。

LambdaQueryWrapper ：看名称也能明白就是用于Lambda语法使用的查询Wrapper

LambdaUpdateWrapper ： Lambda 更新封装Wrapper

QueryWrapper ： Entity 对象封装操作类，不是用lambda语法

UpdateWrapper ： Update 条件封装，用于Entity对象更新操作

>QueryWrapper(LambdaQueryWrapper) 和 UpdateWrapper(LambdaUpdateWrapper) 的父类
>用于生成 sql 的 where 条件, entity 属性也用于生成 sql 的 where 条件
>注意: entity 生成的 where 条件与 使用各个 api 生成的 where 条件没有任何关联行为

## 3. 常用方法

<img src="../img/Mybatis Plus/mp7.png" alt="mp1" style="float:left;zoom:75%;" />



## 4. 示例

<span style="color:red">注意:：</span>如果开启了SQL优化，page.getTotal()数量不准确，应该自行编写sql查询页数

```java
QueryWrapper<User> queryWrapper = new QueryWrapper<>();
queryWrapper
    .between("age", 19, 22)
    .eq("gender", "男")
    .like("name", "一");
List<User> userList;
long i = 0;
userList = userService.page(new Page<>(i, 3), queryWrapper).getRecords();
while (userList != null && userList.size() > 0) {
    System.out.println(i+": "+userList);
    userList = userService.page(new Page<>(i++, 3), queryWrapper).getRecords();
}
```



# 五、分页插件

[分页插件](https://mybatis.plus/guide/page.html)

