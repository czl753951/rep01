package config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/16 16:54
 * @Version: 1.0
 */
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
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        //设置初始化参数
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(servletContext);
    }

}
