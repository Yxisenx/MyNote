[TOC]

# 
# 一、导入坐标

一个后端接口大致分为四个部分组成：接口地址（url）、接口请求方式（get、post等）、请求数据（request）、响应数据（response）。

```xml
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
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
    </dependency>
</dependencies>
```



# 二、全局异常处理

## 1. 参数校验，自动添加msg

```java
package cn.onecolour.uniteResponse.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class User {
    private Integer id;
    @NotNull(message = "用户名不能为空")
    @Length(min = 4, max = 16 ,message = "用户名长度为4-16位")
    private String username;
    @NotNull(message = "密码不能为空")
    @Length(min = 4, max = 16 ,message = "密码长度为4-16位")
    private String password;
}
```



## 2. 自定义异常

```java
package cn.onecolour.uniteResponse.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BaseException extends RuntimeException {
    /**
     * 自定义异常
     */
    public Integer code;
    public String msg;

    public BaseException(String msg) {
        this(1001, msg);
    }

    public BaseException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
```



## 3. 配置全局异常处理类

```java
package cn.onecolour.uniteResponse.exception;

import cn.onecolour.uniteResponse.config.BaseException;
import cn.onecolour.uniteResponse.config.ResultCode;
import cn.onecolour.uniteResponse.vo.ResultVO;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "cn.onecolour.uniteResponse.controller")
public class GlobalExceptionHandler {

    // 数据校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<List<String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        List<String> list = new ArrayList<>();
        for (ObjectError error : errors) {
            list.add(error.getDefaultMessage());
        }
        return new ResultVO<>(ResultCode.VALIDATE_FAILED,list);
    }

    @ExceptionHandler(BindException.class)
    public ResultVO<List<String>> BindExceptionHandler(BindException e){
        List<ObjectError> errors = e.getAllErrors();
        List<String> list = new ArrayList<>();
        for (ObjectError error : errors) {
            list.add(error.getDefaultMessage());
        }
        return new ResultVO<>(ResultCode.VALIDATE_FAILED,list);
    }

    // 自定义异常类
    @ExceptionHandler(BaseException.class)
    public ResultVO<String> BaseExceptionHandler(BaseException e){
        // 返回类型是自定义响应体
        return new ResultVO<>(e.getCode(),"响应失败",e.getMsg());
    }
    // HttpMediaTypeNotSupportedException
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultVO<String> HttpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e){
        // 返回类型是自定义响应体
        return new ResultVO<>(1001,"请求错误",e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<String> ExceptionHandler(Exception e){
        return new ResultVO<>(ResultCode.ERROR, e.getMessage());
    }
}
```





# 三、统一响应体

## 1. 响应结果

获取成功了呢自然就返回的数据列表，获取失败了后台就会响应异常信息，即一个字符串列表，就是说前端开发者压根就不知道后端响应过来的数据会是啥样的！所以，统一响应数据是前后端规范中必须要做的！

ResultVO\<T>

```java
package cn.onecolour.uniteResponse.vo;

import cn.onecolour.uniteResponse.config.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVO<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    /**
     * 直接放入数据，表示成功
     * @param
     */
    public ResultVO(T data){
        this(ResultCode.SUCCESS,data);
    }

    public ResultVO(ResultCode resultCode, T data){
        this(resultCode.getCode(),resultCode.getMsg(),data);
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
```



## 2. 响应码枚举

```java
package cn.onecolour.uniteResponse.config;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(1000, "操作成功"),

    FAILED(1001, "响应失败"),

    VALIDATE_FAILED(1002, "参数校验失败"),

    ERROR(5000, "未知错误");

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
```



# 四、处理全局响应数据

```java
package cn.onecolour.uniteResponse.config;

import cn.onecolour.uniteResponse.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"cn.onecolour.uniteResponse.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !methodParameter.getParameterType().equals(ResultVO.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        return new ResultVO<>(o);
    }

}

```



# 五、测试

## 1. yml

```yaml
server:
  port: 80
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true

```



## 2. 测试类

```java
package cn.onecolour.uniteResponse.controller;

import cn.onecolour.uniteResponse.entity.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @GetMapping("/user/{id}")
    public User getById(@PathVariable Integer id){
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        user.setPassword("123456");
        return user;
    }

    @PostMapping("/user")
    public User add(@Valid User user){
        return user;
    }

}
```



正常访问

GET http: //localhost/user/1

```json
{
    "code": 1000,
    "msg": "操作成功",
    "data": {
        "id": 1,
        "username": "tom",
        "password": "123456"
    }
}
```

POST http: //localhost/user

```java
{
    "code": 1000,
    "msg": "操作成功",
    "data": {
        "id": 1,
        "username": "toma",
        "password": "563456"
    }
}
```

```json
{
    "code": 1002,
    "msg": "参数校验失败",
    "data": [
        "用户名长度为4-16位"
    ]
}
```

