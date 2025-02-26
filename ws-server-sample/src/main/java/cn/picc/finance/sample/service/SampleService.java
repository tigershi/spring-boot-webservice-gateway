package cn.picc.finance.sample.service;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;

public interface SampleService {

    public TestStudent getSampleStudent(TestStudent student);
    public String getSampleStudentName(TestStudent student);
    public TestTeacher getSampleTeacher(TestTeacher teacher);
    public String getSampleTeacherName(TestTeacher teacher);
}
