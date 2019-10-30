package com.newlife.s4.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 描述:
 *
 * @author withqianqian@163.com
 * @create 2018-05-22 15:29
 */
@Configuration
public class DruidConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    //初始化连接数
    @Value("${spring.datasource.initialSize}")
    private Integer initialSize;
    //最新空闲数
    @Value("${spring.datasource.minIdle}")
    private Integer minIdle;
    //最大活跃数
    @Value("${spring.datasource.maxActive}")
    private Integer maxActive;
    //配置从连接池获取连接等待超时的时间
    @Value("${spring.datasource.maxWait}")
    private Long maxWait;
    //打开后，增强timeBetweenEvictionRunsMillis的周期性连接检查，minIdle内的空闲连接，每次检查强制验证连接有效性. 参考：https://github.com/alibaba/druid/wiki/KeepAlive_cn
    @Value("${spring.datasource.keepAlive}")
    private Boolean keepAlive;
    //连接泄露检查，打开removeAbandoned功能 , 连接从连接池借出后，长时间不归还，将触发强制回连接。回收周期随timeBetweenEvictionRunsMillis进行，如果连接为从连接池借出状态，并且未执行任何sql，并且从借出时间起已超过removeAbandonedTimeout时间，则强制归还连接到连接池中。
    @Value("${spring.datasource.removeAbandoned}")
    private Boolean removeAbandoned;
    //超时时间，秒
    @Value("${spring.datasource.removeAbandonedTimeout}")
    private Long removeAbandonedTimeout;
    //开启abanded连接时输出错误日志，这样出现连接泄露时可以通过错误日志定位忘记关闭连接的位置
    @Value("${spring.datasource.logAbandoned}")
    private Boolean logAbandoned;

    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(100000);
        dataSource.setKeepAlive(true);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(80);
        dataSource.setLogAbandoned(true);
        return dataSource;
    }
}
