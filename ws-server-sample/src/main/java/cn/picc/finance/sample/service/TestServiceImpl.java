package cn.picc.finance.sample.service;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    private static Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);
    @Override
    public TestStudent getStudent(TestStudent student) {
        logger.info("student: {}, {}, {}", student.getId(), student.getName(), student.getAge());
        TestStudent student1 = new TestStudent();
        student1.setId("11111");
        student1.setAge(23);
        student1.setName("remote---webservice-student2");
        return student1;

    }

    @Override
    public String getStudentName(TestStudent student) {
        return "this is the student";
    }

    @Override
    public TestTeacher getTeacher(TestTeacher teacher) {
        TestTeacher test = new TestTeacher();
        test.setId("11112");
        test.setAge(33);
        test.setName("remote---webservice-teacher");
        return test;
    }

    @Override
    public String getTeacherName(TestTeacher teacher) {
        return "this is remote test teacher";
    }

}
