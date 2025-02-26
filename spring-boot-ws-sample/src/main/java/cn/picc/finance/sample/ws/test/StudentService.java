package cn.picc.finance.sample.ws.test;


import cn.picc.finance.sample.model.TestStudent;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService( name = "StudentService",
        targetNamespace = "http://test.ws.sample.finance.picc.cn"
)
public interface StudentService {
    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    @WebResult(name = "return", targetNamespace = "")
    public TestStudent getStudent(@WebParam(name = "student") TestStudent student);

    @WebMethod
    @WebResult(name="return",targetNamespace="")
    public String getStudentName(@WebParam(name = "student") TestStudent student);
}
