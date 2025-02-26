package org.springframework.cloud.ws.gateway.config;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Author:Tiger Shi
 * * Date: 2024/12/1
 * It use to redirect the web-service service address need to redirect server address to gateway address
 * webservice 地址需要修改为代理服务器或者gateway 地址
 */
@Configuration
public class WSModifyResponseBodyFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(WSModifyResponseBodyFilter.class);
    private static final String wsdlStr = "?wsdl";
    private static final String soapAddr = "location=\"";


    private final static String slash = "/";
    private final static String colon = ":";

    @Value("${ws.multiProxy.enable:false}")
    private boolean wsMultiProxyEnable;
    @Value("${ws.multiProxy.request.url:none}")
    private String firstProxyAddr;
    @Value("${ws.multiProxy.request.agent.headerName:none}")
    private String headName;


    /**
     * 拦截以"?wsdl"的请求地址
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestUri = exchange.getRequest().getURI();
        log.info("response body filter:{}", requestUri.toString());
        if (requestUri.toString().endsWith(wsdlStr)){
            ServerHttpResponseDecorator decoratedResponse = getResponseDecorator(exchange);
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }

        return chain.filter(exchange);
    }

    /**
     * 对拦截的地址的返回内容进行webservice
     * 服务地址进行重新定位到gateway地址
     * @param exchange
     * @return
     */
    public ServerHttpResponseDecorator getResponseDecorator (ServerWebExchange exchange) {
        URI requestUri = exchange.getRequest().getURI();
        final String proxyAddr = this.firstProxyAddr;
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        StringBuilder sb = new StringBuilder();
                        dataBuffers.forEach(dataBuffer -> {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            try {
                                sb.append(new String(content, "utf-8"));
                            } catch (IOException e) {
                                log.error(e.getMessage(), e);
                            }
                        });
                        String bodyStr = sb.toString();
                        log.debug("old body str:{}", bodyStr);
                        URI respUrl = (URI)exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
                        String oldUrl = soapAddr+ respUrl.toString().replace(wsdlStr, "");
                        String newUrl = null;
                        if (wsMultiProxyEnable){
                            if (!proxyAddr.equals("none")){
                                newUrl = soapAddr + proxyAddr + requestUri.getPath();
                            }
                            if (!headName.equals("none")){
                                String reqHostName = exchange.getRequest().getHeaders().get(headName).get(0);
                                newUrl = soapAddr + reqHostName + requestUri.getPath();
                            }

                        }else {
                            newUrl = soapAddr + requestUri.toString().replace(wsdlStr, "");
                        }
                        log.info("routeUrl:{}",oldUrl);
                        log.info("schemaUrl:{}",newUrl);
                        String newBodyStr = bodyStr.replace(oldUrl, newUrl);
                        log.debug("new bodyStr;{}", newBodyStr);
                        byte[] newByte = newBodyStr.getBytes(StandardCharsets.UTF_8);
                        exchange.getResponse().getHeaders().setContentLength(newByte.length);
                        return bufferFactory().wrap(newByte);
                    }));
                }

                return super.writeWith(body);
            }
        };

        return decoratedResponse;
    }


    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER -10;
    }

}
