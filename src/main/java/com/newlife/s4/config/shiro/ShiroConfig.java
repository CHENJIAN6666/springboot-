package com.newlife.s4.config.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;

/**
 * Created by Administrator on 2017/12/11.
 */
@Configuration
public class ShiroConfig {

//    @Value("${spring.redis.shiro.host}")  
//    private String host;  
//    @Value("${spring.redis.shiro.port}")  
//    private int port;  
//    @Value("${spring.redis.shiro.timeout}")  
//    private int timeout;  
//    @Value("${spring.redis.shiro.password}")  
//    private String password;  

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new AjaxPermissionsAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
//        filterChainDefinitionMap.put("/logout", "logout");
//        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/ajaxLogin", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/**", "authc");
//        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
//        shiroFilterFactoryBean.setLoginUrl("/unauth");


        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/MP_verify_cYp8rlSAb9hrhRVQ.txt", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login/auth", "anon");
        filterChainDefinitionMap.put("/login/logout", "anon");
        filterChainDefinitionMap.put("/error", "anon");

        //移动端登录
        filterChainDefinitionMap.put("/m/login/auth", "anon");
        filterChainDefinitionMap.put("/m/login/logout", "anon");

        //客户端登录
        filterChainDefinitionMap.put("/mb/login/**", "anon");

        //云充小程序登录
        filterChainDefinitionMap.put("/yc/**", "anon");

        //小程序接口
        filterChainDefinitionMap.put("/mp/**", "anon");


        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/imgs/**", "anon");

        //gps 设备回调地址
        filterChainDefinitionMap.put("/rentCar/directive/**", "anon");

        filterChainDefinitionMap.put("/m/car/**", "anon");
        filterChainDefinitionMap.put("/m/crm/uploadImgWX", "anon");
        filterChainDefinitionMap.put("/m/member/**", "anon");
//		  filterChainDefinitionMap.put("/m/crm/appointDrive", "anon");

        filterChainDefinitionMap.put("/verification/**", "anon");

        filterChainDefinitionMap.put("/m/news/**", "anon");
        filterChainDefinitionMap.put("/m/me/**", "anon");
        filterChainDefinitionMap.put("/m/takeCareCar/**", "anon");
        filterChainDefinitionMap.put("/m/market/**", "anon");
        filterChainDefinitionMap.put("/wechat/**", "anon");
        filterChainDefinitionMap.put("/mp/**", "anon");
        filterChainDefinitionMap.put("/m/order/**", "anon");

//		  filterChainDefinitionMap.put("/api-docs/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/swagger-resources/configuration/security", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");


        filterChainDefinitionMap.put("/**", "authc");


        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);


        return shiroFilterFactoryBean;
    }

//    /**  
//     * 凭证匹配器  
//     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了  
//     * ）  
//     *  
//     * @return  
//     */  
//    @Bean  
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {  
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();  
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;  
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));  
//        return hashedCredentialsMatcher;  
//    }  

    @Bean
    public UserRealm myShiroRealm() {
        UserRealm myShiroRealm = new UserRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());  
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());

        // 自定义session管理 使用redis  
        securityManager.setSessionManager(sessionManager());
//        // 自定义缓存实现 使用redis  
//        securityManager.setCacheManager(cacheManager());  
        return securityManager;
    }

    //自定义sessionManager  
    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

//    /** 
//     * 配置shiro redisManager 
//     * <p> 
//     * 使用的是shiro-redis开源插件 
//     * 
//     * @return 
//     */  
//    public RedisManager redisManager() {  
//        RedisManager redisManager = new RedisManager();  
//        redisManager.setHost(host);  
//        redisManager.setPort(port);  
//        redisManager.setExpire(1800);// 配置缓存过期时间  
//        redisManager.setTimeout(timeout);  
//        redisManager.setPassword(password);  
//        return redisManager;  
//    }  

//    /** 
//     * cacheManager 缓存 redis实现 
//     * <p> 
//     * 使用的是shiro-redis开源插件 
//     * 
//     * @return 
//     */  
//    @Bean  
//    public RedisCacheManager cacheManager() {  
//        RedisCacheManager redisCacheManager = new RedisCacheManager();  
//        redisCacheManager.setRedisManager(redisManager());  
//        return redisCacheManager;  
//    }  

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * <p>
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public SessionRedisDao redisSessionDAO() {
        SessionRedisDao redisSessionDAO = new SessionRedisDao();
//        redisSessionDAO.setRedisManager(redisManager());  
        return redisSessionDAO;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

//    /** 
//     * 注册全局异常处理 
//     * @return 
//     */  
//    @Bean(name = "exceptionHandler")  
//    public HandlerExceptionResolver handlerExceptionResolver() {  
//        return new MyExceptionHandler();  
//    }


}  