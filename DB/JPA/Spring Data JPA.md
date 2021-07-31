[TOC]

# 一、Spring Data JPA 的概述

>Spring Data JPA, part of the larger Spring Data family, makes it easy to easily implement JPA based repositories. This module deals with enhanced support for JPA based data access layers. It makes it easier to build Spring-powered applications that use data access technologies.

Spring Data JPA 是 Spring 基于 ORM 框架、JPA 规范的基础上封装的一套JPA应用框架，可使开发者用极简的代码即可实现对数据库的访问和操作。它提供了包括增删改查等在内的常用功能，且易于扩展！学习并使用 Spring Data JPA 可以极大提高开发效率！

Spring Data JPA 让我们解脱了DAO层的操作，基本上所有CRUD都可以依赖于它来实现,在实际的工作工程中，推荐使用Spring Data JPA + ORM（如：hibernate）完成操作，这样在切换不同的ORM框架时提供了极大的方便，同时也使数据库层操作更加简单，方便解耦



## 1. Spring Data JPA 的特性

>* Sophisticated support to build repositories based on Spring and JPA
>* Support for [Querydsl](http://www.querydsl.com/) predicates and thus type-safe JPA queries
>* Transparent auditing of domain class
>* Pagination support, dynamic query execution, ability to integrate custom data access code
>* Validation of `@Query` annotated queries at bootstrap time
>* Support for XML based entity mapping
>* JavaConfig based repository configuration by introducing `@EnableJpaRepositories`.

SpringData Jpa 极大简化了数据库访问层代码。 如何简化的呢？ 使用了SpringDataJpa，我们的dao层中只需要写接口，就自动具有了增删改查、分页查询等方法。



## 2. Spring Data JPA 与 JPA和hibernate之间的关系

JPA是一套规范，内部是有接口和抽象类组成的。hibernate是一套成熟的ORM框架，而且Hibernate实现了JPA规范，所以也可以称hibernate为JPA的一种实现方式，我们使用JPA的API编程，意味着站在更高的角度上看待问题（面向接口编程）

 

Spring Data JPA是Spring提供的一套对JPA操作更加高级的封装，是在JPA规范下的专门用来进行数据持久化的解决方案。



# 二、Spring Data JPA 快速入门

使用Spring Data JPA完成客户的基本CRUD操作

## 1. pom.xml 导入坐标

```xml
<properties>
    <spring.version>5.2.13.RELEASE</spring.version>
    <hibernate.version>5.4.28.Final</hibernate.version>
    <log4j.version>1.2.17</log4j.version>
    <slf4j.version>1.7.30</slf4j.version>
    <mysql.version>5.1.49</mysql.version>
</properties>

<dependencies>
    <!-- Spring Begin -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.6</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-orm</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <!-- Spring end -->

    <!-- spring data jpa -->
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jpa</artifactId>
        <version>2.3.7.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>javax.el</groupId>
        <artifactId>javax.el-api</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>javax.el</artifactId>
        <version>3.0.0</version>
    </dependency>

    <!-- spring data jpa end -->

    <!-- hibernate begin -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-c3p0</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>6.1.7.Final</version>
    </dependency>
    <!-- hibernate end -->

    <!-- log -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <!-- mysql -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>


    <!-- junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>${spring.version}</version>
    </dependency>
    
    <!-- lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.18</version>
        </dependency>

</dependencies>
```



## 2. 整合Spring Data JPA 与 Spring

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:jpa="http://www.springframework.org/schema/data/jpa" xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">
    
    <!-- 1. 数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="jdbcUrl" value="jdbc:mysql://192.168.1.3:3306/ssm_demo"/>
        <property name="driverClass" value="com.mysql.jdbc.Driver"/>
        <property name="user" value="yang"/>
        <property name="password" value="yang"/>
    </bean>

    <!-- 2. entityManagerFactory -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="packagesToScan" value="cn.onecolour.springJpa.entity"/>
        <property name="persistenceProvider">
            <bean class="org.hibernate.jpa.HibernatePersistenceProvider"/>
        </property>
        <!--JPA的供应商适配器-->
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <property name="generateDdl" value="false"/>
                <property name="database" value="MYSQL"/>
                <property name="databasePlatform" value="org.hibernate.dialect.MySQLDialect"/>
                <property name="showSql" value="true"/>
                <property name="prepareConnection" value="true"/>
            </bean>
        </property>
        <property name="jpaDialect">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaDialect"/>
        </property>
    </bean>

    <!-- 3. 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>
    <!-- 整合spring data jpa -->
    <jpa:repositories base-package="cn.onecolour.springJpa.dao"
                      transaction-manager-ref="transactionManager"
                      entity-manager-factory-ref="entityManagerFactory"/>

    <!-- 4. txAdvice -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="save*" propagation="REQUIRED"/>
            <tx:method name="insert*" propagation="REQUIRED"/>
            <tx:method name="delete*" propagation="REQUIRED"/>
            <tx:method name="update*" propagation="REQUIRED"/>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="find*" read-only="true"/>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!-- 5. aop -->
    <aop:config>
        <aop:pointcut id="servicePointcut" expression="execution(* cn.onecolour.springJpa.service.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="servicePointcut"/>
    </aop:config>
</beans>
```



## 3. 配置实体类

```java
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data // lombok 自动生成getter setter and toString
@Entity //声明实体类
@Table(name = "goods") // 指定表名
@GenericGenerator(name = "jpa-uuid", strategy = "uuid") // 主键生成器
public class Book {
    @Id // 声明当前属性为主键
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;
}
```



## 4. 编写DAO层

Spring Data JPA是spring提供的一款对于数据访问层（Dao层）的框架，使用Spring Data JPA，只需要按照框架的规范提供dao接口，不需要实现类就可以完成数据库的增删改查、分页查询等方法的定义，极大的简化了我们的开发过程。

在Spring Data JPA中，对于定义符合规范的Dao层接口，我们只需要遵循以下几点就可以了：

* 创建一个Dao层接口，并实现JpaRepository和JpaSpecificationExecutor
* 提供相应的泛型

```java
package cn.onecolour.springJpa.repository;

import cn.onecolour.springJpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 */

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

}
```

这样就定义好了一个符合Spring Data JPA规范的Dao层接口



## 5. 测试

```java
import cn.onecolour.springJpa.entity.Book;
import cn.onecolour.springJpa.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JpaTest1 {
    @Autowired
    private BookRepository bookRepository;

    /**
     * 对于save方法的解释：如果执行此方法是对象中存在id属性，即为更新操作会先根据id查询，再更新
     * 如果执行此方法中对象中不存在id属性，即为保存操作
     */
    @Test
    public void testSave() {
        Book book = new Book();
        book.setName("机关师之玄玉归藏");
        book.setDescription("中国机关师小说开山之作！ 腾讯超人气漫画《守墓笔记之机关师》同人小说重磅来袭!");
        book.setPrice(28.9);
        bookRepository.save(book);
        System.out.println(book.getUuid());
    }

    @Test
    public void testFindOne() {
        Book book = new Book();
        book.setUuid("402881827848fb18017848fb1dc60000");
        Example<Book> example = Example.of(book);
        Optional<Book> optional = bookRepository.findOne(example);
        System.out.println(optional);
    }

    /**
     * getOne()   返回一个实体对象的引用，为空会抛出异常。
     * 在实体类上加入注解：@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
     * 或 @Proxy(lazy = false)可排除异常
     * findOne()  返回一个Optional对象,可以实现条件查询
     * findById() 返回一个Optional对象
     * Optional 可以存null，是实体对象的包装对象。可以通过get方法获取到实体对象：
     * optional.get()可以从Optional中取出对象，
     * 可以先用option.isPresent()进行判断
     * 当optional中存在对象时，optional.isPresent()值为true，通过get（）方法返回对象。
     */

    @Test
    public void testFindById() {
        Optional<Book> optional = bookRepository.findById("402881827848fb18017848fb1dc60000");
        System.out.println(optional);
    }

    @Test
    public void testUpdate() {
        Optional<Book> optional = bookRepository.findById("402881827848fb18017848fb1dc60000");
        if (optional.isPresent()){
            Book book = optional.get();
            book.setPrice(book.getPrice()+0.01);
            bookRepository.save(book);
        }
    }

/*    @Test
    public void testDeleteById() {
        bookRepository.deleteById("402881827848fb18017848fb1dc60000");
    }*/

/*    @Test
    public void testDelete() {
        Optional<Book> optional = bookRepository.findById("402881827848fb18017848fb1dc60000");
        if (optional.isPresent()){
            Book book = optional.get();
            bookRepository.delete(book);
        }
    }*/
}
```



# 三、Spring Data JPA 实现过程



# 四、Spring Data JPA 查询

## 1. 使用Spring Data JPA中接口定义的方法进行查询

在继承JpaRepository，和JpaSpecificationExecutor接口后,我们就可以使用接口中定义的方法进行查询

<img src="img/Spring Data JPA/JpaRespository方法.png" alt="JpaRespository方法" style="float:left" />

<img src="img/Spring Data JPA/JpaSpecificationExecutor.JPG" alt="JpaSpecificationExecutor" style="float:left" />



## 2.  使用JPQL的方式查询



使用Spring Data JPA提供的查询方法已经可以解决大部分的应用场景，但是对于某些业务来说，我们还需要灵活的构造查询条件，这时就可以使用@Query注解，结合JPQL的语句方式完成查询

@Query 注解的使用非常简单，只需在方法上面标注该注解，同时提供一个JPQL查询语句即可

demo

```java
import cn.onecolour.springJpa.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * JpaRepository<实体类类型，主键类型>：用来完成基本CRUD操作
 * JpaSpecificationExecutor<实体类类型>：用于复杂查询（分页等查询操作）
 */

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {

    /**
     * // @Query 使用jpql的方式查询。
     */
    @Query(value = "from Book ")
    public List<Book> findAllBook();
    /**
     * //@Query 使用jpql的方式查询。?1代表参数的占位符，其中1对应方法中的参数索引
     */
    @Query(value = "from Book where name = ?1 ")
    public Book findBookByName(String name);

    /**
     * 也可以通过使用 @Query 来执行一个更新操作，
     * 在使用 @Query 的同时，用 @Modifying 来将该操作标识为修改查询
     * 这样框架最终会生成一个更新的操作，而非查询
     */
    @Query(value = "update Book set name = ?1, description = ?2, price = ?3 where uuid = ?4 ")
    @Modifying
    public void updateBook(String name, String description, Double price, String uuid);

    /**
     *  使用@Param注解注入参数
     */
    @Query(value = "from Book where description like %:description%")
    public List<Book> findBookByDescription(@Param("description") String description);

}

