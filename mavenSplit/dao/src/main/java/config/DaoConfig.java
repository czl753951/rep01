package config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description: java类作用描述
 * @Author: 阿呆
 * @CreateDate: 2019/1/17 20:55
 * @Version: 1.0
 */
@MapperScan("dao")//扫描Mybatis的Mapper接口
public class DaoConfig {
    /**
     * 将数据源加入IOC容器
     * @return 数据源
     * @throws Exception 异常
     */
    @Bean("dataSource")
    public DataSource getDataSource() throws Exception {
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
