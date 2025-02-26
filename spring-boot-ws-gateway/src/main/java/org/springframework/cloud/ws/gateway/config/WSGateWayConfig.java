package org.springframework.cloud.ws.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.ArrayList;
/**
 * Author:Tiger Shi
 *It use to configure cross region and register DiscoveryClientRouteDefinitionLocator that use to
 * get the register server information from miro-service register center
 * 用于跨域配置和注册DiscoveryClientRouteDefinitionLocator，后者用于从微服务注册中心得到微服务的注册信息
 *
 */

@Configuration
public class WSGateWayConfig {
    private final static String slash = "/";
    private static Logger logger = LoggerFactory.getLogger(WSGateWayConfig.class);
    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public CorsWebFilter corsWebFilter() {
        logger.info("configure the cors web filter");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //配置跨域
        corsConfiguration.addAllowedHeader("*");     //允许任意请求头跨域
        corsConfiguration.addAllowedMethod("*");     //允许任何请求方式跨域
        corsConfiguration.addAllowedOrigin("*");     //允许任意请求来源跨域
        corsConfiguration.setAllowCredentials(true); //允许携带cookie信息跨域
        corsConfiguration.setMaxAge(7200L);          //准备响应前的缓存持续的最大时间（以秒为单位）
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }

    @Autowired
    private DiscoveryLocatorProperties properties;
    @Autowired
    private ReactiveDiscoveryClient discoveryClient;

    @Bean
    public DiscoveryClientRouteDefinitionLocator discoveryClientRouteDefinitionLocator() {
        this.properties.setFilters(new ArrayList<>());
        return new DiscoveryClientRouteDefinitionLocator(new WSReactiveDiscoveryClient(appName, discoveryClient), this.properties);
    }


}