```



## 3. 使用 原生sql语句查询

demo

```java
	/**
     * 使用原生sql语句查询
     */
@Query(value = "select * from goods where price > :start and price <= :end", nativeQuery = true)
public List<Book> findBookByPriceStartingWithAndPriceEndingWith(@Param("start") Double start, @Param("end") Double end);
```



## 4. 方法命名规则

方法命名规则查询就是根据方法的名字，就能创建查询。只需要按照Spring Data JPA提供的方法命名规则定义方法的名称，就可以完成查询工作。Spring Data JPA在程序执行的时候会根据方法名称进行解析，并自动生成查询语句进行查询

按照Spring Data JPA 定义的规则，查询方法以findBy开头，涉及条件查询时，条件的属性用条件关键字连接，要注意的是：条件属性首字母需大写。框架在进行方法名解析时，会先把方法名多余的前缀截取掉，然后对剩下部分进行解析。

```java
@Query(value = "from Book where name = ?1 ")
public Book findBookByName(String name);
```



| **Keyword**       | **Sample**                                | **JPQL**                                                     |
| ----------------- | ----------------------------------------- | ------------------------------------------------------------ |
| And               | findByLastnameAndFirstname                | …  where x.lastname = ?1 and x.firstname = ?2                |
| Or                | findByLastnameOrFirstname                 | …  where x.lastname = ?1 or x.firstname = ?2                 |
| Is,Equals         | findByFirstnameIs,  findByFirstnameEquals | …  where x.firstname = ?1                                    |
| Between           | findByStartDateBetween                    | …  where x.startDate between ?1 and ?2                       |
| LessThan          | findByAgeLessThan                         | …  where x.age < ?1                                          |
| LessThanEqual     | findByAgeLessThanEqual                    | …  where x.age ⇐ ?1                                          |
| GreaterThan       | findByAgeGreaterThan                      | …  where x.age > ?1                                          |
| GreaterThanEqual  | findByAgeGreaterThanEqual                 | …  where x.age >= ?1                                         |
| After             | findByStartDateAfter                      | …  where x.startDate > ?1                                    |
| Before            | findByStartDateBefore                     | …  where x.startDate < ?1                                    |
| IsNull            | findByAgeIsNull                           | …  where x.age is null                                       |
| IsNotNull,NotNull | findByAge(Is)NotNull                      | …  where x.age not null                                      |
| Like              | findByFirstnameLike                       | …  where x.firstname like ?1                                 |
| NotLike           | findByFirstnameNotLike                    | … where  x.firstname not like ?1                             |
| StartingWith      | findByFirstnameStartingWith               | …  where x.firstname like ?1 (parameter bound with appended %) |
| EndingWith        | findByFirstnameEndingWith                 | …  where x.firstname like ?1 (parameter bound with prepended %) |
| Containing        | findByFirstnameContaining                 | …  where x.firstname like ?1 (parameter bound wrapped in %)  |
| OrderBy           | findByAgeOrderByLastnameDesc              | …  where x.age = ?1 order by x.lastname desc                 |
| Not               | findByLastnameNot                         | …  where x.lastname <> ?1                                    |
| In                | findByAgeIn(Collection ages)              | …  where x.age in ?1                                         |
| NotIn             | findByAgeNotIn(Collection age)            | …  where x.age not in ?1                                     |
| TRUE              | findByActiveTrue()                        | …  where x.active = true                                     |
| FALSE             | findByActiveFalse()                       | …  where x.active = false                                    |
| IgnoreCase        | findByFirstnameIgnoreCase                 | …  where UPPER(x.firstame) = UPPER(?1)                       |







# 五、Specifications动态查询

## **criteria 查询**

有时我们在查询某个实体的时候，给定的条件是不固定的，这时就需要动态构建相应的查询语句，在Spring Data JPA中可以通过JpaSpecificationExecutor接口查询。相比JPQL,其优势是类型安全,更加的面向对象。

```java
public interface JpaSpecificationExecutor<T> {
    // 根据条件查询一个对象
    Optional<T> findOne(@Nullable Specification<T> var1);
    //根据条件查询集合
    List<T> findAll(@Nullable Specification<T> var1);
    //根据条件分页查询
    Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
    //排序查询查询
    List<T> findAll(@Nullable Specification<T> var1, Sort var2);
    //统计查询
    long count(@Nullable Specification<T> var1);
}
```

JpaSpecificationExecutor，这个接口基本是围绕着Specification接口来定义的。我们可以简单的理解为，Specification构造的就是查询条件。

Specification接口中只定义了如下一个方法：

```java
//构造查询条件
    /**
    *	root	：Root接口，代表查询的根对象，可以通过root获取实体中的属性
    *	query	：代表一个顶层查询对象，用来自定义查询
    *	cb		：用来构建查询，此对象里有很多条件方法
    **/
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

