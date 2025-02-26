package cn.picc.finance.sample.ws.test;

import cn.picc.finance.sample.model.TestTeacher;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService( name = "TeacherWS",
        targetNamespace = "http://test.ws.sample.finance.picc.cn"
)
public interface TeacherWS {

    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public TestTeacher getTeacher(@WebParam(name = "teacher") TestTeacher teacher);

    @WebMethod
    @WebResult(name="return",targetNamespace="")
    public String getTeacherName(@WebParam(name = "teacher") TestTeacher teacher);
}
