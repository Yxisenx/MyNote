[TOC]

# 一. 快速入门

## 1. 概念：

* JSP全称Java Server Pages，是一种动态网页开发技术。它使用JSP标签在HTML网页中插入Java代码。标签通常以<%开头以%>结束。
* JSP是一种Java servlet，主要用于实现Java web应用程序的用户界面部分。网页开发者们通过结合HTML代码、XHTML代码、XML元素以及嵌入JSP操作和命令来编写JSP。
* JSP通过网页表单获取用户输入数据、访问数据库及其他数据源，然后动态地创建网页。
* JSP标签有多种功能，比如访问数据库、记录用户选择信息、访问JavaBeans组件等，还可以在不同的网页中传递控制信息和共享信息。

## 2. 原理

### 第一次执行

1. 客户端通过浏览器向服务器发出请求，在该请求中包含了请求的资源的路径，这样当服务器接收到该请求后就可以知道被请求的内容。
2. 服务器根据接收到的客户端的请求来加载相应的JSP文件。
3. Web服务器中的JSP引擎会将被加载的JSP文件转化为Servlet。
4. JSP引擎将生成的Servlet代码编译成Class文件。
5. 服务器执行这个Class文件。
6. 最后服务器将执行结果发送给浏览器进行显示。

### 后续执行

与Servlet一致

## 3. JSP的脚本

> JSP定义Java代码的方式

### 3.1<%  代码 %>

定义的java代码，在service方法中。service方法中可以定义什么，该脚本中就可以定义什么。

### 3.2 <%! 代码 %>

定义的java代码，在jsp转换后的java类的成员位置。

### 3.3 <%= 代码 %>

定义的java代码，会输出到页面上。输出语句中可以定义什么，该脚本中就可以定义什么。



## JSP内置对象

在jsp页面中不需要获取和创建，可以直接使用的对象
jsp一共有9个内置对象。常用的为以下三种

* request
*  response
* out：字符输出流对象。可以将数据输出到页面上。和response.getWriter()类似
  *  response.getWriter()和out.write()的区别：
    * 在tomcat服务器真正给客户端做出响应之前，会先找response缓冲区数据，再找out缓冲区数据。
    *  response.getWriter()数据输出永远在out.write()之前



***



# 二. 指令

### 1. 作用

用于配置JSP页面，导入资源文件

## 2. 格式

```html
<%@ 指令名称 属性名1=属性值1 属性名2=属性值2 ... %>
```

## 3. 分类

### 3.1 page