```





## 1. 使用Specifications完成条件查询

* CriteriaBuilder 安全查询创建工厂
  CriteriaBuilder 安全查询创建工厂，,创建CriteriaQuery，创建查询具体具体条件Predicate 等。
  CriteriaBuilder是一个工厂对象,安全查询的开始.用于构建JPA安全查询.可以从EntityManager 或 EntityManagerFactory类中获得CriteriaBuilder。



* CriteriaQuery 安全查询主语句

  它通过调用 CriteriaBuilder, createQuery 或CriteriaBuilder.createTupleQuery 获得。
  CriteriaBuilder就像CriteriaQuery 的工厂一样。
  CriteriaQuery对象必须在实体类型或嵌入式类型上的Criteria 查询上起作用。

* Root

  Root 定义查询的From子句中能出现的类型
  Criteria查询的查询根定义了实体类型，能为将来导航获得想要的结果，它与SQL查询中的FROM子句类似。
  Root实例也是类型化的，且定义了查询的FROM子句中能够出现的类型。
  查询根实例能通过传入一个实体类型给 AbstractQuery.from方法获得。
  Criteria查询，可以有多个查询根。

* Predicate 过滤条件

  过滤条件应用到SQL语句的FROM子句中。
  在criteria 查询中，查询条件通过Predicate 或Expression 实例应用到CriteriaQuery 对象上。
  这些条件使用 CriteriaQuery .where 方法应用到CriteriaQuery 对象上。
  Predicate 实例也可以用Expression 实例的 isNull， isNotNull 和 in方法获得，复合的Predicate 语句可以使用CriteriaBuilder的and, or andnot 方法构建。
  CriteriaBuilder 也是作为Predicate 实例的工厂，Predicate 对象通过调用CriteriaBuilder 的条件方法（ equal，notEqual， gt， ge，lt， le，between，like等）创建。



```java
@Test
public void testFind() {
    //使用匿名内部类的方式，创建一个Specification的实现类，并实现toPredicate方法
    Specification<Book> spec = new Specification<Book>(){
        @Override
        public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

            return criteriaBuilder.like(root.get("name").as(String.class), "%机关师%");
        }
    };
    Optional<Book> optionalBook = bookRepository.findOne(spec);
    optionalBook.ifPresent(System.out::println);
}

