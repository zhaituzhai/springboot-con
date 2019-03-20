package com.zhaojm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

//    @Autowired
//    ISysUserService sysUserService;

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * accountExpired(boolean) 定义账号是否已经过期
     * accountLocked(boolean) 定义账号是否已经锁定
     * and() 用来连接配置
     * authorities(GrantedAuthority...) 授予某个用户一项或多项权限
     * authorities(List<? extends GrantedAuthority>) 授予某个用户一项或多项权限
     * authorities(String...) 授予某个用户一项或多项权限
     * credentialsExpired(boolean) 定义凭证是否已经过期
     * disabled(boolean) 定义账号是否已被禁用
     * password(String) 定义用户的密码
     * roles(String...) 授予某个用户一项或多项角色
     */

    /**
     * NoOpPasswordEncoder 明文方式保存
     * BCtPasswordEncoder 强hash方式加密
     * StandardPasswordEncoder SHA-256方式加密
     * 实现PasswordEncoder接口 自定义加密方式
     */

    /**
     * 配置user-detail服务 身份管理生成器
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth)throws Exception{
//        1.启用内存用户存储
//        auth.inMemoryAuthentication()
//                .withUser("xfy").password(passwordEncoder().encode("1234")).roles("ADMIN").and()
//                .withUser("tom").password(passwordEncoder().encode("1234")).roles("USER");
//        2.基于数据库表进行验证
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username,password,enabled from user where username = ?")
//                .authoritiesByUsernameQuery("select username,rolename from role where username=?")
//                .passwordEncoder(passwordEncoder());
//         3.配置自定义的用户服务
//        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
//        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    /**
     * 一些方法能够用来定义如何保护请求
     *
     * access(String)     如果给定的SpEL表达式计算结果为true，就允许访问
     * anonymous()        允许匿名用户访问
     * authenticated()    允许认证过的用户访问
     * denyAll()          无条件拒绝所有访问
     * fullyAuthenticated()   如果用户是完整认证的话（不是通过Remember-me功能认证的），就允许访问
     * hasAnyAuthority(String...)   如果用户具备给定权限中的某一个的话，就允许访问
     * hasAnyRole(String...)   如果用户具备给定角色中的某一个的话，就允许访问
     * hasAuthority(String)   如果用户具备给定权限的话，就允许访问
     * hasIpAddress(String)   如果请求来自给定IP地址的话，就允许访问
     * hasRole(String)   如果用户具备给定角色的话，就允许访问
     * not()   对其他访问方法的结果求反
     * permitAll()   无条件允许访问
     * rememberMe()   如果用户是通过Remember-me功能认证的，就允许访问
     */

    /**
     * 拦截请求 HTTP请求安全处理
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http)throws Exception{
        // 拦截策略
        /*http.authorizeRequests()
            .antMatchers("/","/css/**","/js/**").permitAll()   //任何人都可以访问
            .antMatchers("/admin/**").access("hasRole('ADMIN')")     //持有user权限的用户可以访问
            .antMatchers("/user/**").hasAuthority("ROLE_USER");*/
        http.authorizeRequests()
                .antMatchers("/", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    /**
     * 拦截请求 通过重载，配置Spring Security的Filter链
     * web安全
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