用于配置JSP页面

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="404.jsp" %>
```

#### 3.1.1 contentType

等同于response.setContentType()
   				1. 设置响应体的mime类型以及字符集
                        				2. 设置当前jsp页面的编码（只能是高级的IDE才能生效，如果使用低级工具，则需要设置pageEncoding属性设置当前页面的字符集）

#### 3.1.2 import

导包

```html
<%@ page import="cn.onecolour.userManager.domain.User" %>
```

#### 3.1.3 errorPage

当前页面发生异常后，会自动跳转到指定的错误页面

#### 3.1.4 isErrorPage

标识当前也是是否是错误页面。

* true：是，可以使用内置对象exception
*  false：否。默认值。不可以使用内置对象exception

### 3.2 include

导入页面的资源文件

```html
<%@include file="top.jsp"%>
```

### 3.3 taglib

导入资源

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

* prefix：前缀，自定义的



***



# 三. 注释

## 1. html注释：
\<!-- 注释内容 -->:只能注释html代码片段

## 2. jsp注释：推荐使用
<%-- 注释内容 --%>：可以注释所有



***



# 四. 内置对象

> 在jsp页面中不需要创建，直接使用的对象

共9个

| 变量名      | 真实类型            | 作用                                         |
| ----------- | ------------------- | -------------------------------------------- |
| pageContext | PageContext         | 当前页面共享数据，还可以获取其他八个内置对象 |
| request     | HttpServletRequest  | 一次请求访问的多个资源(转发)                 |
| session     | HttpSession         | 一次会话的多个请求间                         |
| application | ServletContext      | 所有用户间共享数据                           |
| response    | HttpServletResponse | 响应对象                                     |
| page        | Object              | 当前页面(Servlet)的对象 this                 |
| out         | JspWriter           | 输出对象，数据输出到页面上                   |
| config      | ServletConfig       | Servlet的配置对象                            |
| exception   | Throwable           | 异常对象                                     |



***



# 五. EL表达式

## 1. 概念

Expression Language 表达式语言



## 2. 作用

替换和简化jsp页面中java代码的编写



## 3. 语法

```el
${表达式}
```



## 4. 注意

jsp默认支持el表达式的。如果要忽略el表达式

1. 设置jsp中page指令中：isELIgnored="true" 忽略当前jsp页面中所有的el表达式
2. \\${表达式} ：忽略当前这个el表达式 

## 5. 使用

### 5.1 运算

#### 5.1.1 运算符

1. 算数运算符： + - * /(div) %(mod)
2. 比较运算符： > < >= <= == !=
3. 逻辑运算符： &&(and) ||(or) !(not)
4. 空运算符： empty
   * 功能：用于判断字符串、集合、数组对象是否为null或者长度是否为0
   * ${empty list}:判断字符串、集合、数组对象是否为null或者长度为0
   * ${empty list}:判断字符串、集合、数组对象是否为null或者长度为0

#### 5.1.2 获取值

1. el表达式只能从域对象中获取值

2. 语法
   1. ${域名称.键名}：从指定域中获取指定键的值
      1. pageScope		--> pageContext
      2. requestScope 	--> request
      3. sessionScope 	--> session
      4. applicationScope --> application（ServletContext）
      5. 举例：在request域中存储了name=张三
         * ${requestScope.name}
   2. ${键名}：表示依次从最小的域中查找是否有该键对应的值，直到找到为止。
   3. 获取对象、List集合、Map集合的值
      1. 对象：${域名称.键名.属性名}	
         *  本质上会去调用对象的getter方法
      2. List集合：${域名称.键名[索引]}
      3. Map集合
         * ${域名称.键名.key名称}
         * ${域名称.键名["key名称"]}

#### 5.1.3 隐式对象

el表达式中有11个隐式对象

pageContext

* 获取jsp其他八个内置对象
  * 例如：${pageContext.request.contextPath}：动态获取虚拟目录



***



# 六. JSTL

## 1. 概念

JavaServer Pages Tag Library  JSP标准标签库

## 2. 作用

用于简化和替换jsp页面上的java代码

## 3. 使用步骤

### 3.1 导入jstl相关jar包

jstl-impl.jar

### 3.2 引入标签库

taglib指令：  

```html
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

### 3.3 使用标签

```html
<c:if test="${sessionScope.userType=='admin'}">
    <%-- 内容 --%>
</c:if>
```



## 4. 核心标签

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

|标签|描述|
|:---:|:---|
|\<c:out>|用于在JSP中显示数据，就像<%= ... >|
|\<c:set>|用于保存数据|
|\<c:remove>|用于删除数据|
|\<c:catch>|用来处理产生错误的异常状况，并且将错误信息储存起来|
|**\<c:if>**|与我们在一般程序中用的if一样|
|**\<c:choose>**|本身只当做\<c:when>和\<c:otherwise>的父标签(相当于swich)|
|**\<c:when>**|\<c:choose>的子标签，用来判断条件是否成立(相当于 case)|
|**\<c:otherwise>**|\<c:choose>的子标签，接在\<c:when>标签后，当\<c:when>标签判断为false时被执行(相当于default)|
|\<c:import>|检索一个绝对或相对 URL，然后将其内容暴露给页面|
|**\<c:forEach>**|基础迭代标签，接受多种集合类型|

