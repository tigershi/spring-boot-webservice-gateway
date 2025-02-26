package cn.picc.finance.sample.service;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;

public interface TestService {

    public TestStudent getStudent(TestStudent student);

    public String getStudentName(TestStudent student);
    public TestTeacher getTeacher(TestTeacher teacher);
    public String getTeacherName(TestTeacher teacher);
}
