package cn.picc.finance.ysbf;


import com.alibaba.nacos.shaded.com.google.common.io.BaseEncoding;

public class Test {

    public static void main(String[] args) {

        System.out.println("----------------------");
        String abc = BaseEncoding.base64().encode("xinyidaicaiwuxitong@202501webservice".getBytes());
        System.out.println(abc);
    }
}
