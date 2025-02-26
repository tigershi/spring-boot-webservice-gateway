package cn.picc.finance.ysbf;

import cn.picc.finance.sample.model.TestStudent;
import cn.picc.finance.sample.model.TestTeacher;
import cn.picc.finance.sample.ws.conf.SampleWS;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class TestSampleWebService {


    public static void main(String[] args) {
        for(int i=0 ; i < 10; i++) {
            //调用WebService
            JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
            factory.setServiceClass(SampleWS.class);
            factory.setAddress("http://localhost:9102/ws-sample/services/sampleWS?wsdl");

            SampleWS service = (SampleWS) factory.create();

            System.out.println("#############Client getUserByName##############");
            TestStudent userreq = new TestStudent();
            userreq.setName("test req");
            userreq.setId("aaareq");
            userreq.setAge(33);
            TestStudent user = service.getSampleStudent(userreq);
            System.out.print("--------remote-------");
            System.out.print(user.getName());
            System.out.print("---------------");
            System.out.println(user.getAge());

            System.out.println( service.getSampleStudentName(userreq));


            TestTeacher req = new TestTeacher();
            userreq.setName("test teacher req");
            userreq.setId("66666");
            userreq.setAge(33);
            System.out.println( service.getSampleTeacherName(req));


            System.out.println("-------------------------------------");
        }

    }

}
