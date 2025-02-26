package cn.picc.finance.sample.ws.conf;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


@WebService( name = "SampleWS",
        targetNamespace = "http://conf.ws.sample.finance.picc.cn"
)
public interface SampleWS {

    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public TestStudent getSampleStudent(@WebParam(name = "student") TestStudent student);
    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public String getSampleStudentName(@WebParam(name = "student") TestStudent student);

    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public TestTeacher getSampleTeacher(@WebParam(name = "teacher") TestTeacher teacher);
    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public String getSampleTeacherName(@WebParam(name = "teacher") TestTeacher teacher);

}
