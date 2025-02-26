package cn.picc.finance.sample.ws.conf;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;
import cn.picc.finance.sample.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "SampleWS",
        targetNamespace = "http://conf.ws.sample.finance.picc.cn",
        endpointInterface="cn.picc.finance.sample.ws.conf.SampleWS"

)
@Component
public class SampleWSImpl implements SampleWS{

    @Autowired
    private SampleService sampleService;

    @Override
    public TestStudent getSampleStudent(TestStudent student) {
        return sampleService.getSampleStudent(student);
    }

    @Override
    public String getSampleStudentName(TestStudent student) {
        return sampleService.getSampleStudentName(student);
    }

    @Override
    public TestTeacher getSampleTeacher(TestTeacher teacher) {
        return sampleService.getSampleTeacher(teacher);
    }

    @Override
    public String getSampleTeacherName(TestTeacher teacher) {
        return sampleService.getSampleTeacherName(teacher);
    }
}
