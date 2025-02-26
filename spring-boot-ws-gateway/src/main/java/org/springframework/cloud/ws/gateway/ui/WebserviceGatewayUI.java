package org.springframework.cloud.ws.gateway.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.ws.gateway.service.WsInterfaceDesc;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Author:Tiger Shi
 * 整个gateway代理的 webservice 接口地址描述暴露接口
 */
@Component
public class WebserviceGatewayUI {
    private final static String baseDefPath = "/interfaces/wsdl";
    private static Logger logger = LoggerFactory.getLogger(WebserviceGatewayUI.class);
    @Autowired
    private WsInterfaceDesc desc;

    @Value("${spring.cloud.gateway.ui.url:none}")
    private String defDescPath;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(ResourceLoader resourceLoader) {
        String descUrl;
        if (!defDescPath.equals("none")){
            descUrl = defDescPath;
        } else {
            descUrl = baseDefPath;
        }
        return route(GET(descUrl).and(accept(MediaType.TEXT_HTML)), request ->{
                    String bastPath = request.uri().toString().replace(descUrl, "");
                    logger.info("request bose path:{}", bastPath);
                    return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(getBodyFromResource(bastPath), String.class);
                }
        );
    }

    private Mono<String> getBodyFromResource(String basePath){
        return Mono.just(desc.getGateWayWsDesc(basePath));
    }

}
