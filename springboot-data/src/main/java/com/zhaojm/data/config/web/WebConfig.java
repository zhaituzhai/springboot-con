package com.zhaojm.data.config.web;

/*
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ConRequestMappingHandlerMapping();
    }

    *//**
     * https://www.cnblogs.com/deng720/p/8989388.html
     * spring boot 继承WebMvcConfigurationSupport后自动配置不生效问题
     *//*
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //swagger2
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }
}*/