@Test
public void testBetween() {
    // 构造查询条件
    Specification<Book> spec = (Specification<Book>) (root, criteriaQuery, criteriaBuilder) 
        -> criteriaBuilder.between(root.get("price").as(Double.class),5.0,30d);
    List<Book> bookList = bookRepository.findAll(spec);
    System.out.println(bookList);
}
```



## 2. 基于Specifications的分页查询

```java
@Test
public void testPage() {
    // 构造查询条件
    Specification<Book> spec = (Specification<Book>) (root, criteriaQuery, criteriaBuilder)
        -> criteriaBuilder.like(root.get("name").as(String.class),"%机关师%");
    	/*
         * 构造分页参数
         * 		Pageable : 接口
         * 			PageRequest实现了Pageable接口，调用构造方法的形式构造
         * 				第一个参数：页码（从0开始）
         * 				第二个参数：每页查询条数
         */
    Pageable pageable = PageRequest.of(0, 5);
    	/*
         * 分页查询，封装为Spring Data Jpa 内部的page bean
         * 		此重载的findAll方法为分页方法需要两个参数
         * 			第一个参数：查询条件Specification
         * 			第二个参数：分页参数
         */
    Page<Book> bookPage = bookRepository.findAll(spec, pageable);
    System.out.println(bookPage.toList());

}
```



对于Spring Data JPA中的分页查询，是其内部自动实现的封装过程，返回的是一个Spring Data JPA提供的pageBean对象。其中的方法说明如下：

```java
public interface Page<T> extends Slice<T> {
    static <T> Page<T> empty() {
        return empty(Pageable.unpaged());
    }

