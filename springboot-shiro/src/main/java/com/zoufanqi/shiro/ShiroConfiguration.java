package com.zoufanqi.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * shiro 相关配置
 *
 * @author vanki
 * @date 2019-06-19 17:01
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 先放这
     */
//    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public Realm realm() {
        return new MyRealm();
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("r_m");
        // 30天
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(1L));
        cookie.setHttpOnly(true);

        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(cookie);
        /**
         * AES key，16位
         */
        cookieRememberMeManager.setCipherKey("1234567890123456".getBytes());

        return cookieRememberMeManager;
    }

    @Bean
    protected CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean sff = new ShiroFilterFactoryBean();
        sff.setSecurityManager(securityManager);

        // 未登录用户请求受限资源跳转地址
        sff.setLoginUrl("/index/login_html");
        // 权限不足跳转地址
        sff.setUnauthorizedUrl("/index/unauthorized");

        /**
         * 内置过滤器（org.apache.shiro.web.filter.mgt.DefaultFilter）
         *
         * anon
         *      AnonymousFilter
         *      指定url可以匿名访问
         * authc
         *      FormAuthenticationFilter
         *      指定url需要form表单登录，默认会从请求中获取username、password,rememberMe等参数并尝试登录，如果登录不了就会跳转到loginUrl配置的路径。
         *      我们也可以用这个过滤器做默认的登录逻辑，但是一般都是我们自己在控制器写登录逻辑的，自己写的话出错返回的信息都可以定制嘛。
         * authcBasic
         *      BasicHttpAuthenticationFilter
         *      指定url需要basic登录
         * logout
         *      LogoutFilter
         *      登出过滤器，配置指定url就可以实现退出功能，非常方便
         * noSessionCreation
         *      NoSessionCreationFilter
         *      禁止创建会话
         * perms
         *      PermissionsAuthorizationFilter
         *      需要指定权限才能访问
         * port
         *      PortFilter
         *      需要指定端口才能访问
         * rest
         *      HttpMethodPermissionFilter
         *      将http请求方法转化成相应的动词来构造一个权限字符串，这个感觉意义不大，有兴趣自己看源码的注释
         * roles
         *      RolesAuthorizationFilter
         *      需要指定角色才能访问
         * ssl
         *      SslFilter
         *      需要https请求才能访问
         * user
         *      UserFilter
         *      需要已登录或“记住我”的用户才能访问
         */

        sff.getFilterChainDefinitionMap().put("/index/**", "anon");

        sff.getFilterChainDefinitionMap().put("/login/**", "anon");

        // 需要登录
        sff.getFilterChainDefinitionMap().put("/t/changePwd", "authc");

        // 已登录或“记住我”的用户可以访问
        sff.getFilterChainDefinitionMap().put("/t/user", "user");

        // 已主动登录或rememberMe登录 + 需要permA权限
        sff.getFilterChainDefinitionMap().put("/t/pa", "user,perms[permA]");

        // 已主动登录或rememberMe登录 + 需要permAB权限，未定义此权限，跳转至：UnauthorizedUrl
        sff.getFilterChainDefinitionMap().put("/t/pab", "user,perms[permAB]");

        // 已主动登录 + 需要permB权限
        sff.getFilterChainDefinitionMap().put("/t/pb", "authc,perms[permB]");

        // 已主动登录或rememberMe登录 + 需要roleA角色
        sff.getFilterChainDefinitionMap().put("/t/ra", "user,roles[roleA]");

        // 已主动登录 + 需要roleB角色
        sff.getFilterChainDefinitionMap().put("/t/rb", "authc,roles[roleB]");

        // 退出，跳转至：LoginUrl
        sff.getFilterChainDefinitionMap().put("/t/logout", "anon,logout");

        // 其它路径均需要登录
        sff.getFilterChainDefinitionMap().put("/**", "authc");

        return sff;
    }
}
