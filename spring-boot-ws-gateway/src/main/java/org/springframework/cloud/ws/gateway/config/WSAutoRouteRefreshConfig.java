package org.springframework.cloud.ws.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author:Tiger Shi
 * It use to config the auto refresh route information
 * 用户自当刷新路由信息
 */
@Configuration
@EnableScheduling
public class WSAutoRouteRefreshConfig {
    private static Logger logger = LoggerFactory.getLogger(WSAutoRouteRefreshConfig.class);

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionRepository repository;
    //it debug 路由
    @Autowired
    private RouteDefinitionLocator locator;

    private Set<String> serverIds = new HashSet<>();
    boolean routeChange = false;


    /**
     * 此处代码用于刷新注册中心变化后添加
     * */
    @Scheduled(fixedDelay = 60 * 1000)
    public void printRoute() {
        Mono<List<String>> result1 =  locator.getRouteDefinitions().map(routeDefinition -> {
            if (!this.serverIds.contains(routeDefinition.getId())){
                this.repository.save(Mono.just(routeDefinition)).subscribe();
                this.serverIds.add(routeDefinition.getId());
                this.routeChange = true;
                logger.info("add route:{}", routeDefinition.toString());
            }
            return routeDefinition.getId();
        }).collectList();
        List<String> list = result1.block();
        if (list != null){
            for (String serverId: this.serverIds){
                if (!(list.contains(serverId))){
                    this.routeChange = true;
                    logger.info("del route:{}", serverId);
                    this.repository.delete(Mono.just(serverId)).subscribe();
                    this.serverIds.remove(serverId);
                }
            }
        }
        if (this.routeChange){
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        }
        routeChange = false;
    }

}
