package cn.picc.finance.sample.service;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class SampleServiceImpl implements SampleService{
    @Value("${ws.sample.config.student.name}")
    private String studentName;
    @Value("${ws.sample.config.teacher.name}")
    private String teachName;

    @Override
    public TestStudent getSampleStudent(TestStudent student) {
        TestStudent student1 = new TestStudent();
        student1.setId("11111");
        student1.setAge(23);
        student1.setName("remote--sample--webservice-student2");
        return student1;
    }

    @Override
    public String getSampleStudentName(TestStudent student) {
        return studentName;
    }

    @Override
    public TestTeacher getSampleTeacher(TestTeacher teacher) {
        TestTeacher test = new TestTeacher();
        test.setId("11112");
        test.setAge(33);
        test.setName("remote-sample--webservice-teacher");
        return test;
    }

    @Override
    public String getSampleTeacherName(TestTeacher teacher) {
        return teachName;
    }
}
