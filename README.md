# SSM框架整合纯注解小案例

springMVC+spring+mybatis 3大框架整合，各种XML配置扑面而来。而这些XML文件格式要求又很严格，整套搭下来，让人感觉写的不是java，而是XML配置文件。而网上的注解配置，并不是纯粹的0配置，还是要写一些xml配置，只是用了几个@Service，@Controller注解。

鄙人讲的配置方式是一行XML代码都不需要，什么web.xml,applicationContext.xml，springmvc.xml，统统去死吧~~~！！！

## **准备**

首先建立一个Maven项目，Packageing方式为war，项目结构为标准Maven WebApp结构。

pom文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <packaging>war</packaging>
  <name>ssm</name>
  <groupId>MyCase</groupId>
  <artifactId>ssm</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <spring.version>5.0.2.RELEASE</spring.version>
    <slf4j.version>1.6.6</slf4j.version>
    <log4j.version>1.2.12</log4j.version>
    <mysql.version>5.1.6</mysql.version>
    <mybatis.version>3.4.5</mybatis.version>
  </properties>

  <dependencies>
    <!--切面表达式-->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.8.7</version>
    </dependency>
    <!--spring/springMVC-->
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
      <artifactId>spring-web</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <!--单元测试-->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>compile</scope>
    </dependency>
    <!--mysql驱动-->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql.version}</version>
    </dependency>
    <!--servlet-->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>4.0.0</version>
      <scope>provided</scope>
    </dependency>
    <!--jsp-->
    <dependency>
      <groupId>javax.servlet.jsp</groupId>
      <artifactId>jsp-api</artifactId>
      <version>2.0</version>
      <scope>provided</scope>
    </dependency>
    <!--jstl标签-->
    <dependency>
      <groupId>jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>

    <!--日志依赖-->
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
    <!--mybatis-->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${mybatis.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.0</version>
    </dependency>
    <!--德鲁伊连接池-->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.0.9</version>
    </dependency>

  </dependencies>

  <build>
    <!--maven插件-->
    <plugins>
      <!--jdk编译插件-->
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
          <encoding>utf-8</encoding>
        </configuration>
      </plugin>
      <!--添加tomcat的maven插件-->
      <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
          <port>8080</port>
          <path>/SSM</path>
          <!--该配置可以解决get请求乱码-->
          <uriEncoding>UTF-8</uriEncoding>
        </configuration>
      </plugin>
    </plugins>
  </build>

</project>
```

数据库表：

```sql
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `money` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) 
```

一些数据库的测试数据：

|  id  | name | money |
| :--: | :--: | :---: |
|  1   | 小强 | 1000  |
|  2   | 小明 | 1000  |

一切准备就绪，开始动手吧。

## dao的配置部分

```java
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

@MapperScan("dao")//扫描Mybatis的Mapper接口
public class DaoConfig {
    /**
     * 将数据源加入IOC容器
     * @return 数据源
     * @throws Exception 
     */
    @Bean("dataSource")
    public DataSource getDataSource() throws Exception {
        //读取配置连接数据库的配置文件信息，创建德鲁伊数据源
        Properties prop=new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbcConfig.properties"));
        return DruidDataSourceFactory.createDataSource(prop);
    }
    /**
     * 添加动态sql工厂
     * @param dataSource 数据源
     * @return 动态sql工厂
     */
    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }
}
```

> 对应dao的XML的配置片段

```xml
 <!--载入jdbc配置文件-->
    <context:property-placeholder location="classpath:jdbcConfig.properties" system-properties-mode="FALLBACK"/>
    <!--通过德鲁伊数据源工厂配置数据源-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSourceFactory" factory-method="createDataSource">
        <constructor-arg>
            <props>
                <prop key="driver">${driver}</prop>
                <prop key="url">${url}</prop>
                <prop key="username">${username}</prop>
                <prop key="password">${password}</prop>
            </props>
        </constructor-arg>
    </bean>

    <bean id="factory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--告诉spring扫描DAO包下的的接口，添加代理实现对象到IOC容器-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="dao"/>
        <property name="sqlSessionFactoryBeanName" value="factory"/>
    </bean>
```

## service的配置部分

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration//表明此类是配置类
@ComponentScan("service.impl")// 扫描自定义的组件
@Import(DaoConfig.class)//引入dao部分的配置
@EnableTransactionManagement //开启事务管理
public class SpringConfig {
    /**
     * 声明式事务
     * @param dataSource 数据源
     * @return 事务管理对象
     */
    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
```

> 对应service的XML的配置片段

```xml
 <!--扫描service包-->
    <context:component-scan base-package="service.impl"/>
    <!--添加事务管理-->
    <!--将spring内置事务管理装入IOC-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <tx:advice transaction-manager="transactionManager" id="txAdvice">
        <tx:attributes>
            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
            <tx:method name="*"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <!-- 配置切点表达式 -->
        <aop:pointcut expression="execution(* service.impl.*.*(..))" id="txPc"/>
        <!-- 配置切面 : 通知+切点 advice-ref:通知的名称 pointcut-ref:切点的名称 -->
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPc"/>
    </aop:config>
```