    static <T> Page<T> empty(Pageable pageable) {
        return new PageImpl(Collections.emptyList(), pageable, 0L);
    }
    //获取总页数
    int getTotalPages();
    //获取总记录数
    long getTotalElements();
    //获取列表数据
    <U> Page<U> map(Function<? super T, ? extends U> var1);
}
```

## 3. 多表查询

```java
	/**
	 * Specification的多表查询
	 */
@Test
public void testFind() {
    Specification<LinkMan> spec = new Specification<LinkMan>() {
        public Predicate toPredicate(Root<LinkMan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            //Join代表链接查询，通过root对象获取
            //创建的过程中，第一个参数为关联对象的属性名称，第二个参数为连接查询的方式（left，inner，right）
            //JoinType.LEFT : 左外连接,JoinType.INNER：内连接,JoinType.RIGHT：右外连接
            Join<LinkMan, Customer> join = root.join("customer",JoinType.INNER);
            return cb.like(join.get("custName").as(String.class),"tom");
        }
    };
    List<LinkMan> list = linkManDao.findAll(spec);
    for (LinkMan linkMan : list) {
        System.out.println(linkMan);
    }
}

```



## 4. CriteriaBuilder 常用方法

| 方法名   | sql中的符号 |
| -------- | ----------- |
| between  | between and |
| equal    | =           |
| gt       | >           |
| ge       | >=          |
| lt       | <           |
| le       | <=          |
| like     | like        |
| notEqule | !=          |



# 六、Spring Data JPA 多表查询

在实际开发中，我们数据库的表难免会有相互的关联关系，在操作表的时候就有可能会涉及到多张表的操作。而在这种实现了ORM思想的框架中（如JPA），可以通过操作实体类就实现对数据库表的操作。

1. 首先确定两张表之间的关系。

如果关系确定错了，后面做的所有操作就都不可能正确。

2. 在数据库中实现两张表的关系

3. 在实体类中描述出两个实体的关系

4. 配置出实体类和数据库表的关系映射



## 1. 一对多操作

### 1.1 关系分析

这里以班级和学生为例

一个学校拥有多个班级

**一个**班级拥有**多个**学生



### 1.2 建表

在一对多关系中，我们习惯把一的一方称之为主表，把多的一方称之为从表。在数据库中建立一对多的关系，需要使用数据库的外键约束。

从表中有一列，取值参照主表的主键，这一列就是外键。

```sql
drop table if exists t_student;
drop table if exists t_class;


