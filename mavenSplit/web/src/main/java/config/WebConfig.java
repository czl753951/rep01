package config;

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

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/16 16:56
 * @Version: 1.0
 */
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
        exceptionResolver.setExceptionAttribute("ex");
        Properties prop=new  Properties();
        prop.put("exception.TransferException","error");
        exceptionResolver.setExceptionMappings(prop);
        return exceptionResolver;
    }

}
