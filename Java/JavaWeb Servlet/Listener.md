[TOC]

# Liestener 监听器

## 1. ServletContextListener

监听ServletContext对象的创建和销毁

```java
void contextInitialized(ServletContextEvent sce) {}
// 当ServletContext创建，执行该方法
void contextDestroyed(ServletContextEvent sce) {}
// 当ServletContext被销毁，执行该方法
```

## 2.ServeletContextAttributeListener

监听ServeletContext域中属性的变化

```java
void attributeAdded(ServletContextAttributeEvent scae) {}
// 当ServletContext中添加属性后，执行该方法
void attributeRemoved(ServletContextAttributeEvent scae) {}
// 当ServletContext中移除属性后，执行该方法
void attributeReplaced(ServletContextAttributeEvent scae) {}
// 当ServletContext中的属性被修改后，执行该方法
```

## 3. HttpSessionListener

监听HttpSession的创建与销毁

```java
void sessionCreated(HttpSessionEvent se) {}
// 当HttpSession创建，执行该方法
void sessionDestroyed(HttpSessionEvent se) {}
// 当HttpSession销毁，执行该方法
```

## 4. HttpSessionAttributeListener

监听HttpSession域中属性的变化

```java
void attributeAdded(HttpSessionBindingEvent sbe) {}
// 当HttpSession中添加属性后，执行该方法
void attributeRemoved(HttpSessionBindingEvent sbe) {}
// 当HttpSession中移除属性后，执行该方法
void attributeReplaced(HttpSessionBindingEvent sbe) {}
// 当HttpSession中属性被修改后，执行该方法
```

## 5.HttpRequestListener

```java
void requestInitialized(ServletRequestEvent sre) {}
// Request请求开始时执行该方法
void requestDestroyed(ServletRequestEvent sre) {}
// Request请求结束，执行该方法
```

## 6. HttpRequestAttributeListener

```java
void attributeAdded(ServletRequestAttributeEvent srae) {}
// 当HttpRequest域中添加属性后，执行该方法
void attributeRemoved(ServletRequestAttributeEvent srae) {}
// 当HttpRequest域中移除属性后，执行该方法
void attributeReplaced(ServletRequestAttributeEvent srae) {}
// 当HttpRequest域中属性被修改后，执行该方法
```



配置：

1. 注解配置

   ```java
   @WebListener
   ```

2. xml配置

   ```xml
   <listener>
    <listener-class>
        cn.onecolour.web.listener.ContextLoaderListener
       </listener-class>
   </listener>
   ```

   

demo

```java
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.io.UnsupportedEncodingException;

/**
 *  监听request对象创建，设置request编码
 */
@WebListener
public class RequestListener implements ServletRequestListener {
    
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        String encoding = servletRequest.getCharacterEncoding();
        if (encoding==null||"".equals(encoding)){
            try {
                servletRequest.setCharacterEncoding("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
```

