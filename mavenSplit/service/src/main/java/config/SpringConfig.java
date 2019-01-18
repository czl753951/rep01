package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/16 15:43
 * @Version: 1.0
 */
@Configuration//表明此类是配置类
@ComponentScan("service.impl")// 扫描自定义的组件
@Import(DaoConfig.class)
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
