package com.zhaojm.config.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private boolean useSuffixPatternMatch = true;

    private boolean useTrailingSlashMatch = true;

    private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

    private final List<String> fileExtensions = new ArrayList();
    /****
     *  实现 RequestMapping  默认根据包路径生成 url
     */
    /**
     * 初始化方法时候查询是否有@requestMapping注解
     * 使用方法和类型级别@RequestMapping注释来创建 RequestMappingInfo。
     * @param method
     * @param handlerType
     * @return
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        RequestMapping methodAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        // 如果方法注解了@RequestMapping
        if(null != methodAnnotation){
            // 得到自定义方法级请求条件。
            RequestCondition<?> requestCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(methodAnnotation, requestCondition, method);
            RequestMapping typeAnnotation = AnnotatedElementUtils.findMergedAnnotation(handlerType, RequestMapping.class);
            if (typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                info = createRequestMappingInfo(typeAnnotation, typeCondition, handlerType).combine(info);
            }
        }
        return info;
    }

    /***
     * 得到@requestMapping 注解中的value，如果没有的话就把重新赋值
     * @param annotation
     * @param customCondition
     * @param element
     * @return
     */
    protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation, RequestCondition<?> customCondition, AnnotatedElement element) {
        String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());
        if(null != patterns && patterns.length == 0){
            //如是类名
            if(element instanceof Class){
                Class<?> handlerType = (Class<?>) element;
                patterns= new String[]{this.classPathToUrl(handlerType.getName())};
            }else{  //如果是方法就去方法名称
                Method method = (Method) element;
                patterns= new String[]{method.getName()};
            }
        }
        return new RequestMappingInfo(
                new PatternsRequestCondition(patterns, getUrlPathHelper(), getPathMatcher(), this.useSuffixPatternMatch, this.useTrailingSlashMatch, this.fileExtensions),
                new RequestMethodsRequestCondition(annotation.method()),
                new ParamsRequestCondition(annotation.params()),
                new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()),
                new ProducesRequestCondition(annotation.produces(), annotation.headers(), this.contentNegotiationManager),
                customCondition
        );
    }

    private String classPathToUrl(String path) {
        //1.去除包路径
        //2.去除controller包
        //String typeValue = path.replaceAll("^com\\.xxxxxx\\.xxxx\\.[a-zA-z]+\\.|[c|C]ontroller[\\.]*", "");
        String typeValue = path.replaceAll("^com\\.zhaojm\\.[a-zA-z]+\\.|[c|C]ontroller[\\.]*", "");
        //3.类名称首字母转小写
        String[] typeValues = typeValue.split("\\.");
        String lastStr = typeValues[typeValues.length-1];
        typeValues[typeValues.length-1] = lastStr.substring(0, 1).toLowerCase() + lastStr.substring(1);
        return StringUtils.join(typeValues, "/");
    }
}
