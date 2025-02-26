package cn.picc.finance.sample.conf;

import cn.picc.finance.sample.ws.test.StudentService;
import cn.picc.finance.sample.ws.test.TeacherWS;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.ws.ui.annotation.WSModuleDesc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.ws.ui.annotation.WSInterfaceDesc;

import javax.xml.ws.Endpoint;

/**
 * 用于配置每个
 */
@Configuration
@WSModuleDesc(name = "测试模块")
public class WebServiceTestRegister {

    @Autowired
    private Bus bus;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherWS teacherWS;

    /**
     * JAX-WS
     * 站点服务
     * 注释后:wsdl访问地址为http://localhost:9101/{spring.application.name}/services/testService?wsdl
     **/
    // @WSInterfaceDesc不定义module name 的将使用类上@WSModuleDesc的name作为module name
    @Bean
    @WSInterfaceDesc(name = "学生测试", webServiceUri="/studentService",  desc = "测试学生接口", lastUpdateTime = "2025-1-24", version = "v1")
    public Endpoint testEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, studentService);
        endpoint.getInInterceptors().add(new ReqContentLogInInterceptor());
        endpoint.publish("/studentService");
        return endpoint;
    }


    /**
     * JAX-WS
     * 站点服务
     * 注释后:wsdl访问地址为http://localhost:9101/{spring.application.name}/services/testService?wsdl
     **/

    @Bean
    public Endpoint testTeacherEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, teacherWS);
        endpoint.getInInterceptors().add(new ReqContentLogInInterceptor());
        endpoint.publish("/teacherWS");
        return endpoint;
    }


}
