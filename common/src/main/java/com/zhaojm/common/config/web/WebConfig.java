package com.zhaojm.common.config.web;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ConRequestMappingHandlerMapping();
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        // 处理"redirect:url"字符串
        registry.enableContentNegotiation();
        registry.viewResolver(new InternalResourceViewResolver());
    }
    /**
     * https://www.cnblogs.com/deng720/p/8989388.html
     * spring boot 继承WebMvcConfigurationSupport后自动配置不生效问题
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //swagger2
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}