create table t_class
(
    id      int(16) auto_increment not null primary key,
    grade   int(4)                 not null,
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

INSERT INTO `t_class` VALUES (1, 1, 1);
INSERT INTO `t_class` VALUES (2, 1, 2);
INSERT INTO `t_class` VALUES (3, 1, 3);
INSERT INTO `t_class` VALUES (4, 2, 1);
INSERT INTO `t_class` VALUES (5, 2, 2);
INSERT INTO `t_class` VALUES (6, 2, 3);
INSERT INTO `t_class` VALUES (7, 3, 1);
INSERT INTO `t_class` VALUES (8, 3, 2);
INSERT INTO `t_class` VALUES (9, 3, 3);


INSERT INTO `t_student` VALUES (1, '张三', 16, 'man', '13131313131', '明月路太阳小区', 1);
INSERT INTO `t_student` VALUES (2, '李四', 15, 'man', '12312312312', '明月路紫薇小区', 1);
INSERT INTO `t_student` VALUES (3, 'Rose', 15, 'woman', '12323232323', '明月路香榭小区', 1);
INSERT INTO `t_student` VALUES (4, '杰克', 16, 'man', '23654236542', '太阳路月亮小区', 2);
```



### 1.3 实体类关系建立以及配置映射

@OneToMany默认的是FetchType.LAZY(懒加载)
@ManyToOne默认的是FetchType.EAGER(急加载)

mappedBy只能适用于@OneToOne，@OneToMany，@ManyToMany这些注解。mappedBy用于主表的一方。

一对多的关系由从表去负责维护。

Class

```java
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "t_class")
public class ClassInSchool implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Class ID

    @Column(name = "grade")
    private Integer grade; // 年级

    @Column(name = "grade_order")
    private Integer order; // 班级

    @OneToMany(mappedBy = "classInSchool", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    // 主表放弃对外键的维护，mappedBy属性声明从表所对应的属性名 FetchType.EAGER为急加载
    private List<Student> students = new ArrayList<>(); // 学生列表

}
```

Student **ToString排除了ManyToOne 所表示的属性，如果不排除会进入死循环**

```java
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@ToString(exclude = "classInSchool")
@Table(name = "t_student")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Student id

    @Column(name = "name")
    private String name; // 姓名

    @Column(name = "age")
    private Integer age; // 年龄

    @Column(name = "gender")
    private String gender;// woman or man

    @Column(name = "address")
    private String address; // 地址

    @Column(name = "phone_number")
    private String tel; // 电话

    @ManyToOne(targetEntity = ClassInSchool.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private ClassInSchool classInSchool; // 所属班级

    public Student(String name, Integer age, String gender, String address, String tel) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.tel = tel;
    }
}

