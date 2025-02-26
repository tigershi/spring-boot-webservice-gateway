package cn.picc.finance.sample.ws.test;

import cn.picc.finance.sample.model.TestTeacher;
import cn.picc.finance.sample.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.ws.ui.annotation.WSInterfaceDesc;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "TeacherWS",
        targetNamespace = "http://test.ws.sample.finance.picc.cn",
        endpointInterface="cn.picc.finance.sample.ws.test.TeacherWS"

)
@Component
@WSInterfaceDesc(moduleName = "老师测试模块", name = "老师测试",desc = "在webservice实现定义描述的测试老师接口", webServiceUri="/teacherWS", lastUpdateTime = "2025-2-05", version = "v1")
public class TeachWSImpl implements TeacherWS{

    @Autowired
    private TestService testService;

    @Override
    public TestTeacher getTeacher(TestTeacher teacher) {
        return testService.getTeacher(teacher);
    }

    @Override
    public String getTeacherName(TestTeacher teacher) {
        return testService.getTeacherName(teacher);
    }
}
