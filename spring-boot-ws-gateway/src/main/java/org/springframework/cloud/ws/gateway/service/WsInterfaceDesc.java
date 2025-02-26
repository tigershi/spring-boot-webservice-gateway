package org.springframework.cloud.ws.gateway.service;

/**
 * Author:Tiger Shi
 * 用于得到整个gateway的 代理的 webservice 接口地址描述
 */
public interface WsInterfaceDesc {
    public String getGateWayWsDesc(String basePath);
}