## controller的配置部分

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Properties;

@Configuration //springmvc的配置类
@ComponentScan("controller")//扫描包
@EnableWebMvc//开启视图
public class WebConfig implements WebMvcConfigurer {
    /**
     * jsp视图解析器的bean
     * @return 视图解析器
     */
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }
    /**
     * 配置静态资源的处理
     * <mvc:default-servlet-handler/>
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    /**
     * 注册内置简单异常处理器
     * @return 异常处理器
     */
    @Bean
    public SimpleMappingExceptionResolver registerExcption(){
        SimpleMappingExceptionResolver exceptionResolver=new SimpleMappingExceptionResolver();
        exceptionResolver.setDefaultErrorView("error");
        //ex是异常对象，jsp页面要是只想取到错误信息 ${requestScope.ex.message}
        exceptionResolver.setExceptionAttribute("ex");
        //这里还可以继续扩展对不同异常类型的处理，往Properties添加就行
        Properties prop=new  Properties();
        prop.put("exception.TransferException","error");
        exceptionResolver.setExceptionMappings(prop);
        return exceptionResolver;
    }
}
```

> 对应controller的XML的配置片段

```xml
<context:component-scan base-package="controller"/>

    <!-- 视图解析器对象 -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!--配置内置简单异常处理-->
    <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
        <!-- 定义默认的异常处理页面，当该异常类型的注册时使用   -->
        <property name="defaultErrorView" value="error"/>
        <!-- 定义异常处理页面用来获取异常信息的变量名，默认名为exception  -->
        <property name="exceptionAttribute" value="ex"/>
        <!-- 定义需要特殊处理的异常，用类名或完全路径名作为key，异常页名作为值，会跳转到对应的异常页面 -->
        <property name="exceptionMappings">
            <props>
                <prop key="exception.TransferException">error</prop>
                <!-- 这里还可以继续扩展对不同异常类型的处理 -->
            </props>
        </property>
    </bean>

    <!-- 开启SpringMVC框架注解的支持,同时加载自定义转换器-->
    <mvc:annotation-driven/>
    <!--静态资源放行-->
    <mvc:default-servlet-handler/>
```

## web的配置部分

```java
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;

//只要继承AbstractAnnotationConfigDispatcherServletInitializer的配置类
//使得拿掉 web.xml 成为可能
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Spring IoC容器配置
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // 可以返回Spring的Java配置文件数组
        return new Class<?>[]{SpringConfig.class};
    }

    // DispatcherServlet的URI映射关系配置
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // 可以返回Spring的Java配置文件数组
        return new Class<?>[]{WebConfig.class};
    }

    // DispatchServlet拦截请求匹配
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //动态注册过滤器
        //使用动态注册过滤器的API，需要用servlet依赖3.0.0以上。
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        //设置初始化参数
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
		/*
		这里记得一定要调用父类方法，不然getServletMappings() ，getServletConfigClasses()，				getRootConfigClasses()将不能生效
        */
        super.onStartup(servletContext);
    }
}

```

> 对应web的XML的配置片段

```xml
 <!-- 加载Spring容器配置 -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <!-- Spring容器加载所有的配置文件的路径 -->
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/applicationContext.xml</param-value>
    </context-param>

    <!--配置控制器-->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:MVC/springmvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--配置过滤器，解决中文乱码-->
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

## 测试

```java
  @RequestMapping("/test1")
    public String test() {
        List<Account> list = accountService.findAll();
        System.out.println(list);
        return "success";
    }

    @RequestMapping("/test2")
    public ModelAndView test2() {
        List<Account> list = accountService.findAll();
        System.out.println(list);
        ModelAndView mv = new ModelAndView();
        mv.addObject("list", list);
        mv.setViewName("success");
        return mv;
    }

    @RequestMapping("/test3")
    public String test3(Integer sid, Integer tid, Double money) {
        accountService.transfer(sid, tid, money);
        return "success";
    }
```

> index.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="test1">测试1</a>
<a href="test2">测试2</a>

<form action="test3" method="post">
    转出账号<input type="text" name="sid"><br>
    转入账号<input type="text" name="tid"><br>
    金额<input type="text" name="money"><br>
    <input type="submit" value="转账">
</form>
</body>
</html>

```

> success.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>成功</title>
</head>
<body>

<center>
    <h1>成功页面</h1>
    <c:if test="${requestScope.list!=null}" >
    <table border="1px" cellspacing="0">
        <thead>
        <tr>
            <th>姓名</th>
            <th>零钱</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="account" items="${requestScope.list}">
            <tr>
                <td>${account.name}</td>
                <td> ${account.money}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:if>
</center>

</body>
</html>

```

> error.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${requestScope.ex.message}
</body>
</html>
```

鄙人不才，抛砖引玉。提供小小案例
