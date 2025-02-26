package org.springframework.cloud.ws.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.ws.ui.model.WebServiceDesc;
import org.springframework.cloud.ws.ui.model.WebServiceInterfaceHtmlTemple;
import org.springframework.cloud.ws.ui.model.WebServiceModuleDesc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * Author:Tiger Shi
 * 用于得到整个gateway的 代理的 webservice 接口地址描述
 */
@Service
public class WsInterfaceDescImpl implements WsInterfaceDesc {
    private static Logger logger = LoggerFactory.getLogger(WsInterfaceDescImpl.class);
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private ConcurrentMap<String,String> descsStr= new ConcurrentHashMap<>();
    @Override
    public String getGateWayWsDesc(String basePath) {
        Map<String, String> serviceUrls = new HashMap<>();
        List<String> list = discoveryClient.getServices();
        for (String service : list) {
                List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(service);
                for (ServiceInstance instance : serviceInstanceList) {
                    if (!appName.equals(instance.getServiceId())){
                        String path = "/"+instance.getServiceId()+"/interfaces/json";
                        serviceUrls.put(instance.getServiceId()+"/"+instance.getUri().toString(), path);
                        logger.info("add webservice desc addr: {}---serviceId:{}--instanceId:{}---uri:{}----path:{}", service, instance.getServiceId(), instance.getInstanceId(), instance.getUri(), path);
                    }
                    break;
                }
        }

        for (Map.Entry<String, String> entry : serviceUrls.entrySet()){
            String path = entry.getKey();
            int idx = path.indexOf("/");
            String serviceId = path.substring(0, idx);
            String baseUrl = path.substring(idx+1);
            String reqUri = entry.getValue();
            logger.info("baseUrl:{}", baseUrl);
            logger.info("reqUri:{}", reqUri);
            try {
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                WebClient webClient = webClientBuilder
                        .baseUrl(baseUrl)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
                // 在数据到达时执行的操作，不会阻塞主线程
                webClient.get()
                        .uri(reqUri)
                        .retrieve()
                        .bodyToMono(String.class)
                        .onErrorResume(error->{
                            logger.error(error.getMessage(), error);
                            return Mono.just("[]");
                        }).subscribe(responseStr ->{
                            logger.info("request result:{}", responseStr);
                            descsStr.put(reqUri, responseStr);
                        });

            }catch (Exception e){
                logger.error(e.getMessage(), e);
                if (!descsStr.containsKey(reqUri)){
                    descsStr.put(reqUri, "[]");
                }
            }
        }

        List<WebServiceModuleDesc> moduleDescs = new ArrayList<>();

        for(Map.Entry<String, String> entry: descsStr.entrySet()){
            if (!serviceUrls.containsValue(entry.getKey())){
                descsStr.remove(entry.getKey());
                continue;
            }
            String result = entry.getValue();
            if (!result.equals("[]")){
                JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, WebServiceModuleDesc.class);
                try {

                    List<WebServiceModuleDesc> subDescs = mapper.readValue(result, javaType);
                    for (WebServiceModuleDesc subDesc : subDescs){
                        for(WebServiceDesc desc : subDesc.getUris()){
                            String uri_new = basePath+desc.getWebServiceUri();
                          desc.setWebServiceUri(uri_new);
                        }
                    }
                    moduleDescs.addAll(subDescs);
                } catch (JsonProcessingException e) {
                    logger.error(e.getMessage(), e);
                }

            }



        }

        WebServiceInterfaceHtmlTemple htmlTemple = new WebServiceInterfaceHtmlTemple();
        logger.info("resultModuleSize:{}", moduleDescs.size());
        String result = htmlTemple.getModules(this.appName, moduleDescs);
        logger.info(result);
        return  result;
    }

}
