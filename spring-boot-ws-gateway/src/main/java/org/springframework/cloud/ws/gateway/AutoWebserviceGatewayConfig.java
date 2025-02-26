package org.springframework.cloud.ws.gateway;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages ="org.springframework.cloud.ws.gateway.config, org.springframework.cloud.ws.gateway.service, org.springframework.cloud.ws.gateway.ui" )
public class AutoWebserviceGatewayConfig {
}
