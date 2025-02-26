package org.springframework.cloud.ws.ui.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.ws.ui.annotation.WSInterfaceDesc;
import org.springframework.cloud.ws.ui.annotation.WSModuleDesc;
import org.springframework.cloud.ws.ui.model.WebServiceDesc;
import org.springframework.cloud.ws.ui.model.WebServiceInterfaceHtmlTemple;
import org.springframework.cloud.ws.ui.model.WebServiceModuleDesc;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ConditionalOnProperty(name="spring.cloud.webservice.ui.enable", havingValue= "true", matchIfMissing = false)
public class WsInterfaceControl {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final static String serStr = "/services";
    @Autowired
    private ApplicationContext applicationContext;

    @Value("${spring.application.name:Default}")
    private String appName;

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @RequestMapping("/interfaces/json")
    @ResponseBody
    public List<WebServiceModuleDesc>  getInterfaceJson(){
        return getWsModules();
    }

    @RequestMapping("/interfaces/wsdl")
    @ResponseBody
    public String getInterfaceWsdl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requrl = request.getRequestURL().toString();
        URI uri = URI.create(requrl);
        String hostUri = uri.getScheme()+ "://"+uri.getHost()+":"+uri.getPort();
        List<WebServiceModuleDesc> list =getWsModules();
        for (WebServiceModuleDesc desc : list){
            for (WebServiceDesc interDesc : desc.getUris()){
                interDesc.setWebServiceUri(hostUri+ interDesc.getWebServiceUri());
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        WebServiceInterfaceHtmlTemple temple = new WebServiceInterfaceHtmlTemple();
        return temple.getModules(appName, list);
    }

    private List<WebServiceModuleDesc> getWsModules(){
        List<WebServiceModuleDesc> list = new ArrayList<>();
        Map<String, WebServiceModuleDesc> req = getWebServiceInfo();
        for (Map.Entry<String, WebServiceModuleDesc> entry : req.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    private Map<String, WebServiceModuleDesc> getWebServiceInfo(){
        Map<String, WebServiceModuleDesc> result = new HashMap<>();
        Map<String, Object> modules  = applicationContext.getBeansWithAnnotation(WSModuleDesc.class);

      if (modules != null && modules.size()>0){
            for(Map.Entry<String, Object> entry : modules.entrySet()){

                Class<?>  clazz =  AopUtils.getTargetClass(entry.getValue());
                WSModuleDesc wsModuleDesc = clazz.getAnnotation(WSModuleDesc.class);
                WebServiceModuleDesc moduleDesc = new WebServiceModuleDesc();
                moduleDesc.setName(wsModuleDesc.name());
                moduleDesc.setDescription(wsModuleDesc.desc());
                result.put(moduleDesc.getName(), moduleDesc);
                Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
                for (Method method : methods) {
                    WSInterfaceDesc wsInterfaceDesc= null;
                    if ( (wsInterfaceDesc= AnnotationUtils.getAnnotation(method, WSInterfaceDesc.class)) != null){
                        if (wsInterfaceDesc.moduleName().equals("") || wsInterfaceDesc.moduleName().equals(wsModuleDesc.name())){
                            WebServiceDesc desc = new WebServiceDesc();
                            desc.setName(wsInterfaceDesc.name());
                            desc.setDesc(wsInterfaceDesc.desc());
                            desc.setWebServiceUri(getWebServicePath(wsInterfaceDesc.webServiceUri()));
                            desc.setLastUpdateTime(wsInterfaceDesc.lastUpdateTime());
                            desc.setVersion(wsInterfaceDesc.version());
                            moduleDesc.addWebServiceDesc(desc);
                        }else{
                            WebServiceDesc desc = new WebServiceDesc();
                            desc.setName(wsInterfaceDesc.name());
                            desc.setDesc(wsInterfaceDesc.desc());
                            desc.setWebServiceUri(getWebServicePath(wsInterfaceDesc.webServiceUri()));
                            desc.setLastUpdateTime(wsInterfaceDesc.lastUpdateTime());
                            desc.setVersion(wsInterfaceDesc.version());

                            if(result.get(wsInterfaceDesc.moduleName()) != null){
                                result.get(wsInterfaceDesc.moduleName()).addWebServiceDesc(desc);
                            }else {
                                WebServiceModuleDesc moduleDesc2 = new WebServiceModuleDesc();
                                moduleDesc2.setName(wsInterfaceDesc.moduleName());
                                result.put(moduleDesc2.getName(), moduleDesc2);
                                moduleDesc2.addWebServiceDesc(desc);
                            }
                        }
                    }

                }

            }
        }


        Map<String, Object> inters  = applicationContext.getBeansWithAnnotation(WSInterfaceDesc.class);
        for (Map.Entry<String, Object> objectEntry : inters.entrySet()){
            Class<?>  clazz =  AopUtils.getTargetClass(objectEntry.getValue());
            WSInterfaceDesc descWs = clazz.getAnnotation(WSInterfaceDesc.class);
            if (descWs == null){
                continue;
            }
            String moduleName = null;
            if(descWs.moduleName().equals("")){
               moduleName = "Default";
            }else {
                moduleName = descWs.moduleName();
            }
            WebServiceDesc desc = new WebServiceDesc();
            desc.setName(descWs.name());
            desc.setDesc(descWs.desc());
            desc.setWebServiceUri(getWebServicePath(descWs.webServiceUri()));
            desc.setLastUpdateTime(descWs.lastUpdateTime());
            desc.setVersion(descWs.version());

            if(result.get(moduleName) != null){
               result.get(moduleName).addWebServiceDesc(desc);
            }else {
                WebServiceModuleDesc moduleDesc2 = new WebServiceModuleDesc();
                moduleDesc2.setName(moduleName);
                result.put(moduleDesc2.getName(), moduleDesc2);
                moduleDesc2.addWebServiceDesc(desc);
            }

        }

        return result;
    }

    private String getWebServicePath(String path){
        return this.contextPath+serStr+path+"?wsdl";
    }

    @PostConstruct
    private void initLog(){
        String jsonStr = "/interfaces/json";
        String wsdlStr = "/interfaces/wsdl";

        if (!this.contextPath.equals("/")){
           jsonStr = this.contextPath + jsonStr;
           wsdlStr = this.contextPath + wsdlStr;
        }

        logger.info("spring boot webservice UI html format Address: "+wsdlStr);
        logger.info("spring boot webservice UI json format Address: "+jsonStr);

    }

}