```

### 1.4 注解说明

**@OneToMany:**

   作用：建立一对多的关系映射

  属性：

​       targetEntityClass：指定多的多方的类的字节码

​       mappedBy：指定从表实体类中引用主表对象的名称。

​       cascade：指定要使用的级联操作

​       fetch：指定是否采用延迟加载

​       orphanRemoval：是否使用孤儿删除

 

**@ManyToOne**

  作用：建立多对一的关系

  属性：

​       targetEntityClass：指定一的一方实体类字节码

​       cascade：指定要使用的级联操作

​       fetch：指定是否采用延迟加载

​       optional：关联是否可选。如果设置为false，则必须始终存在非空关系。

 cascade 说明

>CascadeType.PERSIST
>
>级联新增，保存父对象时会新建其中包含的子对象
>
> 
>
>CascadeType.MERGE
>
>级联修改，保存父对象时会更新其中所包含的子对象数据
>
> 
>
>CascadeType.REMOVE
>
>级联删除，当删除关联关系时会将子对象的数据删除
>
> 
>
>CascadeType.REFRESH
>
>级联刷新，保存关联关系时会更新子对象和数据库中一致(意思是你在父对象中添加一个只包含ID的子对象，也可以保存进去)
>
> 
>
>CascadeType.ALL
>
>包含上述所有操作

**@JoinColumn**

   作用：用于定义主键字段和外键字段的对应关系。

   属性：

​       name：指定外键字段的名称

​       referencedColumnName：指定引用主表的主键字段名称

​       unique：是否唯一。默认值不唯一

​       nullable：是否允许为空。默认值允许。

​       insertable：是否允许插入。默认值允许。

​       updatable：是否允许更新。默认值允许。

​       columnDefinition：列的定义信息。



### 1.5 一对多操作

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母顺序执行单元测试
public class ClassTest {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private StudentRepository studentRepository;



    /**
     * 级联查询
     */
    @Test
    public void testAFindById() {
        Optional<ClassInSchool> classInSchool = classRepository.findById(1);
        classInSchool.ifPresent(System.out::println);
    }
    /**
     * 插入
     */
    @Test
    public void testBInsert() {
        // 构造插入参数
        Student student1 = new Student("周六", 16, "woman", "明月路紫薇小区", "59645625465");
        Student student2 = new Student("王五", 17, "man", "明月路香榭小区", "12365894654");
        ClassInSchool aClass = new ClassInSchool();
        aClass.setGrade(2);
        aClass.setOrder(4);
        List<Student> students = new ArrayList<>();
        /*
         * 如果设置了外键不能为空，一定要将一的一方添加到多的一方
         */
        student1.setClassInSchool(aClass);
        student2.setClassInSchool(aClass);

        students.add(student1);
        students.add(student2);
        aClass.setStudents(students);
        classRepository.save(aClass);
    }

    /**
     * 修改
     */
    @Test
    public void testCUpdate() {
        // 查询
        ClassInSchool classInSchool = new ClassInSchool();
        classInSchool.setGrade(2);
        classInSchool.setOrder(4);
        Optional<ClassInSchool> one = classRepository.findOne(Example.of(classInSchool));
        if (one.isPresent()){
            ClassInSchool aClass = one.get();
            aClass.setOrder(5);
            classRepository.save(aClass);
        }
    }
    /**
     * 删除
     */
    @Test
    public void testDDelete() {
        // 查询
        ClassInSchool classInSchool = new ClassInSchool();
        classInSchool.setGrade(2);
        classInSchool.setOrder(5);
        Optional<ClassInSchool> one = classRepository.findOne(Example.of(classInSchool));
        if (one.isPresent()){
            ClassInSchool aClass = one.get();
            classRepository.deleteById(aClass.getId());
        }
    }
}
```



## 2. 多对多操作

### 2.1 关系分析

一个老师教多个班级，一个班级又对应多个老师



### 2.2 建表

班级表采用一对多操作中的表，这里建立老师表和中间表

```sql
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
  
INSERT INTO `t_teacher` VALUES (1, '李一', 31, 'man', '123456789', '明月路太阳小区', '语文');
INSERT INTO `t_teacher` VALUES (2, '李二', 28, 'man', '965654522', '明月路紫薇小区', '数学');
INSERT INTO `t_teacher` VALUES (3, '李三', 29, 'woman', '564235462', '太阳路月亮小区', '英语');

INSERT INTO `t_class_teacher` VALUES (1, 1);
INSERT INTO `t_class_teacher` VALUES (2, 1);
INSERT INTO `t_class_teacher` VALUES (1, 2);
INSERT INTO `t_class_teacher` VALUES (2, 2);
INSERT INTO `t_class_teacher` VALUES (3, 2);
INSERT INTO `t_class_teacher` VALUES (1, 3);
INSERT INTO `t_class_teacher` VALUES (3, 3);
```



###  2.3 实体关系建立， 映射配置

Teacher

