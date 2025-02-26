package cn.picc.finance.sample.conf;

import cn.picc.finance.sample.ws.conf.SampleWS;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.ws.ui.annotation.WSInterfaceDesc;
import org.springframework.cloud.ws.ui.annotation.WSModuleDesc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * 用于配置每个
 */
@Configuration
@WSModuleDesc(name = "Sample模块")
public class WebServiceSampleRegister {

    @Autowired
    private Bus bus;

    @Autowired
    private SampleWS sampleWS;

    /**
     * JAX-WS
     * 站点服务
     * 注释后:wsdl访问地址为http://localhost:9101/{spring.application.name}/services/testService?wsdl
     **/
    // @WSInterfaceDesc不定义module name 的将使用类上@WSModuleDesc的name作为module name
    @Bean
    @WSInterfaceDesc( name = "sample的WebService", webServiceUri="/sampleWS", desc = "测试sample接口", lastUpdateTime = "2025-2-05", version = "v1")
    public Endpoint sampleEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, sampleWS);
        endpoint.getInInterceptors().add(new ReqContentLogInInterceptor());
        endpoint.publish("/sampleWS");
        return endpoint;
    }



}
