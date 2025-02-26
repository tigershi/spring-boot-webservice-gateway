package cn.picc.finance.sample.ws.test;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "StudentService",
        targetNamespace = "http://test.ws.sample.finance.picc.cn",
        endpointInterface="cn.picc.finance.sample.ws.test.StudentService"
)
@Component
public class StudentServiceImpl implements StudentService {
   private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private TestService testService;

    @Override
    public TestStudent getStudent(TestStudent student) {
        return testService.getStudent(student);
    }

    @Override
    public String getStudentName(TestStudent student) {
        return testService.getStudentName(student);
    }
}