```java
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "t_teacher")
public class Teacher implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Student id

    @Column(name = "name")
    private String name; // 姓名

    @Column(name = "age")
    private Integer age; // 年龄

    @Column(name = "gender")
    private String gender;// woman or man

    @Column(name = "address")
    private String address; // 地址

    @Column(name = "phone_number")
    private String tel; // 电话

    @Column(name = "subject")
    private String subject; // 科目

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ClassInSchool.class,cascade = {CascadeType.PERSIST,  CascadeType.MERGE})
    @JoinTable(
            name = "t_class_teacher",// 中间表名称
            joinColumns = {@JoinColumn(name = "teacher_id", referencedColumnName = "id")},
            // 中间表字段中的teacher_id对应 t_teacher表中的id,
            inverseJoinColumns = {@JoinColumn(name = "class_id", referencedColumnName = "id")}
    )
    private Set<ClassInSchool> classSet = new HashSet<>();

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", subject='" + subject + '\'' +
                ", classSet=" + classSet +
                '}';
    }
}
```

Class

```java
@Entity
@Getter
@Setter
@Table(name = "t_class")
public class ClassInSchool implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // Class ID

    @Column(name = "grade")
    private Integer grade; // 年级

    @Column(name = "grade_order")
    private Integer order; // 班级

    @OneToMany(mappedBy = "classInSchool", fetch = FetchType.EAGER)
    // 主表放弃对外键的维护，mappedBy属性声明从表所对应的属性名
    private List<Student> students = new ArrayList<>(); // 学生列表

    @ManyToMany(mappedBy = "classSet")
    private Set<Teacher> teachers = new HashSet<>();

    @Override
    public String toString() {
        return "ClassInSchool{" +
                "id=" + id +
                ", grade=" + grade +
                ", order=" + order +
                ", students=" + students +
                '}';
    }
}
```



注意：直接使用`LomBok`的`@Data` 注解可能发生死循环，(equal, toString)，所用使用@Setter @Getter, toString自己写

### 2.4 注解说明

**@ManyToMany**

​     作用：用于映射多对多关系

​     属性：

​          cascade：配置级联操作。

​          fetch：配置是否采用延迟加载。

​       targetEntity：配置目标的实体类。映射多对多的时候不用写。

 

**@JoinTable**

  作用：针对中间表的配置

  属性：

​       name：配置中间表的名称

​       joinColumns：中间表的外键字段关联当前实体类所对应表的主键字段                           

​       inverseJoinColumn：中间表的外键字段关联对方表的主键字段

​       

**@JoinColumn**

  作用：用于定义主键字段和外键字段的对应关系。

  属性：

​       name：指定外键字段的名称

​       referencedColumnName：指定引用主表的主键字段名称

​       unique：是否唯一。默认值不唯一

​       nullable：是否允许为空。默认值允许。

​       insertable：是否允许插入。默认值允许。

​       updatable：是否允许更新。默认值允许。

​       columnDefinition：列的定义信息。



### 2.5 多对多操作

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按字母顺序执行单元测试
public class TeacherTest {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;

    /**
     * 查询
     */
    @Test
    public void testA1Find() {
        Optional<Teacher> teacherOptional = teacherRepository.findById(1);
        teacherOptional.ifPresent(System.out::print);
    }

    /**
     * 级联插入
     * Hibernate: insert into t_teacher (address, age, gender, name, subject, phone_number) values (?, ?, ?, ?, ?, ?)
     * Hibernate: insert into t_class (grade, grade_order) values (?, ?)
     * Hibernate: insert into t_class_teacher (teacher_id, class_id) values (?, ?)
     */
    @Test
    public void testA2Insert() {

        ClassInSchool aClass = new ClassInSchool();
        aClass.setGrade(2);
        aClass.setOrder(4);


        Teacher teacher = new Teacher();
        teacher.setName("刘一");
        teacher.setAge(36);
        teacher.setGender("man");
        teacher.setTel("163546");
        teacher.setSubject("书法");
        teacher.setAddress("花园路玫瑰小区");


        // Teacher方维护多表结构，建立关系
        teacher.getClassSet().add(aClass);

        teacherRepository.save(teacher);
    }

    /**
     * 删除id为1的teacher, 先删除中间表中的数据，再删除teacher表
     * Hibernate: delete from t_class_teacher where teacher_id=?
     * Hibernate: delete from t_teacher where id=?
     */
    @Test
    public void testA3Delete() {
        teacherRepository.deleteById(1);
    }
}
```



# 七、自定义注解