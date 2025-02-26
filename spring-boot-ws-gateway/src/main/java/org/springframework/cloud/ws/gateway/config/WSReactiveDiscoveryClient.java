package org.springframework.cloud.ws.gateway.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Author:Tiger Shi
 * * Date: 2024/12/1
 * It use to filter gateway server's register information. Gateway server needn't to a route agent
 * 过滤gateway 服务的注册信息，因为gateway 不需要路由
 */
public class WSReactiveDiscoveryClient implements ReactiveDiscoveryClient {

    private String gateWayServerId;

    private ReactiveDiscoveryClient client;

    public WSReactiveDiscoveryClient(String serverId,  ReactiveDiscoveryClient client){
        this.gateWayServerId = serverId;
        this.client = client;
    }

    @Override
    public String description() {
        return "Spring Cloud Webservice Reactive Discovery Client";
    }

    @Override
    public Flux<ServiceInstance> getInstances(String serviceId) {
        return client.getInstances(serviceId);
    }

    @Override
    public Flux<String> getServices() {
        return client.getServices().filter(serverId ->{
            return !serverId.equals(gateWayServerId);
        });
    }

    public Mono<Void> reactiveProbe() {
        return client.reactiveProbe();
    }

    public int getOrder() {
        return client.getOrder();
    }
}
