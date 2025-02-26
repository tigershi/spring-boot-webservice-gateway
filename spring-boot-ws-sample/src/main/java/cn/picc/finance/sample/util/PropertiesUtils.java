package cn.picc.finance.sample.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtils {

	public static String getProperties(String key){
		String data = "";
		try {
			InputStream resourceAsStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties pro = new Properties();
			pro.load(new InputStreamReader(resourceAsStream,"UTF-8"));
			data = pro.getProperty(key);
			System.out.println(data);
		}catch (Exception e){
			e.printStackTrace();
		}
		return data;
	}


	public static void main(String[] args) {
		String url = getProperties("jdbc.driver");
		System.out.println("url"+url);
		String username = getProperties("jdbc.username");
		System.out.println("username"+username);
		String password = getProperties("jdbc.password");
		System.out.println("password"+password);

	}
}
